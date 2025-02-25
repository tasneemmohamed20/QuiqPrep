package com.example.fragmentsbonus.auth.login_by_email.persenter;

public interface EmailLoginPresenter {
    void attemptLogin(String email, String password);
    void detachView();
}
