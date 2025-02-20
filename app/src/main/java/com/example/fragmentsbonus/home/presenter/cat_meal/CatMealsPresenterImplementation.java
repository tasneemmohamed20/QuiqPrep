package com.example.fragmentsbonus.home.presenter.cat_meal;

import android.content.Context;
import com.example.fragmentsbonus.database.MealLocalDataSourceImp;
import com.example.fragmentsbonus.home.view.cat_meals_list.CatMealsView;
import com.example.fragmentsbonus.models.repository.MealsRepositoryImplementation;
import com.example.fragmentsbonus.network.MealsRemoteDataSourceImplementation;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CatMealsPresenterImplementation implements  CatMealsPresenter {
    private CatMealsView view;
    private Disposable disposable;
    private final MealsRepositoryImplementation repository;
    public CatMealsPresenterImplementation(CatMealsView view, Context context ) {

        this.view = view;
        this.repository = MealsRepositoryImplementation
                .getInstance(MealsRemoteDataSourceImplementation.getInstance(),
                        MealLocalDataSourceImp.getInstance(context));
    }

    @Override
    public void loadCatMeals(String category) {
        view.showLoading();
        disposable = repository.getMealsByCategory(category)
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
                                view.showMeals(detailedMeals);
                            }
                        },
                        error -> {
                            if (view != null) {
                                view.hideLoading();
                                view.onErrorLoading(error.getMessage());
                            }
                        }
                );
    }
    @Override
    public void detachView() {

        view = null;
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
