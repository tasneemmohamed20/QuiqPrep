package com.example.fragmentsbonus.search.presenter.filtered.by_country;

import android.content.Context;

import com.example.fragmentsbonus.database.MealLocalDataSourceImp;
import com.example.fragmentsbonus.models.meals.MealsItem;
import com.example.fragmentsbonus.models.repository.MealsRepositoryImplementation;
import com.example.fragmentsbonus.network.MealsRemoteDataSourceImplementation;
import com.example.fragmentsbonus.search.view.filtered.FilteredView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CounFilterPresenterImp implements CounFilterPresenter{
    private FilteredView view;
    private final MealsRepositoryImplementation repository;
    private Disposable disposable;
    private List<MealsItem> allMeals = new ArrayList<>();
    public CounFilterPresenterImp(FilteredView view, Context context) {
        this.view = view;
        this.repository = MealsRepositoryImplementation.getInstance(
                MealsRemoteDataSourceImplementation.getInstance(),
                MealLocalDataSourceImp.getInstance(context)
        );
    }
    @Override
    public void getMealsByCountry(String country) {
        view.showLoading();
        disposable = repository.getMealsByCountry(country)
                .flatMapObservable(response -> Observable.fromIterable(response.getMeals()))
                .flatMap(meal -> repository.getMealById(meal.getIdMeal())
                        .map(detailResponse -> detailResponse.getMeals().get(0))
                        .toObservable())
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        detailedMeals -> {
                            if (view != null) {
                                view.hideLoading();
                                allMeals = new ArrayList<>(detailedMeals);
                                view.showFilteredMeals(detailedMeals);
                            }
                        },
                        error -> {
                            if (view != null) {
                                view.hideLoading();
                                view.showFilteredError(error.getMessage());
                            }
                        }
                );
    }

    @Override
    public void dispose() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        view = null;
    }

    @Override
    public void filterMeals(String query) {
        if (query.isEmpty()) {
            view.showFilteredMeals(allMeals);
            return;
        }

        List<MealsItem> filteredList = allMeals.stream()
                .filter(meal -> meal.getStrMeal().toLowerCase()
                        .contains(query.toLowerCase()))
                .collect(Collectors.toList());

        view.showFilteredMeals(filteredList);
    }
}
