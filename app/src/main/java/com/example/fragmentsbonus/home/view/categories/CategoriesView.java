package com.example.fragmentsbonus.home.view.categories;

import com.example.fragmentsbonus.models.categories.CategoriesItem;

import java.util.List;


public interface CategoriesView {
    void showLoading();
    void hideLoading();
    void showCategories(List<CategoriesItem> categories);
    void onErrorLoading(String message);
//    CategoriesAdapter getAdapter();
}
