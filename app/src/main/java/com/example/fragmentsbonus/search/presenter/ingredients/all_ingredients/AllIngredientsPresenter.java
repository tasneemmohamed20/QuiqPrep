package com.example.fragmentsbonus.search.presenter.ingredients.all_ingredients;

public interface AllIngredientsPresenter {
    void getIngredients();
    void filterIngredients(String query);
    void detachView();
}
