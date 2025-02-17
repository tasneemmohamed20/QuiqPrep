package com.example.fragmentsbonus.models.repository;

import androidx.lifecycle.LiveData;

import com.example.fragmentsbonus.database.MealsLocalDataSource;
import com.example.fragmentsbonus.models.meals.MealsItem;
import com.example.fragmentsbonus.network.MealsRemoteDataSource;
import com.example.fragmentsbonus.network.NetworkCallBack;

import java.util.List;

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
    public LiveData<List<MealsItem>> getStoredMeals() {
        return localDataSource.getAllStoredMeals();
    }

    @Override
    public void getRandomMeals(NetworkCallBack networkCallBack) {
        remoteDataSource.getRandomMeals(new NetworkCallBack(){

            @Override
            public void onSuccess(Object response) {
                networkCallBack.onSuccess(response);
            }

            @Override
            public void onError(String error) {
                networkCallBack.onError(error);
            }
        });
    }

    @Override
    public void getCategories(NetworkCallBack networkCallBack) {
        remoteDataSource.getCategories(new NetworkCallBack(){

            @Override
            public void onSuccess(Object response) {
                networkCallBack.onSuccess(response);
            }

            @Override
            public void onError(String error) {
                networkCallBack.onError(error);
            }
        });
    }

    @Override
    public void getMealsByCategory(String category, NetworkCallBack networkCallBack) {
        remoteDataSource.getMealsByCategory(category, new NetworkCallBack(){

            @Override
            public void onSuccess(Object response) {
                networkCallBack.onSuccess(response);
            }

            @Override
            public void onError(String error) {
                networkCallBack.onError(error);
            }
        });
    }

    @Override
    public void insertMeal(MealsItem mealsItem) {
        localDataSource.insertMeal(mealsItem);
    }

    @Override
    public void deleteMeal(MealsItem mealsItem) {
        localDataSource.deleteMeal(mealsItem);
    }
}
