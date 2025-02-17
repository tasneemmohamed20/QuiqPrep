package com.example.fragmentsbonus.home.presenter.cat_meal;

import android.content.Context;
import com.example.fragmentsbonus.database.MealLocalDataSourceImp;
import com.example.fragmentsbonus.models.meals.MealResponse;
import com.example.fragmentsbonus.home.view.cat_meals_list.CatMealsView;
import com.example.fragmentsbonus.models.repository.MealsRepositoryImplementation;
import com.example.fragmentsbonus.network.MealsRemoteDataSourceImplementation;
import com.example.fragmentsbonus.network.NetworkCallBack;

public class CatMealsPresenterImplementation implements  CatMealsPresenter {
    private CatMealsView view;

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
        repository.getMealsByCategory(category, new NetworkCallBack () {

            @Override
            public void onSuccess(Object response) {
                view.hideLoading();
                if (response instanceof MealResponse) {
                    view.showMeals(((MealResponse) response).getMeals());
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
