package com.example.fragmentsbonus.details.view.ingredients;

import com.example.fragmentsbonus.models.meals.MealsItem;

public interface IngredientsView {
    void displayIngredients(MealsItem meal);
    void showError(String message);
    void showLoading();
    void hideLoading();
}
