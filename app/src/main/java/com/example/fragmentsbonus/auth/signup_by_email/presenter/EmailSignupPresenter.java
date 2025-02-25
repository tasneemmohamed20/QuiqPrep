package com.example.fragmentsbonus.auth.signup_by_email.presenter;

public interface EmailSignupPresenter {
    void validateAndSignup(String name, String email, String password, String confirmPassword);
    void onDestroy();
}
