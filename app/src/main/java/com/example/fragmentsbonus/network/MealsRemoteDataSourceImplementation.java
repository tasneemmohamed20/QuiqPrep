package com.example.fragmentsbonus.network;


import androidx.annotation.NonNull;

import com.example.fragmentsbonus.models.categories.CategoryResponse;
import com.example.fragmentsbonus.models.meals.MealResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSourceImplementation implements MealsRemoteDataSource {
    private static final String TAG = "MealsClient";
    private static MealsRemoteDataSourceImplementation client = null;
    private final ApiService apiService;

    private MealsRemoteDataSourceImplementation() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api_strings.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())

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
    public  Single<MealResponse> getRandomMeals() {
        return apiService.getRandomMeal();
    }

    @Override
    public  Single<CategoryResponse> getCategories() {
        return apiService.getCategories();
    }

    @Override
    public  Single<MealResponse> getMealsByCategory(String category) {
        return apiService.getMealByCategory(category);
    }

    @Override
    public Single<MealResponse> getMealById(String id) {
        return apiService.getMealById(id);
    }

    @Override
    public void dispose() {
        client = null;
    }

}
