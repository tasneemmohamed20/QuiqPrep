package com.example.fragmentsbonus.auth.signup_by_email.view;

public interface EmailSignupView {
    void showEmailError(String error);
    void showPasswordError(String error);
    void showConfirmPasswordError(String error);
    void clearErrors();
    void showSignupSuccess();
    void showSignupError(String error);
    void navigateToLogin();
    void showNameError(String error);
}
