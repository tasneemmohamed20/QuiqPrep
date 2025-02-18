package com.example.fragmentsbonus.details.presenter.ingredients;

import com.example.fragmentsbonus.details.view.ingredients.IngredientsView;
import com.example.fragmentsbonus.models.meals.MealsItem;

public class IngredientsPresenterImp implements IngredientsPresenter {
    private IngredientsView view;
    private MealsItem meal;

    public IngredientsPresenterImp(IngredientsView view, MealsItem meal) {
        this.view = view;
        this.meal = meal;
    }

    @Override
    public void loadIngredients() {
        if (meal != null) {
            view.displayIngredients(meal);
        } else {
            view.showError("Ingredients not found");
        }
    }

    @Override
    public void detachView() {
        view = null;
    }
}
