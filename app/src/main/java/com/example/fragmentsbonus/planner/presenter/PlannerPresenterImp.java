package com.example.fragmentsbonus.planner.presenter;


import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;

import com.example.fragmentsbonus.database.MealLocalDataSourceImp;
import com.example.fragmentsbonus.database.MealsLocalDataSource;
import com.example.fragmentsbonus.models.meals.MealsItem;
import com.example.fragmentsbonus.models.repository.MealsRepository;
import com.example.fragmentsbonus.models.repository.MealsRepositoryImplementation;
import com.example.fragmentsbonus.network.MealsRemoteDataSource;
import com.example.fragmentsbonus.network.MealsRemoteDataSourceImplementation;
import com.example.fragmentsbonus.planner.view.PlannerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlannerPresenterImp implements PlannerPresenter {
    private PlannerView view;
    private final MealsRepository repository;
    private final Context context;
    private Disposable disposable;

    public PlannerPresenterImp(PlannerView view, Context context) {
        this.view = view;
        this.context = context;
        this.repository = MealsRepositoryImplementation.getInstance(
                MealsRemoteDataSourceImplementation.getInstance(),
                MealLocalDataSourceImp.getInstance(context)
        );
    }

    @Override
    public void onCalendarButtonClicked() {
        if (view != null) {
            view.showDatePicker();
        }
    }

    @Override
    public void onDateSelected(Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = sdf.format(new Date(date));
        loadMealForDate(dateString);
        view.showCalendar(); // Show calendar after date selection
    }

    @Override
    public void loadMealForDate(String date) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        // Fix: Don't cast single meal to List
        disposable = repository.getStoredMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    List<MealsItem> mealsForDate = meals.stream()
                            .filter(meal -> date.equals(meal.getScheduleDate()))
                            .collect(Collectors.toList());
                    view.showMealForDate(mealsForDate, date);
                }, throwable -> {
                    if (view != null) {
                        view.showError("Error loading meals: " + throwable.getMessage());
                    }
                });
    }

    @Override
    public void detachView() {
        view = null;
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}