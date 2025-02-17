package com.example.fragmentsbonus.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.example.fragmentsbonus.models.meals.MealsItem;

import java.util.List;

@Dao
public interface MealDao {
    @Query("SELECT * FROM meals")
    LiveData<List<MealsItem>> getAllMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(MealsItem meal);

    @Delete
    void deleteMeal(MealsItem meal);
}
