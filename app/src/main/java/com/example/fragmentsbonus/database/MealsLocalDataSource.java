package com.example.fragmentsbonus.database;

import androidx.lifecycle.LiveData;

import com.example.fragmentsbonus.models.meals.MealsItem;

import java.util.List;

public interface MealsLocalDataSource {
    void insertMeal(MealsItem meal);
    void deleteMeal(MealsItem meal);
    LiveData<List<MealsItem>> getAllStoredMeals();
}
