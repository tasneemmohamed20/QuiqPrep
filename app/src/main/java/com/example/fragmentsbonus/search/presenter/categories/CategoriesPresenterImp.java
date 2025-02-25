package com.example.fragmentsbonus.search.presenter.categories;

import android.content.Context;

import com.example.fragmentsbonus.database.MealLocalDataSourceImp;
import com.example.fragmentsbonus.models.repository.MealsRepositoryImplementation;
import com.example.fragmentsbonus.network.MealsRemoteDataSourceImplementation;
import com.example.fragmentsbonus.search.view.SearchView;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoriesPresenterImp implements CategoriesPresenter {
    private SearchView view;
    private final MealsRepositoryImplementation repository;
    private Disposable disposable;

    public CategoriesPresenterImp(SearchView view, Context context) {
        this.view = view;
        this.repository = MealsRepositoryImplementation.getInstance(
                MealsRemoteDataSourceImplementation.getInstance(context),
                MealLocalDataSourceImp.getInstance(context));
    }
    @Override
    public void getCategories() {
        disposable = repository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (view != null) {
                                List<String> categoryNames = response.getCategories()
                                        .stream()
                                        .map(category -> category.getStrCategory())
                                        .collect(Collectors.toList());
                                view.showCategories(categoryNames);
                            }
                        },
                        error -> {
                            if (view != null) {
                                view.showError(error.getMessage());
                            }
                        }
                );
    }

    @Override
    public void filterCategories(String query) {
        if (view == null) return;

        disposable = repository.getCategories()
                .map(response -> {
                    List<String> filteredCategories = response.getCategories().stream()
                            .map(category -> category.getStrCategory())
                            .filter(categoryName ->
                                    categoryName.toLowerCase()
                                            .contains(query.toLowerCase()))
                            .collect(Collectors.toList());

                    // If no matches found, return the original list
                    if (filteredCategories.isEmpty()) {
                        return response.getCategories().stream()
                                .map(category -> category.getStrCategory())
                                .collect(Collectors.toList());
                    }

                    return filteredCategories;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categories -> view.showCategories(categories),
                        error -> view.showError(error.getMessage())
                );
    }

    @Override
    public void detachView() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        this.view = null;
    }
}
