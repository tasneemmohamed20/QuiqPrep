package com.example.fragmentsbonus;

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

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class EmailSignupFragment extends Fragment {
    TextInputLayout emailInputLayout, passwordInputLayout, confirmPasswordInputLayout;
    Button signupButton;
    private FirebaseAuth mAuth;

    public EmailSignupFragment() {
        // Required empty public constructor
    }

    public static EmailSignupFragment newInstance(String param1, String param2) {
        EmailSignupFragment fragment = new EmailSignupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        mAuth = FirebaseAuth.getInstance();
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInputLayout.getEditText().getText().toString();
                String password = passwordInputLayout.getEditText().getText().toString();
                String confirmPassword = confirmPasswordInputLayout.getEditText().getText().toString();
                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    emailInputLayout.setError("Please fill in the required fields");
                    passwordInputLayout.setError("Please fill in the required fields");
                    confirmPasswordInputLayout.setError("Please fill in the required fields");
                } else if (!password.equals(confirmPassword)) {
                    confirmPasswordInputLayout.setError("Passwords do not match");
                } else {
                    emailInputLayout.setError(null);
                    passwordInputLayout.setError(null);
                    confirmPasswordInputLayout.setError(null);

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    System.out.println("createUserWithEmail:success");
                                    Toast.makeText(getContext(), "Account created successfully!", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(view).navigate(R.id.action_emailSignupFragment_to_emailLoginFragment);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    System.out.println("createUserWithEmail:failure");
                                    Toast.makeText(getContext(), "Something went wrong, please try again.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }
}