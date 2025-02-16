package com.example.fragmentsbonus.network;

import com.example.fragmentsbonus.home.model.categories.CategoryResponse;
import com.example.fragmentsbonus.home.model.random_meal.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET(api_strings.MEAL_OF_DAY)
    Call<MealResponse> getRandomMeal();

    @GET(api_strings.CATEGORIES)
    Call<CategoryResponse> getCategories();

    @GET(api_strings.CATEGORIES_MEAL)
    Call<MealResponse> getMealByCategory(@Query("c") String category);
}