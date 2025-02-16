package com.example.fragmentsbonus.home.presenter.random_meal;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.example.fragmentsbonus.home.model.random_meal.MealResponse;
import com.example.fragmentsbonus.home.view.random_meal.RandomMealView;
import com.example.fragmentsbonus.network.ApiClient;
import com.example.fragmentsbonus.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;

public class RandomMealPresenterImplementation implements RandomMealPresenter {

    private RandomMealView view;
    private final ApiService apiService;
    private LifecycleOwner lifecycleOwner;

    public RandomMealPresenterImplementation(RandomMealView view, Context context, LifecycleOwner lifecycleOwner) {
        this.view = view;
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    @Override
    public void loadRandomMeal() {

        view.showLoading();

        Call<MealResponse> call = apiService.getRandomMeal();
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, retrofit2.Response<MealResponse> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.showMeal(response.body().getMeals().get(0));
                } else {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                view.hideLoading();
                view.onErrorLoading("Error Loading");
            }
        });

    }
    @Override
    public void detachView() {
        view = null;
    }

}
