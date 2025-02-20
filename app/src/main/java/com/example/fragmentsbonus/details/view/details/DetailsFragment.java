package com.example.fragmentsbonus.details.view.details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.fragmentsbonus.models.meals.MealsItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DetailsFragment extends Fragment implements DetailsView {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private DetailsAdapter pagerAdapter;
    private DetailsPresenter presenter;
    private TextView titleText;
    private TextView country;
    private ImageView mealImage;
    private FloatingActionButton fabLike, fabBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        initViews(view);
        setupViewPager();
        setupClickListeners();

        return view;
    }

    private void initViews(View view) {
        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout_details);
        titleText = view.findViewById(R.id.textTitle);
        country = view.findViewById(R.id.meal_country);
        mealImage = view.findViewById(R.id.imageView3);
        fabLike = view.findViewById(R.id.like_button);
        fabBack = view.findViewById(R.id.back_button);
    }

    private void setupViewPager() {
        assert getArguments() != null;
        MealsItem meal = getArguments().getParcelable("meal");
        presenter = new DetailsPresenterImp(this, meal);
        pagerAdapter = new DetailsAdapter(requireActivity(), meal);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(pagerAdapter.getTitle(position))
        ).attach();
    }

    private void setupClickListeners() {
        fabBack.setOnClickListener(v -> {
            // Use NavController to navigate back
            androidx.navigation.Navigation.findNavController(requireView()).navigateUp();
        });

        fabLike.setOnClickListener(v -> {
            // Implement favorite functionality
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadMealDetails();
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
}