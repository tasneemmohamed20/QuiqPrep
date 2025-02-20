package com.example.fragmentsbonus.models.repository;

import androidx.lifecycle.LiveData;

import com.example.fragmentsbonus.models.categories.CategoryResponse;
import com.example.fragmentsbonus.models.meals.MealResponse;
import com.example.fragmentsbonus.models.meals.MealsItem;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MealsRepository {
  LiveData<List<MealsItem>> getStoredMeals();
  Single<MealResponse> getRandomMeals();
  Single<CategoryResponse> getCategories();
  Single<MealResponse> getMealsByCategory(String category);
  Single<MealResponse> getMealById(String id);
  void insertMeal(MealsItem mealsItem);
  void deleteMeal(MealsItem mealsItem);
  void dispose();

}
