package com.example.fragmentsbonus.search.presenter.filtered.by_category;

public interface CatFilterPresenter {
    void getMealsByCategory(String category);
    void dispose();
    void filterMeals(String query);

}
