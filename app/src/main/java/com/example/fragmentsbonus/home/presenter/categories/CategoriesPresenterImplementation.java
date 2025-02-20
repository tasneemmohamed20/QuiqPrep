package com.example.fragmentsbonus.home.presenter.categories;

import android.content.Context;
import com.example.fragmentsbonus.database.MealLocalDataSourceImp;
import com.example.fragmentsbonus.home.view.categories.CategoriesView;
import com.example.fragmentsbonus.models.repository.MealsRepositoryImplementation;
import com.example.fragmentsbonus.network.MealsRemoteDataSourceImplementation;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoriesPresenterImplementation implements  CategoriesPresenter {
    private CategoriesView view;
    private Disposable disposable;
    private final MealsRepositoryImplementation repository;


    public CategoriesPresenterImplementation(CategoriesView view,Context context) {
        this.view = view;
        this.repository = MealsRepositoryImplementation.getInstance(MealsRemoteDataSourceImplementation.getInstance(), MealLocalDataSourceImp.getInstance(context));
    }

    @Override
    public void loadCategories() {
        view.showLoading();
        disposable = repository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (view != null) {
                                view.hideLoading();
                                view.showCategories(response.getCategories());
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
