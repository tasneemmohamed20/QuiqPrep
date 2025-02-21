package com.example.fragmentsbonus.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.fragmentsbonus.models.meals.MealsItem;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

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
    public Completable insertMeal(MealsItem meal) {
        return mealsDao.insertMeal(meal);
    }

    @Override
    public Completable deleteMeal(MealsItem meal) {
        return mealsDao.deleteMeal(meal);
    }

    @Override
    public Flowable<List<MealsItem>> getAllStoredMeals() {
        return mealsDao.getAllMeals();
    }
}
