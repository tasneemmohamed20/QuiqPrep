package com.example.fragmentsbonus.models.repository;

import com.example.fragmentsbonus.database.MealsLocalDataSource;
import com.example.fragmentsbonus.models.categories.CategoryResponse;
import com.example.fragmentsbonus.models.meals.MealResponse;
import com.example.fragmentsbonus.models.meals.MealsItem;
import com.example.fragmentsbonus.network.MealsRemoteDataSource;


import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealsRepositoryImplementation implements  MealsRepository {
    private final MealsRemoteDataSource remoteDataSource;
    private final MealsLocalDataSource localDataSource;
    private static  MealsRepositoryImplementation repo = null;

    public MealsRepositoryImplementation(MealsRemoteDataSource remoteDataSource, MealsLocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public static MealsRepositoryImplementation getInstance(MealsRemoteDataSource remoteDataSource, MealsLocalDataSource localDataSource){
        if(repo == null){
            repo = new MealsRepositoryImplementation(remoteDataSource, localDataSource);
        }
        return repo;
    }

    @Override
    public Flowable<List<MealsItem>> getStoredMeals() {
        return localDataSource.getAllStoredMeals();
    }

    @Override
    public Single<MealResponse> getRandomMeals() {
        return remoteDataSource.getRandomMeals();
    }

    @Override
    public Single<CategoryResponse> getCategories() {
        return remoteDataSource.getCategories();
    }

    @Override
    public  Single<MealResponse> getMealsByCategory(String category) {
        return remoteDataSource.getMealsByCategory(category);
    }

    @Override
    public Single<MealResponse> getMealById(String id) {
        return remoteDataSource.getMealById(id);
    }

    @Override
    public void dispose() {
        remoteDataSource.dispose();
    }

    // Local Data Source Methods
    @Override
    public Completable insertMeal(MealsItem mealsItem) {
        return Completable.fromAction(() -> localDataSource.insertMeal(mealsItem).blockingAwait());
    }

    @Override
    public Completable deleteMeal(MealsItem mealsItem) {
        return Completable.fromAction(() -> localDataSource.deleteMeal(mealsItem).blockingAwait());
    }
}
