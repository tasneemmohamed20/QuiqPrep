package com.example.fragmentsbonus.search.presenter.filtered.by_ingredient;

public interface IngFilterPresenter {
    void getMealsByIngredient(String ingredient);
    void dispose();
    void filterMeals(String query);

}
