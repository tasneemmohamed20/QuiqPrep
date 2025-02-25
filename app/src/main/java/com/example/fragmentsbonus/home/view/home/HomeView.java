package com.example.fragmentsbonus.home.view.home;

import android.content.Context;

public interface HomeView {
    void displayUserName(String name);
    void navigateToLogin();
    void showError(String message);
}
