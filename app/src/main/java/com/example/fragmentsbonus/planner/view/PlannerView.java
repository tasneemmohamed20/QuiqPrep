package com.example.fragmentsbonus.planner.view;

import com.example.fragmentsbonus.models.meals.MealsItem;

import java.util.List;

public interface PlannerView {
    void showDatePicker();
    void showMealForDate(List<MealsItem> meal, String date);
    void showError(String message);
    void showCalendar();

}
