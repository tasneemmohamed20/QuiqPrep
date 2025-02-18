package com.example.fragmentsbonus.details.presenter.instructions;

import com.example.fragmentsbonus.details.view.instructions.InstructionsView;
import com.example.fragmentsbonus.models.meals.MealsItem;

public class InstructionsPresenterImp implements InstructionsPresenter {
    private InstructionsView view;
    private MealsItem meal;

    public InstructionsPresenterImp(InstructionsView view, MealsItem meal) {
        this.view = view;
        this.meal = meal;
    }

    @Override
    public void loadInstructions() {
        if (view != null) {
            if (meal != null && meal.getStrInstructions() != null) {
                view.displayInstructions(meal.getStrInstructions());
            } else {
                view.showError("Instructions not available");
            }
        }
    }

    @Override
    public void detachView() {
        view = null;
    }
}