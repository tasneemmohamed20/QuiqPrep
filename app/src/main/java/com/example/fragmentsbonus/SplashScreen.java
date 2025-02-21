package com.example.fragmentsbonus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Setup Navigation
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView2);
        navController = navHostFragment.getNavController();

        // Setup ActionBar
        NavigationUI.setupActionBarWithNavController(this, navController);

        // Setup BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        // Handle visibility of both bars
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.splashFragment
                || destination.getId() == R.id.onBoardingFragment
                    || destination.getId() == R.id.loginOptionFragment
                    || destination.getId() == R.id.emailLoginFragment
                    || destination.getId() == R.id.emailSignupFragment
                    || destination.getId() == R.id.detailsFragment
            ) {
                // Hide both bars on splash screen
                if (getSupportActionBar() != null) {
                    getSupportActionBar().hide();
                }
                bottomNavigationView.setVisibility(View.GONE);
            } else if (destination.getId() == R.id.favFragment
                    || destination.getId() == R.id.searchFragment
                    || destination.getId() == R.id.plannerFragment) {
                // Show both bars on main screens
                if (getSupportActionBar() != null) {
                    getSupportActionBar().show();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                bottomNavigationView.setVisibility(View.VISIBLE);
            } else if (destination.getId() == R.id.homeFragment) {
                // Hide action bar but show bottom nav on home screen
                if (getSupportActionBar() != null) {
                    getSupportActionBar().hide();
                }
                bottomNavigationView.setVisibility(View.VISIBLE);
            } else {
                // Show action bar but hide bottom nav on other screens
                if (getSupportActionBar() != null) {
                    getSupportActionBar().show();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                bottomNavigationView.setVisibility(View.GONE);
            }

        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}