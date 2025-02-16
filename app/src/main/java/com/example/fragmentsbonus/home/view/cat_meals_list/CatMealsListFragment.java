package com.example.fragmentsbonus.home.view.cat_meals_list;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.home.model.random_meal.MealsItem;
import com.example.fragmentsbonus.home.presenter.cat_meal.CatMealsPresenter;
import com.example.fragmentsbonus.home.presenter.cat_meal.CatMealsPresenterImplementation;
import com.example.fragmentsbonus.home.view.CatMealsAdapter;

import java.util.List;


public class CatMealsListFragment extends Fragment implements CatMealsView {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private CatMealsPresenter presenter;
    private String category;

    public static CatMealsListFragment newInstance() {
        return new CatMealsListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString("category");
        }
        presenter = new CatMealsPresenterImplementation((CatMealsView) this, this, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar3);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        presenter.loadCatMeals(category);
        return view;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMeals(List<MealsItem> meals) {
//        List<Recipe> recipes = new ArrayList<>();
//        for (MealsItem meal : meals) {
//            recipes.add(new Recipe(meal.getStrMeal(), meal.getStrInstructions(), meal.getStrCategory(), meal.getStrMealThumb()));
//        }
        CatMealsAdapter adapter = new CatMealsAdapter(meals);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

//    private List<Recipe> getRecipesByCategory(String category) {
//        List<Recipe> recipes = new ArrayList<>();
//
//        switch (category) {
//            case "Breakfast":
//                recipes.add(new Recipe("Pancakes", "Fluffy breakfast pancakes", "20 min", 4.5f, 5.99, R.drawable.img));
//                recipes.add(new Recipe("Omelette", "Classic cheese omelette", "15 min", 4.3f, 4.99, R.drawable.img));
//                break;
//
//            case "Lunch":
//                recipes.add(new Recipe("Sandwich", "Club sandwich", "10 min", 4.2f, 6.99, R.drawable.img));
//                recipes.add(new Recipe("Salad", "Caesar salad", "15 min", 4.4f, 7.99, R.drawable.img));
//                break;
//
//            case "Dinner":
//                recipes.add(new Recipe("Steak", "Grilled ribeye steak", "30 min", 4.8f, 15.99, R.drawable.img));
//                recipes.add(new Recipe("Pasta", "Fettuccine alfredo", "25 min", 4.6f, 12.99, R.drawable.img));
//                break;
//
//            case "Dessert":
//                recipes.add(new Recipe("Ice Cream", "Vanilla ice cream", "5 min", 4.7f, 3.99, R.drawable.img));
//                recipes.add(new Recipe("Cake", "Chocolate cake", "45 min", 4.9f, 8.99, R.drawable.img));
//                break;
//
//            case "Snacks":
//                recipes.add(new Recipe("Chips", "Potato chips", "1 min", 4.0f, 1.99, R.drawable.img));
//                recipes.add(new Recipe("Popcorn", "Butter popcorn", "5 min", 4.1f, 2.99, R.drawable.img));
//                break;
//        }
//
//        return recipes;
//    }
}