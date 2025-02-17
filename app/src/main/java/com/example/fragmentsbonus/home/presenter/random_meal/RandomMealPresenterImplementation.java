package com.example.fragmentsbonus.home.presenter.random_meal;

import android.content.Context;
import com.example.fragmentsbonus.database.MealLocalDataSourceImp;
import com.example.fragmentsbonus.models.meals.MealResponse;
import com.example.fragmentsbonus.home.view.random_meal.RandomMealView;
import com.example.fragmentsbonus.models.repository.MealsRepositoryImplementation;
import com.example.fragmentsbonus.network.MealsRemoteDataSourceImplementation;
import com.example.fragmentsbonus.network.NetworkCallBack;

public class RandomMealPresenterImplementation implements RandomMealPresenter {

    private RandomMealView view;
    private final MealsRepositoryImplementation repository;
    public RandomMealPresenterImplementation(RandomMealView view, Context context) {
        this.view = view;
        this.repository = MealsRepositoryImplementation.getInstance(MealsRemoteDataSourceImplementation.getInstance(), MealLocalDataSourceImp.getInstance(context));
    }

    @Override
    public void loadRandomMeal() {

        view.showLoading();
        repository.getRandomMeals(new NetworkCallBack() {
            @Override
            public void onSuccess(Object response) {
                view.hideLoading();
                if (response instanceof MealResponse) {
                    view.showMeal(((MealResponse) response).getMeals().get(0));
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
        } );

    }
    @Override
    public void detachView() {
        view = null;
    }

}
