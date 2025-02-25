package com.example.fragmentsbonus.auth.signup_by_email.presenter;

import com.example.fragmentsbonus.auth.signup_by_email.view.EmailSignupView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EmailSignupPresenterImp implements EmailSignupPresenter {
    private EmailSignupView view;
    private FirebaseAuth mAuth;

    public EmailSignupPresenterImp(EmailSignupView view) {
        this.view = view;
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void validateAndSignup(String name, String email, String password, String confirmPassword) {
        if (name.isEmpty()||email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            if (name.isEmpty()) view.showNameError("Please fill in your name");
            if (email.isEmpty()) view.showEmailError("Please fill in the email");
            if (password.isEmpty()) view.showPasswordError("Please fill in the password");
            if (confirmPassword.isEmpty()) view.showConfirmPasswordError("Please confirm your password");
            return;
        }

        if (!password.equals(confirmPassword)) {
            view.showConfirmPasswordError("Passwords do not match");
            return;
        }

        view.clearErrors();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Update user profile with name
                        mAuth.getCurrentUser().updateProfile(
                                new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build()
                        ).addOnCompleteListener(profileTask -> {
                            if (profileTask.isSuccessful()) {
                                view.showSignupSuccess();
                                view.navigateToLogin();
                            } else {
                                view.showSignupError("Account created but failed to set name");
                            }
                        });
                    } else {
                        view.showSignupError("Something went wrong, please try again.");
                    }
                });
    }


    @Override
    public void onDestroy() {
        view = null;
    }
}