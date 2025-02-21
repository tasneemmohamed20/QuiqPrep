package com.example.fragmentsbonus.details.presenter.details;

import com.example.fragmentsbonus.models.meals.MealsItem;

public interface DetailsPresenter {
    void loadMealDetails();
    void detachView();

    void addMealToFavorite(MealsItem mealsItem);
    void removeMealFromFavorite(MealsItem mealsItem);
    void onDeleteConfirmed(MealsItem mealsItem);
    void handleLikeButtonClick(MealsItem meal, boolean isFavorite);

}
