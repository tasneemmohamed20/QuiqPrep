package com.example.fragmentsbonus.auth.login_options.view;

public interface LoginOptionsView {
    void navigateToEmailLogin();
    void navigateToEmailSignup();
    void navigateToHome();
    void onGoogleSignUpSuccess();
    void showError(String message);

}
