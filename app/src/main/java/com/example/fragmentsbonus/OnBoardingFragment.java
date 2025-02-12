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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnBoardingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnBoardingFragment extends Fragment {
    Button button;
    public static OnBoardingFragment newInstance(String param1, String param2) {
        OnBoardingFragment fragment = new OnBoardingFragment();
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
        return inflater.inflate(R.layout.fragment_on_boarding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = view.findViewById(R.id.signup_email);
        button.setOnClickListener(v -> {
        Navigation.findNavController(view).navigate(R.id.action_onBoardingFragment_to_loginOptionFragment);        });
        OnBoardingFragmentDirections.ActionOnBoardingFragmentToLoginOptionFragment action =
                OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginOptionFragment("Hello");
        Navigation.findNavController(view).navigate(action);
    }
}