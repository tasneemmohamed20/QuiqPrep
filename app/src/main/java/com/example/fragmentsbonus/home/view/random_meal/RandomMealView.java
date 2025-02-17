package com.example.fragmentsbonus.home.view.random_meal;

import com.example.fragmentsbonus.models.meals.MealsItem;

public interface RandomMealView {
    void showLoading();
    void hideLoading();
    void showMeal(MealsItem mealsItem);
    void onErrorLoading(String message);
}
