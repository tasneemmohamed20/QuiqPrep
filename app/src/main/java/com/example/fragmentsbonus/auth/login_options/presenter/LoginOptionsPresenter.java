package com.example.fragmentsbonus.auth.login_options.presenter;

public interface LoginOptionsPresenter {
    void onLoginClicked();
    void onSignupClicked();
    void onSkipClicked();
    void onGoogleSignUpClicked();
    void handleGoogleSignInResult(String idToken);
    void onDestroy();
}
