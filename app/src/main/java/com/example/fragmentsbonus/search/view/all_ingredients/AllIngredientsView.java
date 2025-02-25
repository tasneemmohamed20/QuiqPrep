package com.example.fragmentsbonus.search.view.all_ingredients;

import com.example.fragmentsbonus.models.ingredients.IngredientItem;

import java.util.List;

public interface AllIngredientsView {
    void showIngredients(List<IngredientItem> ingredients);
    void showLoading();
    void hideLoading();
    void setupSearchObservable();
    void showErrorMessage(String message);
    void onIngredientClick();
}
