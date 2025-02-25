package com.example.fragmentsbonus.auth.login_by_email.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.auth.login_by_email.persenter.EmailLoginPresenter;
import com.example.fragmentsbonus.auth.login_by_email.persenter.EmailLoginPresenterImp;
import com.example.fragmentsbonus.database.MealLocalDataSourceImp;
import com.example.fragmentsbonus.models.repository.MealsRepositoryImplementation;
import com.example.fragmentsbonus.network.MealsRemoteDataSourceImplementation;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EmailLoginFragment extends Fragment implements EmailLoginView {
    TextInputLayout emailInputLayout, passwordInputLayout;
    Button loginButton;

    private EmailLoginPresenter presenter;

    public EmailLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        presenter = new EmailLoginPresenterImp(this, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_login, container, false);
    }
    private void initViews(View view) {
        emailInputLayout = view.findViewById(R.id.outlinedTextFieldemaillogin);
        passwordInputLayout = view.findViewById(R.id.outlinedTextFieldPasswordlogin);
        loginButton = view.findViewById(R.id.login_button);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupClickListeners();
    }

    private void setupClickListeners() {
        loginButton.setOnClickListener(v -> {
            String email = emailInputLayout.getEditText().getText().toString();
            String password = passwordInputLayout.getEditText().getText().toString();
            presenter.attemptLogin(email, password);
        });
    }


    @Override
    public void showLoading() {
        if (loginButton != null) {
            loginButton.setEnabled(false);
            loginButton.setText("Loading...");
        }
    }

    @Override
    public void hideLoading() {
        if (loginButton != null) {
            loginButton.setEnabled(true);
            loginButton.setText("Login");
        }
    }

    @Override
    public void showError(String message) {
        emailInputLayout.setError(message);
        passwordInputLayout.setError(message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearErrors() {
        emailInputLayout.setError(null);
        passwordInputLayout.setError(null);
    }

    @Override
    public void navigateToHome() {
        EmailLoginFragmentDirections.ActionEmailLoginFragmentToHomeFragment action =
                EmailLoginFragmentDirections.actionEmailLoginFragmentToHomeFragment(false);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void showLoginSuccess() {
        Toast.makeText(getContext(), "Sign-in successful", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}