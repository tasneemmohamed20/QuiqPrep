package com.example.fragmentsbonus.auth.login_by_email.persenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.fragmentsbonus.auth.login_by_email.view.EmailLoginView;
import com.example.fragmentsbonus.database.MealLocalDataSourceImp;
import com.example.fragmentsbonus.models.repository.MealsRepositoryImplementation;
import com.example.fragmentsbonus.network.MealsRemoteDataSourceImplementation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EmailLoginPresenterImp implements EmailLoginPresenter {
    private static final String TAG = "EmailLoginPresenterImp";
    private EmailLoginView view;
    private FirebaseAuth mAuth;
    private MealsRepositoryImplementation repository;
    private Disposable syncDisposable;

    public EmailLoginPresenterImp(EmailLoginView view, Context context) {
        this.view = view;
        this.mAuth = FirebaseAuth.getInstance();
        this.repository = MealsRepositoryImplementation.getInstance(
                MealsRemoteDataSourceImplementation.getInstance(context),
                MealLocalDataSourceImp.getInstance(context)
        );
    }

    @Override
    public void attemptLogin(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            view.showError("Please fill in the required fields");
            return;
        }

        view.clearErrors();
        view.showLoading();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User authenticated successfully");
                        new Handler(Looper.getMainLooper()).postDelayed(this::syncData, 1000);
                    } else {
                        view.hideLoading();
                        view.showError("E-mail or password is incorrect");
                    }
                });
    }

    private void syncData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Log.e(TAG, "User not logged in");
            view.showError("User not logged in");
            view.hideLoading();
            return;
        }

        Log.d(TAG, "User is logged in: " + currentUser.getEmail());

        syncDisposable = repository.syncFromFirestore()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> view.hideLoading())
                .subscribe(
                        () -> {
                            view.showLoginSuccess();
                            view.navigateToHome();
                        },
                        throwable -> {
                            Log.e(TAG, "Error syncing data: " + throwable.getMessage(), throwable);
                            view.showError("Error syncing data: " + throwable.getMessage());
                            view.navigateToHome();
                        }
                );
    }

    @Override
    public void detachView() {
        view = null;
        if (syncDisposable != null && !syncDisposable.isDisposed()) {
            syncDisposable.dispose();
        }
    }
}