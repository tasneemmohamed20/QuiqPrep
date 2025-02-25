package com.example.fragmentsbonus.models.repository;


import com.example.fragmentsbonus.models.categories.CategoryResponse;
import com.example.fragmentsbonus.models.countries.CountriesResponse;
import com.example.fragmentsbonus.models.ingredients.IngredientResponse;
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
  Single<IngredientResponse> getIngredients();
  Single<CountriesResponse> getCountries();
  Single<MealResponse> getMealsByIngredient(String ingredient);
  Single<MealResponse> getMealsByCountry(String country);
  Single<MealResponse> getMealById(String id);
  Completable insertMeal(MealsItem mealsItem);
  Completable deleteMeal(MealsItem mealsItem);
  Completable scheduleMeal(MealsItem meal);
  Completable deleteScheduledMeal(MealsItem meal);
  Flowable<List<MealsItem>> getFavoriteMeals();
  Completable syncFromFirestore();
  Completable clearAllData();

}
