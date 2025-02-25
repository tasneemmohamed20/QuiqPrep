package com.example.fragmentsbonus.auth.login_options.presenter;

import com.example.fragmentsbonus.auth.login_options.view.LoginOptionsView;

public class LoginOptionsPresenterImp implements LoginOptionsPresenter {
    private LoginOptionsView view;

    public LoginOptionsPresenterImp(LoginOptionsView view) {
        this.view = view;
    }

    @Override
    public void onLoginClicked() {
        if (view != null) {
            view.navigateToEmailLogin();
        }
    }

    @Override
    public void onSignupClicked() {
        if (view != null) {
            view.navigateToEmailSignup();
        }
    }

    @Override
    public void onSkipClicked() {
        if (view != null) {
            view.navigateToHome();
        }
    }

    @Override
    public void onGoogleSignUpClicked() {
        if (view != null) {
            view.onGoogleSignUpSuccess();
        }
    }

    @Override
    public void handleGoogleSignInResult(String idToken) {

    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
