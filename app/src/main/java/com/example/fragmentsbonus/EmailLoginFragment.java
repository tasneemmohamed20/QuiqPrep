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

public class EmailLoginFragment extends Fragment {
    TextInputLayout emailInputLayout, passwordInputLayout;
    Button loginButton;
    private FirebaseAuth mAuth;
    public EmailLoginFragment() {
        // Required empty public constructor
    }

    public static EmailLoginFragment newInstance(String param1, String param2) {
        EmailLoginFragment fragment = new EmailLoginFragment();
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
        return inflater.inflate(R.layout.fragment_email_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailInputLayout = view.findViewById(R.id.outlinedTextFieldemaillogin);
        passwordInputLayout = view.findViewById(R.id.outlinedTextFieldPasswordlogin);
        loginButton = view.findViewById(R.id.login_button);
        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInputLayout.getEditText().getText().toString();
                String password = passwordInputLayout.getEditText().getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    emailInputLayout.setError("Please fill in the required fields");
                    passwordInputLayout.setError("Please fill in the required fields");
                } else {
                    emailInputLayout.setError(null);
                    passwordInputLayout.setError(null);
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Welcome!", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(view).navigate(R.id.action_emailLoginFragment_to_homeFragment);
                                } else {
                                    Toast.makeText(getContext(), "E-mail or password is incorrect", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}