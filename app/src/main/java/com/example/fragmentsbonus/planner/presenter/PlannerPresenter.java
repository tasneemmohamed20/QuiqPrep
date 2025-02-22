package com.example.fragmentsbonus.planner.presenter;

public interface PlannerPresenter {
    void onCalendarButtonClicked();
    void onDateSelected(Long date);
    void loadMealForDate(String date);
    void detachView();
}
