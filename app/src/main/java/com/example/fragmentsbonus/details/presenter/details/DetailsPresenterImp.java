package com.example.fragmentsbonus.details.presenter.details;

import com.example.fragmentsbonus.details.view.details.DetailsView;
import com.example.fragmentsbonus.models.meals.MealsItem;

public class DetailsPresenterImp implements DetailsPresenter {
    private DetailsView view;
    private MealsItem meal;

    public DetailsPresenterImp(DetailsView view, MealsItem meal) {
        this.view = view;
        this.meal = meal;
    }

    @Override
    public void loadMealDetails() {
        if (meal != null) {
            view.displayMealDetails(meal);
        } else {
            view.showError("Meal details not found");
        }
    }

    @Override
    public void detachView() {
        view = null;
    }
}