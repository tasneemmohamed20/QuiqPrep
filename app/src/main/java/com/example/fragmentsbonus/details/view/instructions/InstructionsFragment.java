package com.example.fragmentsbonus.details.view.instructions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.details.presenter.instructions.InstructionsPresenter;
import com.example.fragmentsbonus.details.presenter.instructions.InstructionsPresenterImp;
import com.example.fragmentsbonus.models.meals.MealsItem;

public class InstructionsFragment extends Fragment implements InstructionsView{
    private TextView instructionsText;
    MealsItem meal;
    private InstructionsPresenter presenter;

    public InstructionsFragment() {
        // Required empty public constructor
    }

    public static InstructionsFragment newInstance(MealsItem meal)  {
        InstructionsFragment fragment = new InstructionsFragment();
        Bundle args = new Bundle();
        args.putParcelable("meal", meal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            meal = getArguments().getParcelable("meal");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);
        instructionsText = view.findViewById(R.id.instructions);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new InstructionsPresenterImp(this, meal);
        presenter.loadInstructions();

    }

    @Override
    public void displayInstructions(String instructions) {
        if (instructionsText != null) {
            instructionsText.setText(instructions);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}