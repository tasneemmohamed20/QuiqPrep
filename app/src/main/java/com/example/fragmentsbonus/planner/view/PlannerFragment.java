package com.example.fragmentsbonus.planner.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.click_listener.OnMealClickListener;
import com.example.fragmentsbonus.models.meals.MealsItem;
import com.example.fragmentsbonus.planner.presenter.PlannerPresenter;
import com.example.fragmentsbonus.planner.presenter.PlannerPresenterImp;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class PlannerFragment extends Fragment implements PlannerView, OnMealClickListener {
    private PlannerPresenter presenter;
    private CalendarView calendarView;
    private RecyclerView mealRecyclerView;
    private TextView selectedDateText;
    private TextView emptyStateText;
    private PlannerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planner, container, false);
        initViews(view);
        setupRecyclerView();
        presenter = new PlannerPresenterImp(this, requireContext());

        setupCalendar();
        presenter.loadMealForDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        return view;
    }

    private void initViews(View view) {
        calendarView = view.findViewById(R.id.calendarView);
        mealRecyclerView = view.findViewById(R.id.mealRecyclerView);
        selectedDateText = view.findViewById(R.id.selectedDateText);
    }

    private void setupRecyclerView() {
        adapter = new PlannerAdapter(new ArrayList<>(), this);
        mealRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mealRecyclerView.setAdapter(adapter);
    }

    private void setupCalendar() {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            long dateInMillis = calendar.getTimeInMillis();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String dateString = sdf.format(new Date(dateInMillis));

            presenter.loadMealForDate(dateString);
            selectedDateText.setText(sdf.format(calendar.getTime()));
        });
    }

    @Override
    public void showDatePicker() {
        if (!isAdded()) return;

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Schedule Meal")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTheme(R.style.CustomCalendarView)
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            if (!isAdded()) return;

            presenter.onDateSelected(selection);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(selection);
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
            selectedDateText.setText(sdf.format(calendar.getTime()));
        });

        datePicker.show(getParentFragmentManager(), "DATE_PICKER");
    }

    @Override
    public void showMealForDate(List<MealsItem> meal, String date) {
        if (!isAdded()) return;

        if (meal != null) {
//            emptyStateText.setVisibility(View.GONE);
            mealRecyclerView.setVisibility(View.VISIBLE);
            adapter.setMeal(meal);
        } else {
//            emptyStateText.setVisibility(View.VISIBLE);
            mealRecyclerView.setVisibility(View.GONE);

        }
    }


    @Override
    public void showError(String message) {
        if (!isAdded()) return;
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCalendar() {
        calendarView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    @Override
    public void onMealClick(MealsItem meal) {
        Bundle args = new Bundle();
        args.putParcelable("meal", meal);
        Navigation.findNavController(requireView()).navigate(R.id.action_plannerFragment_to_detailsFragment, args);
    }
}