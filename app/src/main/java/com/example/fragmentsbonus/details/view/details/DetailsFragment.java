package com.example.fragmentsbonus.details.view.details;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.details.presenter.details.DetailsPresenter;
import com.example.fragmentsbonus.details.presenter.details.DetailsPresenterImp;
import com.example.fragmentsbonus.favorites.view.click_listener.OnLikeClickListener;
import com.example.fragmentsbonus.models.meals.MealsItem;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailsFragment extends Fragment implements DetailsView , OnLikeClickListener {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private DetailsAdapter pagerAdapter;
    private DetailsPresenter presenter;
    private TextView titleText;
    private TextView country;
    private ImageView mealImage;
    private FloatingActionButton fabLike, fabBack;
    private MealsItem meal;
    private boolean isFavorite;
    private boolean isGuest;
    MaterialButton calendarButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        initViews(view);

        setupViewPager();
        onLikeClick(meal);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.loadMealDetails();
        setupCalendarButton();
    }

    private void initViews(View view) {
        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout_details);
        titleText = view.findViewById(R.id.textTitle);
        country = view.findViewById(R.id.meal_country);
        mealImage = view.findViewById(R.id.imageView3);
        fabLike = view.findViewById(R.id.like_button);
        fabBack = view.findViewById(R.id.back_button);
        calendarButton = view.findViewById(R.id.button);

    }

    private void setupViewPager() {
        assert getArguments() != null;
        meal = getArguments().getParcelable("meal");
        isGuest = getArguments().getBoolean("Guest");
        presenter = new DetailsPresenterImp(this, meal, requireContext());
        pagerAdapter = new DetailsAdapter(requireActivity(), meal);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(pagerAdapter.getTitle(position))
        ).attach();
    }

    private void setupCalendarButton() {
        if (isGuest) {
            calendarButton.setOnClickListener(v -> showLoginRequiredDialog());
        }else {
            presenter.checkScheduleStatus(meal);
            calendarButton.setOnClickListener(v -> {
                if (meal != null) {
                    presenter.handleScheduleButtonClick(meal);
                }
            });
        }
    }

    @Override
    public void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Schedule Meal")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String dateString = sdf.format(new Date(selection));
            meal.setScheduleDate(dateString);
            presenter.addMealToSchedule(meal);
        });

        datePicker.show(getParentFragmentManager(), "MEAL_SCHEDULER");
    }
    @Override
    public void showLoginRequiredDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Login Required")
                .setMessage("You should login to use this feature")
                .setPositiveButton("Login", (dialog, which) -> {
                    Navigation.findNavController(requireView())
                            .navigate(R.id.action_detailsFragment_to_loginOptionFragment);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void showUnScheduleConfirmation(MealsItem meal) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Scheduled Meal")
                .setMessage("This meal is already scheduled. What would you like to do?")
                .setPositiveButton("Reschedule", (dialog, which) -> showDatePicker())
                .setNegativeButton("Remove", (dialog, which) ->
                        presenter.removeMealFromSchedule(meal))
                .setNeutralButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayMealDetails(MealsItem meal) {
        titleText.setText(meal.getStrMeal());
        String area = meal.getStrArea() +" recipe";
        country.setText(area);
        String imageUrl = meal.getStrMealThumb();
        Glide.with(requireContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_favorite)
                .error(R.drawable.ic_favorite)
                .centerCrop()
                .into(mealImage);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    @Override
    public void onLikeClick(MealsItem meal) {
        fabBack.setOnClickListener(v ->
                androidx.navigation.Navigation.findNavController(requireView()).navigateUp()
        );

        if (isGuest) {
            fabLike.setOnClickListener(v -> showLoginRequiredDialog());
        } else {
            fabLike.setOnClickListener(v -> {
                presenter.handleLikeButtonClick(meal, isFavorite);
            });
        }
    }

    @Override
    public void updateFavoriteStatus(boolean isFavorite) {
        this.isFavorite = isFavorite;
        meal.setFavorite(isFavorite);
        if (isFavorite) {
            fabLike.setImageResource(R.drawable.ic_favorite);
        } else {
            fabLike.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    @Override
    public void updateScheduleStatus(boolean isScheduled) {
        if (isScheduled){
            calendarButton.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.scheduled));
            calendarButton.setIconTint(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.DarkRed)));

        } else {
            calendarButton.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.unscheduled));
            calendarButton.setIconTint(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.BlackishGrey)));
        }
    }

    @Override
    public void showDeleteConfirmation(MealsItem meal) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Remove from Favorites")
                .setMessage("Are you sure you want to remove this meal from favorites?")
                .setPositiveButton("Remove", (dialog, which) -> {
                    presenter.removeMealFromFavorite(meal);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

}