package com.example.fragmentsbonus.search.view;

import com.example.fragmentsbonus.models.countries.CountryItem;
import com.example.fragmentsbonus.models.ingredients.IngredientItem;

import java.util.List;

public interface SearchView {
    void showIngredients(List<IngredientItem> ingredients);
    void showProgress();
    void hideProgress();
    void showError(String error);
    void showCountries(List<CountryItem> countries);
    void showCategories(List<String> categories);
    void OnIngredientClick();
    void OnCountryClick();
    void OnCategoryClick();
    void onVeiwAllClick(List<IngredientItem> ingredients);
}
