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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginOptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginOptionFragment extends Fragment {
    Button signupEmail, login;
    public LoginOptionFragment() {
        // Required empty public constructor
    }

    public static LoginOptionFragment newInstance(String param1, String param2) {
        LoginOptionFragment fragment = new LoginOptionFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_option, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String test = LoginOptionFragmentArgs.fromBundle(getArguments()).getTest();
        Toast.makeText(getContext(), test, Toast.LENGTH_SHORT).show();
        signupEmail = view.findViewById(R.id.signup_email);
        login = view.findViewById(R.id.textButton);
        login.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginOptionFragment_to_emailLoginFragment2);
        });
        signupEmail.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginOptionFragment_to_emailLoginFragment);        });
    }
}