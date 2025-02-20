package com.example.fragmentsbonus.details.view.details;

import com.example.fragmentsbonus.models.meals.MealsItem;

public interface DetailsView {
    void showError(String message);
    void displayMealDetails(MealsItem meal);
}
