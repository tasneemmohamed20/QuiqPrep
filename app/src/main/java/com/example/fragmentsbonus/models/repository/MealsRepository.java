package com.example.fragmentsbonus.models.repository;

import androidx.lifecycle.LiveData;

import com.example.fragmentsbonus.models.categories.CategoryResponse;
import com.example.fragmentsbonus.models.meals.MealResponse;
import com.example.fragmentsbonus.models.meals.MealsItem;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealsRepository {
  Flowable<List<MealsItem>> getStoredMeals();
  Single<MealResponse> getRandomMeals();
  Single<CategoryResponse> getCategories();
  Single<MealResponse> getMealsByCategory(String category);
  Single<MealResponse> getMealById(String id);
  Completable insertMeal(MealsItem mealsItem);
  Completable deleteMeal(MealsItem mealsItem);
  void dispose();

}
