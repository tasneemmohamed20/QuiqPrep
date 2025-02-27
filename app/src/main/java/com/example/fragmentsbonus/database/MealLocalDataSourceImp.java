package com.example.fragmentsbonus.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.fragmentsbonus.models.meals.MealsItem;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealLocalDataSourceImp implements MealsLocalDataSource {
    private final MealDao mealsDao;
    private static MealLocalDataSourceImp instance;
    private final FirestoreHelper firestoreHelper;


    public MealLocalDataSourceImp(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.mealsDao = db.mealsDao();
        this.firestoreHelper = new FirestoreHelper();
    }

    public static MealsLocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new MealLocalDataSourceImp(context);
        }
        return instance;
    }

    @Override
    public Completable insertMeal(MealsItem meal) {

        return mealsDao.insertMeal(meal)
                .andThen(Completable.fromAction(() -> {
            firestoreHelper.syncMealToFirestore(meal)
                    .addOnFailureListener(e ->
                            Log.e("Firestore", "Failed to sync meal: " + e.getMessage())
                    );
        }));
    }

    @Override
    public Completable deleteMeal(MealsItem meal) {

        return mealsDao.deleteMeal(meal)
                .andThen(Completable.fromAction(() -> {
            firestoreHelper.deleteMealFromFirestore(meal)
                    .addOnFailureListener(e ->
                            Log.e("Firestore", "Failed to delete meal: " + e.getMessage())
                    );
        }));
    }

    @Override
    public Flowable<List<MealsItem>> getAllStoredMeals() {
        return mealsDao.getAllMeals();
    }

    @Override
    public Completable scheduleMeal(MealsItem meal) {
        return mealsDao.scheduleMeal(meal)
                .andThen(Completable.fromAction(() -> {
                    firestoreHelper.syncMealToFirestore(meal)
                            .addOnFailureListener(e ->
                                    Log.e("Firestore", "Failed to sync scheduled meal: " + e.getMessage())
                            );
                }));
    }

    @Override
    public Flowable<List<MealsItem>> getMealsForDate(String date) {
        return mealsDao.getMealsForDate(date);
    }

    @Override
    public Completable deleteScheduledMeal(MealsItem meal) {
        return mealsDao.deleteScheduledMeal(meal)
                .andThen(Completable.fromAction(() -> {
                    firestoreHelper.deleteMealFromFirestore(meal)
                            .addOnFailureListener(e ->
                                    Log.e("Firestore", "Failed to delete scheduled meal: " + e.getMessage())
                            );
                }));
    }

    @Override
    public Flowable<List<MealsItem>> getFavoriteMeals() {
        return mealsDao.getFavoriteMeals();
    }

    @Override
    public Completable syncFromFirestore() {
        return Completable.create(emitter -> {
            firestoreHelper.retrieveUserMeals()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<MealsItem> meals = queryDocumentSnapshots.toObjects(MealsItem.class);
                        // Insert all meals into local database
                        Completable.concat(meals.stream()
                                        .map(meal -> insertMeal(meal)
                                                .subscribeOn(Schedulers.io()))
                                        .collect(Collectors.toList()))
                                .subscribeOn(Schedulers.io())
                                .subscribe(
                                        () -> emitter.onComplete(),
                                        emitter::onError
                                );
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Firestore", "Failed to retrieve meals: " + e.getMessage());
                        emitter.onError(e);
                    });
        });
    }

    @Override
    public Completable clearAllData() {
        return mealsDao.clearAllData();
    }
}
