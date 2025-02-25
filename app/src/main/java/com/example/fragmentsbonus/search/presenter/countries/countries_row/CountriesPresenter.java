package com.example.fragmentsbonus.search.presenter.countries.countries_row;

public interface CountriesPresenter {
    void getCountries();
    void filterCountries(String query);

    void detachView();
}
