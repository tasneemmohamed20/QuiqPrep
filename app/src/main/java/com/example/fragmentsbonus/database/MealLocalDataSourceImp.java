package com.example.fragmentsbonus.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.fragmentsbonus.home.model.random_meal.MealsItem;

import java.util.List;

public class MealLocalDataSourceImp implements MealsLocalDataSource {
    private final MealDao mealsDao;

    public MealLocalDataSourceImp(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.mealsDao = db.mealsDao();
    }
    @Override
    public void insertMeal(MealsItem meal) {
        new Thread(() -> {
            mealsDao.insertMeal(meal);
        }).start();

    }

    @Override
    public void deleteMeal(MealsItem meal) {
        new Thread(() -> {
            mealsDao.deleteMeal(meal);
        }).start();
    }

    @Override
    public LiveData<List<MealsItem>> getAllStoredMeals() {
        try {
            return mealsDao.getAllMeals();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
