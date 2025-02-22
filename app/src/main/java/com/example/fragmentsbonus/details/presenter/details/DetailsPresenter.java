package com.example.fragmentsbonus.details.presenter.details;

import com.example.fragmentsbonus.models.meals.MealsItem;

public interface DetailsPresenter {
    void loadMealDetails();
    void detachView();
    void addMealToFavorite(MealsItem mealsItem);
    void removeMealFromFavorite(MealsItem mealsItem);
    void onDeleteConfirmed(MealsItem mealsItem);
    void handleLikeButtonClick(MealsItem meal, boolean isFavorite);
    void addMealToSchedule(MealsItem meal);
    void removeMealFromSchedule(MealsItem meal);

    void checkFavoriteStatus();
    void checkScheduleStatus(MealsItem meal);

    void handleScheduleButtonClick(MealsItem meal);
}
