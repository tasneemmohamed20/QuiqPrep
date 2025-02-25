package com.example.fragmentsbonus.network;

import com.example.fragmentsbonus.models.categories.CategoryResponse;
import com.example.fragmentsbonus.models.countries.CountriesResponse;
import com.example.fragmentsbonus.models.ingredients.IngredientResponse;
import com.example.fragmentsbonus.models.meals.MealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET(api_strings.MEAL_OF_DAY)
    Single<MealResponse> getRandomMeal();

    @GET(api_strings.CATEGORIES)
    Single<CategoryResponse> getCategories();

    @GET(api_strings.FILTER)
    Single<MealResponse> getMealByCategory(@Query("c") String category);

    @GET(api_strings.INGREDIENTS)
    Single<IngredientResponse> getIngredients();

    @GET(api_strings.COUNTRIES)
    Single<CountriesResponse> getCountries();

    @GET(api_strings.FILTER)
    Single<MealResponse> getMealsByIngredient(@Query("i") String category);

    @GET(api_strings.FILTER)
    Single<MealResponse> getMealsByCountry(@Query("a") String category);

    @GET(api_strings.MEAL_BY_ID)
    Single<MealResponse> getMealById(@Query("i") String id);
}