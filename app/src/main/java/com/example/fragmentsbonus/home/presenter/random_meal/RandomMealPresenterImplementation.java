package com.example.fragmentsbonus.home.presenter.random_meal;

import android.content.Context;
import com.example.fragmentsbonus.database.MealLocalDataSourceImp;
import com.example.fragmentsbonus.home.view.random_meal.RandomMealView;
import com.example.fragmentsbonus.models.repository.MealsRepositoryImplementation;
import com.example.fragmentsbonus.network.MealsRemoteDataSourceImplementation;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RandomMealPresenterImplementation implements RandomMealPresenter {

    private RandomMealView view;
    private final MealsRepositoryImplementation repository;

    private Disposable disposable;
    public RandomMealPresenterImplementation(RandomMealView view, Context context) {
        this.view = view;
        this.repository = MealsRepositoryImplementation.getInstance(MealsRemoteDataSourceImplementation.getInstance(), MealLocalDataSourceImp.getInstance(context));
    }

    @Override
    public void loadRandomMeal() {

        view.showLoading();
        disposable = repository.getRandomMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (view != null) {
                                view.hideLoading();
                                view.showMeal(response.getMeals().get(0));
                            }
                        },
                        error -> {
                            if (view != null) {
                                view.hideLoading();
                                view.onErrorLoading(error.getMessage());
                            }
                        }
                );
    }
    @Override
    public void detachView() {
        view = null;
        if (disposable != null) {
            disposable.dispose();
        }
    }

}
