package com.example.fragmentsbonus.home.presenter.categories;

import android.content.Context;
import com.example.fragmentsbonus.database.MealLocalDataSourceImp;
import com.example.fragmentsbonus.models.categories.CategoryResponse;
import com.example.fragmentsbonus.home.view.categories.CategoriesView;
import com.example.fragmentsbonus.models.repository.MealsRepositoryImplementation;
import com.example.fragmentsbonus.network.MealsRemoteDataSourceImplementation;
import com.example.fragmentsbonus.network.NetworkCallBack;

public class CategoriesPresenterImplementation implements  CategoriesPresenter {
    private CategoriesView view;
    private final MealsRepositoryImplementation repository;


    public CategoriesPresenterImplementation(CategoriesView view,Context context) {
        this.view = view;
        this.repository = MealsRepositoryImplementation.getInstance(MealsRemoteDataSourceImplementation.getInstance(), MealLocalDataSourceImp.getInstance(context));
    }

    @Override
    public void loadCategories() {

        view.showLoading();
        repository.getCategories(new NetworkCallBack() {
            @Override
            public void onSuccess(Object response) {
                view.hideLoading();
                if (response instanceof CategoryResponse) {
                    view.showCategories(((CategoryResponse) response).getCategories());
                }
                else {
                    view.onErrorLoading("Error Loading");
                }
            }

            @Override
            public void onError(String error) {
                view.hideLoading();
                view.onErrorLoading(error);
            }
        });
    }

    @Override
    public void detachView() {
        view = null;
    }
}
