package com.example.fragmentsbonus.auth.login_options.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.auth.login_options.presenter.LoginOptionsPresenter;
import com.example.fragmentsbonus.auth.login_options.presenter.LoginOptionsPresenterImp;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginOptionFragment extends Fragment implements LoginOptionsView {
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleSignIn";

    Button signupEmail, login, skip;
    private LoginOptionsPresenter presenter;
    private MaterialButton googleSignUpButton;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    public LoginOptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_option, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        presenter = new LoginOptionsPresenterImp(this);
        setupListeners();
    }

    private void initViews(View view) {
        signupEmail = view.findViewById(R.id.signup_email);
        skip = view.findViewById(R.id.skipButton);
        login = view.findViewById(R.id.textButton);
        googleSignUpButton = view.findViewById(R.id.Googlebutton);
    }

    private void setupListeners() {
        login.setOnClickListener(v -> presenter.onLoginClicked());
        signupEmail.setOnClickListener(v -> presenter.onSignupClicked());
        skip.setOnClickListener(v -> presenter.onSkipClicked());
        googleSignUpButton.setOnClickListener(v -> signInWithGoogle());
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            try {
                GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.e(TAG, "Google Sign-In failed. Status Code: " + e.getStatusCode(), e);
                showError("Google sign-in failed. Code: " + e.getStatusCode());
            }
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d(TAG, "signInWithCredential:success " + user.getDisplayName());
                        Toast.makeText(getContext(), "Sign-in successful", Toast.LENGTH_SHORT).show();
                        onGoogleSignUpSuccess();
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        showError("Authentication Failed.");
                    }
                });
    }

    @Override
    public void navigateToEmailLogin() {
        Navigation.findNavController(requireView()).navigate(R.id.action_loginOptionFragment_to_emailLoginFragment);
    }

    @Override
    public void navigateToEmailSignup() {
        Navigation.findNavController(requireView()).navigate(R.id.action_loginOptionFragment_to_emailSignFragment);
    }

    @Override
    public void navigateToHome() {
        Bundle args = new Bundle();
        args.putBoolean("Guest", true);
        Navigation.findNavController(requireView()).navigate(R.id.action_loginOptionFragment_to_homeFragment, args);
    }

    @Override
    public void onGoogleSignUpSuccess() {
        Navigation.findNavController(requireView()).navigate(R.id.action_loginOptionFragment_to_homeFragment);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }
}
