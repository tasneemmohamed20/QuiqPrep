package com.example.fragmentsbonus.search.view.filtered;

import com.example.fragmentsbonus.models.meals.MealsItem;

import java.util.List;

public interface FilteredView {
    void showFilteredMeals(List<MealsItem> meals);
    void showFilteredError(String error);
    void showLoading();
    void hideLoading();
    void OnMealClick();
    void setupSearchObservable();
}
