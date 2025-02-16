package com.example.fragmentsbonus.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.home.model.categories.CategoriesItem;
import com.example.fragmentsbonus.home.model.random_meal.MealsItem;
import com.example.fragmentsbonus.home.presenter.categories.CategoriesPresenter;
import com.example.fragmentsbonus.home.presenter.categories.CategoriesPresenterImplementation;
import com.example.fragmentsbonus.home.presenter.random_meal.RandomBinder;
import com.example.fragmentsbonus.home.presenter.random_meal.RandomMealPresenter;
import com.example.fragmentsbonus.home.presenter.random_meal.RandomMealPresenterImplementation;
import com.example.fragmentsbonus.home.view.categories.CategoriesAdapter;
import com.example.fragmentsbonus.home.view.categories.CategoriesView;
import com.example.fragmentsbonus.home.view.random_meal.RandomMealView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;


public class HomeFragment extends Fragment implements RandomMealView, CategoriesView {

    private CategoriesAdapter categoriesAdapter;

    List <String> tabs;
    List<Fragment> fragments;
    ProgressBar progressBar;
    RandomMealPresenter presnter;
    CardView cardView;
    RandomBinder binder;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2)  {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        cardView = view.findViewById(R.id.cardViewrand);
        binder = new RandomBinder();


        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager2 viewPager = view.findViewById(R.id.pager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
//        tabs = List.of("Breakfast", "Lunch", "Dinner", "Dessert", "Snacks");
//        fragments = new ArrayList<>();
//        for (String tab : tabs) {
//            RecipeListFragment fragment = new RecipeListFragment();
//            Bundle args = new Bundle();
//            args.putString("category", tab);
//            fragment.setArguments(args);
//            fragments.add(fragment);
//            categoriesAdapter.addFragment(fragment, tab);
//        }
        categoriesAdapter = new CategoriesAdapter(requireActivity());
        viewPager.setAdapter(categoriesAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(categoriesAdapter.getTitle(position))
        ).attach();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presnter = new RandomMealPresenterImplementation(this,  requireContext(), this);
        presnter.loadRandomMeal();

        CategoriesPresenter presenter = new CategoriesPresenterImplementation(this, requireActivity(), requireContext());
        presenter.loadCategories();

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
    public void showCategories(List<CategoriesItem> categories) {
        categoriesAdapter.setCategories(categories);
    }

    @Override
    public void showMeal(MealsItem mealsItem) {
        if (mealsItem != null){
            cardView.setVisibility(View.VISIBLE);
            binder.bind(cardView, mealsItem);
        }
    }

    @Override
    public void onErrorLoading(String message) {
        cardView.setVisibility(View.GONE);
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public CategoriesAdapter getAdapter() {
//        return categoriesAdapter;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presnter != null)
            presnter.detachView();
    }
}