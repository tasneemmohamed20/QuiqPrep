package com.example.fragmentsbonus.search.presenter.countries.countries_row;

import android.content.Context;

import com.example.fragmentsbonus.database.MealLocalDataSourceImp;
import com.example.fragmentsbonus.models.countries.CountryItem;
import com.example.fragmentsbonus.models.repository.MealsRepositoryImplementation;
import com.example.fragmentsbonus.network.MealsRemoteDataSourceImplementation;
import com.example.fragmentsbonus.search.view.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CountriesPresenterImp implements CountriesPresenter {
    private SearchView view;
    Disposable disposable;
    private final MealsRepositoryImplementation repository;

    public CountriesPresenterImp(Context context, SearchView view) {
        this.repository = MealsRepositoryImplementation.getInstance(MealsRemoteDataSourceImplementation.getInstance(), MealLocalDataSourceImp.getInstance(context));
        this.view = view;
    }


    @Override
    public void getCountries() {
//        view.showProgress();
        disposable = repository.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (view != null) {
                                List<CountryItem> countries = response.getCountries();
                                view.showCountries(countries);
//                                view.hideProgress();
                            }
                        },
                        error -> {
                            if (view != null) {
                                view.hideProgress();
                                view.showError(error.getMessage());
                            }
                        }
                );
    }

    @Override
    public void filterCountries(String query) {
        if (view == null) return;

        disposable = repository.getCountries()
                .map(response -> {
                    List<CountryItem> filteredCountries = response.getCountries().stream()
                            .filter(country ->
                                    country.getStrArea().toLowerCase()
                                            .contains(query.toLowerCase()))
                            .collect(Collectors.toList());

                    // If no matches found, return original list
                    return filteredCountries.isEmpty() ? response.getCountries() : filteredCountries;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        countries -> view.showCountries(countries),
                        error -> view.showError(error.getMessage())
                );
    }

    @Override
    public void detachView() {
        this.view = null;
        disposable.dispose();
    }
}
