package com.example.fragmentsbonus.database;

import com.example.fragmentsbonus.models.meals.MealsItem;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface MealsLocalDataSource {
    Completable insertMeal(MealsItem meal);
    Completable deleteMeal(MealsItem meal);
    Flowable<List<MealsItem>> getAllStoredMeals();
    Completable scheduleMeal(MealsItem meal);
    Flowable <List<MealsItem>> getMealsForDate(String date);
    Completable deleteScheduledMeal(MealsItem meal);
    Flowable<List<MealsItem>> getFavoriteMeals();
    Completable syncFromFirestore();
    Completable clearAllData();

}
