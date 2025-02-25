package com.example.fragmentsbonus.splash.presenter;

import com.example.fragmentsbonus.splash.view.SplashView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.os.Handler;

public class SplashPresenterImp implements SplashPresenter {
    private SplashView view;
    private FirebaseAuth mAuth;

    public SplashPresenterImp(SplashView view) {
        this.view = view;
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void checkAuthStatus() {
        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                view.navigateToHome(false);
            } else {
                view.navigateToOnBoarding();
            }
        }, 3000);
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}