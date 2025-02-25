package com.example.fragmentsbonus.search.presenter.filtered.by_country;

public interface CounFilterPresenter {
    void getMealsByCountry(String country);
    void dispose();
    void filterMeals(String query);

}
