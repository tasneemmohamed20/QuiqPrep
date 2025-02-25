package com.example.fragmentsbonus.search.presenter.ingredients.ingredients_row;

import android.content.Context;
import android.util.Log;

import com.example.fragmentsbonus.database.MealLocalDataSourceImp;
import com.example.fragmentsbonus.models.ingredients.IngredientItem;
import com.example.fragmentsbonus.models.repository.MealsRepositoryImplementation;
import com.example.fragmentsbonus.network.MealsRemoteDataSourceImplementation;
import com.example.fragmentsbonus.search.view.SearchView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class IngredientsPresenterImp implements IngredientsPresenter {
    private SearchView view;
    private Disposable disposable;
    private final MealsRepositoryImplementation repository;

    public IngredientsPresenterImp(SearchView view, Context context) {
        this.view = view;
        this.repository = MealsRepositoryImplementation.getInstance(MealsRemoteDataSourceImplementation.getInstance(), MealLocalDataSourceImp.getInstance(context));
    }

    @Override
    public void getIngredients() {
//        view.showProgress();
        disposable = repository.getIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (view != null) {
                                List<IngredientItem> ingredients = response.getIngredient();
                                Log.d("IngredientsPresenter", "Got ingredients: " +ingredients.size() );
                                view.showIngredients(ingredients);
                            }
                        },
                        error -> {
                            Log.i("IngredientsPresenter", "Error: " + error.getMessage());
                            if (view != null) {
//                                view.hideProgress();
                                view.showError(error.getMessage());
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
