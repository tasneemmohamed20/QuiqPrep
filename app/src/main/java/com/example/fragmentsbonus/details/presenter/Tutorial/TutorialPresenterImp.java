package com.example.fragmentsbonus.details.presenter.Tutorial;

import com.example.fragmentsbonus.details.view.tutorial.TutorialView;
import com.example.fragmentsbonus.models.meals.MealsItem;

public class TutorialPresenterImp implements TutorialPresenter {
    private TutorialView view;
    private MealsItem meal;

    public TutorialPresenterImp(TutorialView view, MealsItem meal) {
        this.view = view;
        this.meal = meal;
    }

    @Override
    public void loadVideo() {
        if (view != null) {
            if (meal != null && meal.getStrYoutube() != null) {
                view.displayVideo(meal.getStrYoutube());
            } else {
                view.showError("Video not available");
            }
        }
    }

    @Override
    public void detachView() {
        view = null;
    }
}
