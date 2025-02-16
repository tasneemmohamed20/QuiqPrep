package com.example.fragmentsbonus.home.presenter.cat_meal;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.example.fragmentsbonus.home.model.random_meal.MealResponse;
import com.example.fragmentsbonus.home.view.cat_meals_list.CatMealsView;
import com.example.fragmentsbonus.network.ApiClient;
import com.example.fragmentsbonus.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatMealsPresenterImplementation implements  CatMealsPresenter {
    private CatMealsView view;
    private final ApiService apiService;

    public CatMealsPresenterImplementation(CatMealsView view, LifecycleOwner lifecycleOwner, Context context ) {

        this.view = view;
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    @Override
    public void loadCatMeals(String category) {

        view.showLoading();
        Call<MealResponse> call = apiService.getMealByCategory(category);
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.showMeals(response.body().getMeals());
                } else {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable t) {
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
