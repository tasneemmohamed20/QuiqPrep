package com.example.fragmentsbonus.details.view.ingredients;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.details.presenter.ingredients.IngredientsPresenter;
import com.example.fragmentsbonus.details.presenter.ingredients.IngredientsPresenterImp;
import com.example.fragmentsbonus.models.meals.MealsItem;

public class IngredientsFragment extends Fragment implements IngredientsView {
    private RecyclerView recyclerView;
    private MealsItem meal;
    private IngredientsAdapter adapter;
    private IngredientsPresenter presenter;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    public static IngredientsFragment newInstance(MealsItem meal) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putParcelable("meal", meal);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           meal = getArguments().getParcelable("meal");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        recyclerView = view.findViewById(R.id.recycler_ingrediants);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        presenter = new IngredientsPresenterImp(this, meal);
        presenter.loadIngredients();
    }

    @Override
    public void displayIngredients(MealsItem meal) {
        adapter = new IngredientsAdapter(meal);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}