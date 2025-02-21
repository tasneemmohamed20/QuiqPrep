package com.example.fragmentsbonus.favorites.view;

import com.example.fragmentsbonus.models.meals.MealsItem;

import java.util.List;

public interface FavoritesView {
    void showFavorites(List<MealsItem> meals);
    void showError(String message);
    void showLoading();
    void hideLoading();
}
