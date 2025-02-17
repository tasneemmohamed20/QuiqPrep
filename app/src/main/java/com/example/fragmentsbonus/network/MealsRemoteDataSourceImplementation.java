package com.example.fragmentsbonus.network;


import androidx.annotation.NonNull;

import com.example.fragmentsbonus.models.categories.CategoryResponse;
import com.example.fragmentsbonus.models.meals.MealResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSourceImplementation implements MealsRemoteDataSource {
    private static final String TAG = "MealsClient";
    private static MealsRemoteDataSourceImplementation client = null;
    private ApiService apiService;
    
    private MealsRemoteDataSourceImplementation() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api_strings.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static synchronized MealsRemoteDataSourceImplementation getInstance(){
        if(client == null){
            client = new MealsRemoteDataSourceImplementation();
        }
        return client;
    }


    @Override
    public void getRandomMeals(NetworkCallBack networkCallBack) {
        Call<MealResponse> call = apiService.getRandomMeal();
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    networkCallBack.onSuccess(response.body());
                } else {
                    networkCallBack.onError("Error fetching meals: " +response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable throwable) {
                networkCallBack.onError("Network error: " + throwable.getMessage());
            }
        });
    }

    @Override
    public void getCategories(NetworkCallBack networkCallBack) {
        Call<CategoryResponse> call = apiService.getCategories();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    networkCallBack.onSuccess(response.body());
                } else {
                    networkCallBack.onError("Error fetching categories: " +response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable throwable) {
                networkCallBack.onError("Network error: " + throwable.getMessage());
            }
        });
    }

    @Override
    public void getMealsByCategory(String category, NetworkCallBack networkCallBack) {
        Call<MealResponse> call = apiService.getMealByCategory(category);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    networkCallBack.onSuccess(response.body());
                } else {
                    networkCallBack.onError("Error fetching meals by category: " +response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable throwable) {
                networkCallBack.onError("Network error: " + throwable.getMessage());
            }
        });
    }
}
