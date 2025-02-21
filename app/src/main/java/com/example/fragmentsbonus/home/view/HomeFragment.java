package com.example.fragmentsbonus.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.click_listener.OnMealClickListener;
import com.example.fragmentsbonus.models.categories.CategoriesItem;
import com.example.fragmentsbonus.models.meals.MealsItem;
import com.example.fragmentsbonus.home.presenter.categories.CategoriesPresenter;
import com.example.fragmentsbonus.home.presenter.categories.CategoriesPresenterImplementation;
import com.example.fragmentsbonus.home.view.random_meal.RandomBinder;
import com.example.fragmentsbonus.home.presenter.random_meal.RandomMealPresenter;
import com.example.fragmentsbonus.home.presenter.random_meal.RandomMealPresenterImplementation;
import com.example.fragmentsbonus.home.view.categories.CategoriesAdapter;
import com.example.fragmentsbonus.home.view.categories.CategoriesView;
import com.example.fragmentsbonus.home.view.random_meal.RandomMealView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;


public class HomeFragment extends Fragment implements RandomMealView, CategoriesView, OnMealClickListener {

    private CategoriesAdapter categoriesAdapter;
    ProgressBar progressBar;
    RandomMealPresenter presenter;
    CardView cardView;
    RandomBinder binder;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        cardView = view.findViewById(R.id.cardViewrand);
        binder = new RandomBinder();
        binder.setOnMealClickListener(this);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout_details);
        ViewPager2 viewPager = view.findViewById(R.id.pager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

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
        presenter = new RandomMealPresenterImplementation(this, requireContext());
        presenter.loadRandomMeal();

        CategoriesPresenter presenter = new CategoriesPresenterImplementation(this, requireContext());
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.detachView();
    }

    @Override
    public void onMealClick(MealsItem meal) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("meal", meal);
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_detailsFragment, bundle);
    }
}