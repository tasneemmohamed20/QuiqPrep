package com.example.fragmentsbonus.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.fragmentsbonus.models.meals.MealsItem;

import java.util.List;

public class MealLocalDataSourceImp implements MealsLocalDataSource {
    private final MealDao mealsDao;
    private static MealLocalDataSourceImp instance;

    public MealLocalDataSourceImp(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.mealsDao = db.mealsDao();
    }

    public static MealsLocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new MealLocalDataSourceImp(context);
        }
        return instance;
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
