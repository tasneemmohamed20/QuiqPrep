package com.example.fragmentsbonus.details.presenter.details;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.fragmentsbonus.database.MealLocalDataSourceImp;
import com.example.fragmentsbonus.database.MealsLocalDataSource;
import com.example.fragmentsbonus.details.view.details.DetailsView;
import com.example.fragmentsbonus.models.meals.MealsItem;
import com.example.fragmentsbonus.models.repository.MealsRepository;
import com.example.fragmentsbonus.models.repository.MealsRepositoryImplementation;
import com.example.fragmentsbonus.network.MealsRemoteDataSource;
import com.example.fragmentsbonus.network.MealsRemoteDataSourceImplementation;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailsPresenterImp implements DetailsPresenter {
    private DetailsView view;
    private final MealsItem meal;

    private final MealsRepository repository;

    private Disposable disposable;

    private static final String TAG = "DetailsPresenterImp";

    private final Context context;

    public DetailsPresenterImp(DetailsView view, MealsItem meal, Context context) {
        this.view = view;
        this.meal = meal;
        this.context = context;
        MealsRemoteDataSource remoteDataSource = MealsRemoteDataSourceImplementation.getInstance(context);
        MealsLocalDataSource localDataSource = MealLocalDataSourceImp.getInstance(context);
        repository = MealsRepositoryImplementation.getInstance(remoteDataSource, localDataSource);
        checkFavoriteStatus();
    }

    @Override
    public void loadMealDetails() {
        if (meal != null) {
            view.displayMealDetails(meal);
        } else {
            view.showError("Meal details not found");
        }
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void checkFavoriteStatus() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        disposable = repository.getStoredMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealsItems -> {
                                    boolean isFavorite = mealsItems.stream()
                                    .anyMatch(item -> item.getIdMeal().equals(meal.getIdMeal()));
                            view.updateFavoriteStatus(isFavorite);
                        }, throwable ->
                                Log.e(TAG, "Error checking favorite status", throwable)
                );
    }

    @Override
    public void checkScheduleStatus(MealsItem meal) {
        if (meal == null) return;

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        disposable = repository.getStoredMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealsItems -> {
                    boolean isScheduled = mealsItems.stream()
                            .anyMatch( item ->
                                    {
                                        return (item.getIdMeal().equals(meal.getIdMeal())
                                                && (item.getScheduleDate() != null || !item.getScheduleDate().isEmpty()));
                                    }
                            );

                    if (view != null) {
                        view.updateScheduleStatus(isScheduled);
                    }
                }, throwable -> Log.e(TAG, "Error checking schedule status", throwable));
    }
    
    @Override
    public void handleScheduleButtonClick(MealsItem meal) {
        if (meal != null) {
            if (meal.getScheduleDate() == null) {
                view.showDatePicker();
            } else {
                view.showUnScheduleConfirmation(meal);
            }
        }
    }

    @Override
    public void addMealToFavorite(MealsItem mealsItem) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
            meal.setFavorite(true);
        disposable = repository.insertMeal(mealsItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Toast.makeText(context, "Meal added to favorites", Toast.LENGTH_SHORT).show();
                    view.updateFavoriteStatus(true);
                    checkFavoriteStatus();
                }, throwable -> {
                    view.showError("Failed to add to favorites");
                    Log.e(TAG, "Error adding to favorites", throwable);
                });
    }

    @Override
    public void removeMealFromFavorite(MealsItem mealsItem) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        mealsItem.setFavorite(false);
        disposable = repository.deleteMeal(mealsItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    if (view != null) {
                        Toast.makeText(context, "Meal removed from favorites", Toast.LENGTH_SHORT).show();
                        view.updateFavoriteStatus(false);
                    }

                }, throwable -> {
                    if (view != null) {
                        view.showError("Failed to remove from favorites");
                        Log.e(TAG, "Error removing from favorites", throwable);
                    }
                });
    }

    @Override
    public void onDeleteConfirmed(MealsItem mealsItem) {
        removeMealFromFavorite(mealsItem);
    }

    @Override
    public void handleLikeButtonClick(MealsItem meal, boolean isFavorite) {
        if(meal != null){
            if(!isFavorite){
                addMealToFavorite(meal);
            } else {
                view.showDeleteConfirmation(meal);
            }
        }
    }

    @Override
    public void addMealToSchedule(MealsItem meal) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        disposable = repository.scheduleMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    view.updateScheduleStatus(true);
                    Toast.makeText(context, "Meal scheduled", Toast.LENGTH_SHORT).show();
//                    checkScheduleStatus(meal);
                }, throwable -> view.showError("Failed to schedule meal"));
    }

    @Override
    public void removeMealFromSchedule(MealsItem meal) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        meal.setScheduleDate(null);
        disposable = repository.deleteScheduledMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    if (view != null) {
                        view.updateScheduleStatus(false);
                        Toast.makeText(context, "Meal unscheduled", Toast.LENGTH_SHORT).show(); // Fix message
                    }
//                    checkScheduleStatus(meal);
                }, throwable -> view.showError("Failed to unschedule meal"));
    }

}