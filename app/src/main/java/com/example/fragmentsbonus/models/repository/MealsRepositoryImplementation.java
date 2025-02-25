package com.example.fragmentsbonus.models.repository;

import com.example.fragmentsbonus.database.MealsLocalDataSource;
import com.example.fragmentsbonus.models.categories.CategoryResponse;
import com.example.fragmentsbonus.models.countries.CountriesResponse;
import com.example.fragmentsbonus.models.ingredients.IngredientResponse;
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
    public Single<IngredientResponse> getIngredients() {
        return remoteDataSource.getIngredients();
    }

    @Override
    public Single<CountriesResponse> getCountries() {
        return remoteDataSource.getCountries();
    }

    @Override
    public Single<MealResponse> getMealsByIngredient(String ingredient) {
        return remoteDataSource.getMealsByIngredient(ingredient);
    }

    @Override
    public Single<MealResponse> getMealsByCountry(String country) {
        return remoteDataSource.getMealsByCountry(country);
    }

    // Local Data Source Methods

    @Override
    public Flowable<List<MealsItem>> getStoredMeals() {
        return localDataSource.getAllStoredMeals();
    }

    @Override
    public Completable insertMeal(MealsItem meal) {
        return getStoredMeals()
                .firstElement()
                .flatMapCompletable(meals -> {
                    // Find existing meal if any
                    MealsItem existingMeal = meals.stream()
                            .filter(m -> m.getIdMeal().equals(meal.getIdMeal()))
                            .findFirst()
                            .orElse(null);

                    // Preserve schedule date if exists
                    if (existingMeal != null) {
                        meal.setScheduleDate(existingMeal.getScheduleDate());
                    }

                    // Now insert the meal
                    return Completable.fromAction(() ->
                            localDataSource.insertMeal(meal).blockingAwait());
                });
    }

    @Override
    public Completable deleteMeal(MealsItem mealsItem) {
        return getStoredMeals()
                .firstElement()
                .flatMapCompletable(meals -> {
                    // Find existing meal to check schedule date
                    MealsItem existingMeal = meals.stream()
                            .filter(m -> m.getIdMeal().equals(mealsItem.getIdMeal()))
                            .findFirst()
                            .orElse(null);

                    if (existingMeal != null) {
                        // If meal has schedule date, keep it in DB with updated favorite status
                        if (existingMeal.getScheduleDate() != null && !existingMeal.getScheduleDate().isEmpty()) {
                            mealsItem.setScheduleDate(existingMeal.getScheduleDate());
                            mealsItem.setFavorite(false);
                            return localDataSource.insertMeal(mealsItem);
                        } else {
                            // If no schedule date, completely remove the meal
                            return localDataSource.deleteMeal(mealsItem);
                        }
                    }
                    return localDataSource.deleteMeal(mealsItem);
                });
    }

    @Override
    public Completable scheduleMeal(MealsItem meal) {
        return getStoredMeals()
        .firstElement()
                .flatMapCompletable(meals -> {
                    // Find existing meal if any
                    MealsItem existingMeal = meals.stream()
                            .filter(m -> m.getIdMeal().equals(meal.getIdMeal()))
                            .findFirst()
                            .orElse(null);

                    // Preserve favorite status if exists
                    if (existingMeal != null) {
                        meal.setFavorite(existingMeal.isFavorite());
                    }

                    // Now schedule the meal
                    return Completable.fromAction(() ->
                            localDataSource.scheduleMeal(meal).blockingAwait());
                });
    }

    @Override
    public Completable deleteScheduledMeal(MealsItem meal) {
        return getStoredMeals()
                .firstElement()
                .flatMapCompletable(meals -> {
                    // Find existing meal to preserve favorite status
                    MealsItem existingMeal = meals.stream()
                            .filter(m -> m.getIdMeal().equals(meal.getIdMeal()))
                            .findFirst()
                            .orElse(null);

                        if (existingMeal != null && existingMeal.isFavorite()) {
                            // If meal is favorite, keep it in DB with updated schedule status
                            meal.setFavorite(existingMeal.isFavorite());
                            meal.setScheduleDate(null);
                            return localDataSource.insertMeal(meal);
                        }else {
                            // If not favorite, completely remove the meal
                            return localDataSource.deleteScheduledMeal(meal);
                        }
//                    return localDataSource.deleteScheduledMeal(meal);
                });
    }

    @Override
    public Flowable<List<MealsItem>> getFavoriteMeals() {
        return localDataSource.getFavoriteMeals();
    }


}
