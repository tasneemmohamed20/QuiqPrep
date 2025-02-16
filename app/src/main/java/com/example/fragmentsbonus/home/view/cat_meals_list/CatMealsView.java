package com.example.fragmentsbonus.home.view.cat_meals_list;

import com.example.fragmentsbonus.home.model.random_meal.MealsItem;

import java.util.List;

public interface CatMealsView {
    void showLoading();
    void hideLoading();
    void showMeals(List<MealsItem> meals);
    void onErrorLoading(String message);
}
