package com.example.fragmentsbonus.models.repository;

import androidx.lifecycle.LiveData;

import com.example.fragmentsbonus.models.meals.MealsItem;
import com.example.fragmentsbonus.network.NetworkCallBack;

import java.util.List;

public interface MealsRepository {
  LiveData<List<MealsItem>> getStoredMeals();
  void getRandomMeals(NetworkCallBack networkCallBack);
  void getCategories(NetworkCallBack networkCallBack);
  void getMealsByCategory(String category, NetworkCallBack networkCallBack);
  void insertMeal(MealsItem mealsItem);
  void deleteMeal(MealsItem mealsItem);


}
