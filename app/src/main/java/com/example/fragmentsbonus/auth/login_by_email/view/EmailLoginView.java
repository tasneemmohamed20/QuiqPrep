package com.example.fragmentsbonus.auth.login_by_email.view;

import android.content.Context;

public interface EmailLoginView {
    void showLoading();
    void hideLoading();
    void showError(String message);
    void clearErrors();
    void navigateToHome();
    void showLoginSuccess();
}
