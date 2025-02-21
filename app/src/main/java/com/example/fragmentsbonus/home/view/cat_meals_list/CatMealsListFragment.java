package com.example.fragmentsbonus.home.view.cat_meals_list;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.click_listener.OnMealClickListener;
import com.example.fragmentsbonus.models.meals.MealsItem;
import com.example.fragmentsbonus.home.presenter.cat_meal.CatMealsPresenter;
import com.example.fragmentsbonus.home.presenter.cat_meal.CatMealsPresenterImplementation;

import java.util.List;


public class CatMealsListFragment extends Fragment implements CatMealsView, OnMealClickListener {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private CatMealsPresenter presenter;
    private String category;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString("category");
        }
        presenter = new CatMealsPresenterImplementation(this, requireContext());
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
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMeals(List<MealsItem> meals) {

        CatMealsAdapter adapter = new CatMealsAdapter(meals, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onErrorLoading(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMealClick(MealsItem meal) {
        Bundle args = new Bundle();
        args.putParcelable("meal", meal);
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_detailsFragment, args);
    }
}