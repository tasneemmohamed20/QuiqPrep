package com.example.fragmentsbonus.home.presenter.home;

import android.content.Context;

import com.example.fragmentsbonus.database.MealLocalDataSourceImp;
import com.example.fragmentsbonus.database.MealsLocalDataSource;
import com.example.fragmentsbonus.home.view.home.HomeView;

import com.example.fragmentsbonus.models.repository.MealsRepository;
import com.example.fragmentsbonus.models.repository.MealsRepositoryImplementation;
import com.example.fragmentsbonus.network.MealsRemoteDataSourceImplementation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImp implements HomePresenter {
    private HomeView view;
    private FirebaseAuth mAuth;
    private MealsRepository repository;
    private Disposable disposable;

    public HomePresenterImp(HomeView view, Context context) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
        this.repository = MealsRepositoryImplementation.getInstance(
                MealsRemoteDataSourceImplementation.getInstance(context),
                MealLocalDataSourceImp.getInstance(context)
        );
    }

    @Override
    public void loadUserName() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && currentUser.getDisplayName() != null) {
            view.displayUserName(currentUser.getDisplayName());
        } else {
            view.showError("Unable to load user name");
        }
    }

    @Override
    public void logout() {
        if (mAuth.getCurrentUser() != null) {
            // Clear local database first
            disposable = repository.clearAllData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> {
                                // Then sign out and navigate
                                mAuth.signOut();
                                if (view != null) {
                                    view.navigateToLogin();
                                }
                            },
                            throwable -> {
                                if (view != null) {
                                    view.showError("Error clearing data: " + throwable.getMessage());
                                }
                            }
                    );
        }
    }


    @Override
    public void onDestroy() {
        view = null;
    }

}
