package com.example.fragmentsbonus.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.example.fragmentsbonus.models.meals.MealsItem;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDao {
    @Query("SELECT * FROM meals")
    Flowable<List<MealsItem>> getAllMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(MealsItem meal);

    @Delete
    Completable deleteMeal(MealsItem meal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable scheduleMeal(MealsItem meal);

    @Query("SELECT * FROM meals WHERE scheduleDate = :date")
    Flowable<List<MealsItem>> getMealsForDate(String date);

    @Delete
    Completable deleteScheduledMeal(MealsItem meal);

    @Query("SELECT * FROM meals WHERE isFavorite = 1")
    Flowable<List<MealsItem>> getFavoriteMeals();

    @Query("DELETE FROM meals")
    Completable clearAllData();
}
