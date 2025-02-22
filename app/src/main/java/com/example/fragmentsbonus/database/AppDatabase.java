package com.example.fragmentsbonus.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fragmentsbonus.models.meals.MealsItem;

@Database(entities = {MealsItem.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MealDao mealsDao();
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "meals_database"
            ).build();
        }
        return instance;
    }
}
