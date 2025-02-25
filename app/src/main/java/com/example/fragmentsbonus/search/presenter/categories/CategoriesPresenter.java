package com.example.fragmentsbonus.search.presenter.categories;

public interface CategoriesPresenter {
    void getCategories();

    void filterCategories(String query);
    void detachView();
}
