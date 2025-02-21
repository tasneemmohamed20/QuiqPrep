package com.example.fragmentsbonus.favorites.persenter;

import com.example.fragmentsbonus.favorites.view.FavoritesView;
import com.example.fragmentsbonus.models.meals.MealsItem;
import com.example.fragmentsbonus.models.repository.MealsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritesPresenterImp implements FavoritesPresenter {
    private static final String TAG = "FavoritesPresenterImp";
    private FavoritesView view;
    private MealsRepository repository;
    private Disposable disposable;

    public FavoritesPresenterImp(FavoritesView view, MealsRepository repository) {
        this.view = view;
        this.repository = repository;

    }
    @Override
    public void getFavorites() {
        view.showLoading();
        disposable = repository.getStoredMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    view.hideLoading();
                    view.showFavorites(meals);

                }, throwable -> {
                    view.hideLoading();
                    view.showError(throwable.getMessage());
                });
    }

    @Override
    public void removeFavorite(MealsItem meal) {
        disposable = repository.deleteMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    view.showFavorites(repository.getStoredMeals().blockingFirst());
                }, throwable -> {
                    view.showError(throwable.getMessage());
                });
    }

    @Override
    public void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        view = null;
    }
}
