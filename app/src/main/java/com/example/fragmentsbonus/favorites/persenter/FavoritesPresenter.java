package com.example.fragmentsbonus.favorites.persenter;

import com.example.fragmentsbonus.models.meals.MealsItem;

public interface FavoritesPresenter {
    void getFavorites();
    void removeFavorite(MealsItem meal);
    void onDestroy();
}
