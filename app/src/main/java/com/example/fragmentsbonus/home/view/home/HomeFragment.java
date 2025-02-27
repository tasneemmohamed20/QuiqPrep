package com.example.fragmentsbonus.home.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.click_listener.OnMealClickListener;
import com.example.fragmentsbonus.home.presenter.home.HomePresenter;
import com.example.fragmentsbonus.home.presenter.home.HomePresenterImp;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;


public class HomeFragment extends Fragment implements RandomMealView, CategoriesView, OnMealClickListener, HomeView {

    private CategoriesAdapter categoriesAdapter;
    ProgressBar progressBar;
    RandomMealPresenter presenter;
    CardView cardView, searchCard;
    TextView userName;
    RandomBinder binder;
    private  boolean isGuest = false;
    private HomePresenter homePresenter;
    BottomNavigationView bottomNavigationView;
    ImageView logout;


    public HomeFragment() {
        // Required empty public constructor
    }


    private void showLoginRequiredDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Login Required")
                .setMessage("You should login to use this feature")
                .setPositiveButton("Login", (dialog, which) -> {
                    // Navigate to login screen
                    Navigation.findNavController(requireView())
                            .navigate(R.id.action_homeFragment_to_loginOptionFragment);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter = new HomePresenterImp(this, this.requireContext());
        if (getArguments() != null){
            isGuest = HomeFragmentArgs.fromBundle(getArguments()).getGuest();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        cardView = view.findViewById(R.id.cardViewrand);
        searchCard = view.findViewById(R.id.searchCard);
        userName = view.findViewById(R.id.username);
        binder = new RandomBinder();
        binder.setOnMealClickListener(this);
        logout = view.findViewById(R.id.logout);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout_details);
        ViewPager2 viewPager = view.findViewById(R.id.pager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        categoriesAdapter = new CategoriesAdapter(requireActivity());
        viewPager.setAdapter(categoriesAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(categoriesAdapter.getTitle(position))
        ).attach();
        searchCard.setOnClickListener(v -> {
            // Set transition names
            searchCard.setTransitionName("searchTransition");

            // Create extras for shared element transition
            androidx.navigation.fragment.FragmentNavigator.Extras extras = new androidx.navigation.fragment.FragmentNavigator.Extras.Builder()
                    .addSharedElement(searchCard, "searchTransition")
                    .build();

            // Create animation options
            FragmentNavigator.Extras animExtras = new FragmentNavigator.Extras.Builder()
                    .addSharedElement(searchCard, "searchTransition")
                    .build();

            // Navigate with transition
            Navigation.findNavController(view).navigate(
                    R.id.action_homeFragment_to_searchFragment,
                    null,
                    null,
                    extras
            );
        });
//        Toast.makeText(requireContext(), "isGuest = " + isGuest, Toast.LENGTH_SHORT).show();
//        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
//        navController.navigate(R.id.action_homeFragment_to_loginOptionFragment);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new RandomMealPresenterImplementation(this, requireContext());
        presenter.loadRandomMeal();
        homePresenter.loadUserName();
        CategoriesPresenter presenter = new CategoriesPresenterImplementation(this, requireContext());
        presenter.loadCategories();

        logout.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        homePresenter.logout();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        if (isGuest){
            userName.setText("Guest");
            bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                // Only allow home and search for guests
                if (itemId == R.id.homeFragment || itemId == R.id.searchFragment) {
                    if (itemId == R.id.searchFragment) {
                        Navigation.findNavController(requireView())
                                .navigate(R.id.action_homeFragment_to_searchFragment);

                    }
                    return true;
                } else {
                    showLoginRequiredDialog();
                    return false;
                }
            });
        }

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
        if (homePresenter != null)
            homePresenter.onDestroy();
    }

    @Override
    public void onMealClick(MealsItem meal) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("meal", meal);
        bundle.putBoolean("Guest", isGuest);
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_detailsFragment, bundle);
    }

    @Override
    public void displayUserName(String name) {
        userName.setText(name);
    }

    @Override
    public void navigateToLogin() {
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginOptionFragment);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

}