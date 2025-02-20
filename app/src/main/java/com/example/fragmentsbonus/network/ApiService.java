package com.example.fragmentsbonus.network;

import com.example.fragmentsbonus.models.categories.CategoryResponse;
import com.example.fragmentsbonus.models.meals.MealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET(api_strings.MEAL_OF_DAY)
    Single<MealResponse> getRandomMeal();

    @GET(api_strings.CATEGORIES)
    Single<CategoryResponse> getCategories();

    @GET(api_strings.CATEGORIES_MEAL)
    Single<MealResponse> getMealByCategory(@Query("c") String category);

    @GET(api_strings.MEAL_BY_ID)
    Single<MealResponse> getMealById(@Query("i") String id);
}