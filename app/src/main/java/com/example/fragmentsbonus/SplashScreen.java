package com.example.fragmentsbonus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

//        View rootView = findViewById(R.id.main);
//        if (rootView != null) {
//            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
//                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//                return insets;
//            });
//        }

        // Setup Navigation
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView2);
        navController = navHostFragment.getNavController();

        // Setup ActionBar
//        NavigationUI.setupActionBarWithNavController(this, navController);

        // Setup BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }

        // Handle visibility of both bars
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.splashFragment
                    || destination.getId() == R.id.onBoardingFragment
                    || destination.getId() == R.id.loginOptionFragment
                    || destination.getId() == R.id.emailLoginFragment
                    || destination.getId() == R.id.emailSignupFragment
                    || destination.getId() == R.id.detailsFragment
                    || destination.getId() == R.id.filteredMealsFragment
                    || destination.getId() == R.id.allIngredientsFragment
            ) {

                try {
                    View decorView = getWindow().getDecorView();
                    // Get the background color of the root view
                    int backgroundColor = Color.TRANSPARENT;
                    if (decorView.getBackground() instanceof ColorDrawable) {
                        backgroundColor = ((ColorDrawable) decorView.getBackground()).getColor();
                    }

                    getWindow().setStatusBarColor(backgroundColor);
                    getWindow().setNavigationBarColor(backgroundColor);
                    WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

                    // Adjust system bar icons based on background brightness
                    boolean isLightBackground = isColorLight(backgroundColor);
                    WindowInsetsControllerCompat windowController =
                            WindowCompat.getInsetsController(getWindow(), decorView);
                    windowController.setAppearanceLightStatusBars(isLightBackground);
                    windowController.setAppearanceLightNavigationBars(isLightBackground);

                    bottomNavigationView.setVisibility(View.GONE);
                } catch (Exception e) {
                    // Fallback to default transparent bars if there's an error
                    getWindow().setStatusBarColor(Color.TRANSPARENT);
                    getWindow().setNavigationBarColor(Color.TRANSPARENT);
                }

            } else if (destination.getId() == R.id.favFragment
                    || destination.getId() == R.id.searchFragment
                    || destination.getId() == R.id.plannerFragment
                    || destination.getId() == R.id.homeFragment) {

                try {
                    View contentView = findViewById(android.R.id.content);
                    if (contentView != null && contentView.getBackground() instanceof ColorDrawable) {
                        int backgroundColor = ((ColorDrawable) contentView.getBackground()).getColor();
                        getWindow().setStatusBarColor(backgroundColor);
                        getWindow().setNavigationBarColor(backgroundColor);

                        boolean isLightBackground = isColorLight(backgroundColor);
                        WindowInsetsControllerCompat windowController =
                                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
                        windowController.setAppearanceLightStatusBars(isLightBackground);
                        windowController.setAppearanceLightNavigationBars(isLightBackground);
                    }
                } catch (Exception e) {
                    // Fallback to system default if there's an error
                    getWindow().setStatusBarColor(getResources().getColor(android.R.color.white, getTheme()));
                    getWindow().setNavigationBarColor(getResources().getColor(android.R.color.white, getTheme()));
                }

                WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });


    }

    private boolean isColorLight(int color) {
        if (color == Color.TRANSPARENT) {
            return true;
        }
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness < 0.5;
    }
    private void hideSystemBars() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    private void showSystemBars() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        return navController.navigateUp() || super.onSupportNavigateUp();
//    }
}