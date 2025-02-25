package com.example.fragmentsbonus.search.presenter.ingredients.all_ingredients;

import com.example.fragmentsbonus.models.ingredients.IngredientItem;
import com.example.fragmentsbonus.search.view.all_ingredients.AllIngredientsView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AllIngredientsPresenterImp implements AllIngredientsPresenter {
    private AllIngredientsView view;
    private final List<IngredientItem> ingredients;

    public AllIngredientsPresenterImp(AllIngredientsView view, List<IngredientItem> ingredients) {
        this.view = view;
        this.ingredients = ingredients;
    }

    @Override
    public void getIngredients() {
        view.showLoading();
        view.showIngredients(new ArrayList<>(ingredients));
        view.hideLoading();
    }

    @Override
    public void filterIngredients(String query) {
        if (view == null) return;
        if (ingredients == null) {
            view.showErrorMessage("No ingredients available");
            return;
        }

        if (query.isEmpty()) {
            view.showIngredients(new ArrayList<>(ingredients));
        } else {
            List<IngredientItem> filteredList = ingredients.stream()
                    .filter(ingredient ->
                            ingredient.getStrIngredient().toLowerCase()
                                    .contains(query.toLowerCase()))
                    .collect(Collectors.toList());
            view.showIngredients(filteredList);
        }
    }

    @Override
    public void detachView() {
        view = null;
    }
}