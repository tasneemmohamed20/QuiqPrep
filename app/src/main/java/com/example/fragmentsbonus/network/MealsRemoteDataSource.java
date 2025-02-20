package com.example.fragmentsbonus.network;

import com.example.fragmentsbonus.models.categories.CategoryResponse;
import com.example.fragmentsbonus.models.meals.MealResponse;

import io.reactivex.rxjava3.core.Single;

public interface MealsRemoteDataSource {
    Single<MealResponse> getRandomMeals();
    Single<CategoryResponse> getCategories();
    Single<MealResponse> getMealsByCategory(String category);

    Single<MealResponse> getMealById(String id);

    void dispose();
}
