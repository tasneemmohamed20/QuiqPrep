package com.example.fragmentsbonus.auth.signup_by_email.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.auth.signup_by_email.presenter.EmailSignupPresenter;
import com.example.fragmentsbonus.auth.signup_by_email.presenter.EmailSignupPresenterImp;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class EmailSignupFragment extends Fragment implements EmailSignupView {
    TextInputLayout emailInputLayout, passwordInputLayout, confirmPasswordInputLayout, nameInputLayout;
    Button signupButton;
    private EmailSignupPresenter presenter;


    public EmailSignupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new EmailSignupPresenterImp(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailInputLayout = view.findViewById(R.id.outlinedTextFieldemail);
        passwordInputLayout = view.findViewById(R.id.outlinedTextFieldPassword);
        confirmPasswordInputLayout = view.findViewById(R.id.outlinedTextFieldPasswordConfirm);
        signupButton = view.findViewById(R.id.signup_button);
        nameInputLayout = view.findViewById(R.id.outlinedTextFielName);
        signupButton.setOnClickListener(v -> {
            String email = emailInputLayout.getEditText().getText().toString();
            String password = passwordInputLayout.getEditText().getText().toString();
            String confirmPassword = confirmPasswordInputLayout.getEditText().getText().toString();
            String name = nameInputLayout.getEditText().getText().toString();
            presenter.validateAndSignup(name, email, password, confirmPassword);
        });

    }

    @Override
    public void showEmailError(String error) {
        emailInputLayout.setError(error);
    }

    @Override
    public void showPasswordError(String error) {
        passwordInputLayout.setError(error);
    }

    @Override
    public void showConfirmPasswordError(String error) {
        confirmPasswordInputLayout.setError(error);
    }

    @Override
    public void clearErrors() {
        emailInputLayout.setError(null);
        passwordInputLayout.setError(null);
        confirmPasswordInputLayout.setError(null);
        nameInputLayout.setError(null);
    }

    @Override
    public void showSignupSuccess() {
        Toast.makeText(getContext(), "Account created successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSignupError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToLogin() {
        Navigation.findNavController(requireView()).navigate(R.id.action_emailSignupFragment_to_emailLoginFragment);
    }

    @Override
    public void showNameError(String error) {
        nameInputLayout.setError(error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}