package com.example.fragmentsbonus.home.view.random_meal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.home.view.click_listener.OnMealClickListener;
import com.example.fragmentsbonus.models.meals.MealsItem;

public class RandomBinder {
    private ImageView foodImage;
    private TextView titleText;
    private TextView descriptionText;
    private View rootView;

    private OnMealClickListener onMealClickListener;

    public void bind(View view, MealsItem meal) {
        this.rootView = view;
        initializeViews();
        setMealData(meal);
        setupClickListeners(meal);
    }
    public void setOnMealClickListener(OnMealClickListener listener) {
        this.onMealClickListener = listener;
    }

    private void setupClickListeners(final MealsItem meal) {
        rootView.setOnClickListener(v -> {
            if (onMealClickListener != null) {
                onMealClickListener.onMealClick(meal);
            }
        });
    }

    private void initializeViews() {
        foodImage = rootView.findViewById(R.id.foodImagerand);
        titleText = rootView.findViewById(R.id.dishTitlerand);
        descriptionText = rootView.findViewById(R.id.dishDescriptionrand);
    }

    private void setMealData(MealsItem meal) {
        if (meal != null) {
            titleText.setText(meal.getStrMeal());
            String instructions = meal.getStrArea() +" recipe";

            descriptionText.setText(instructions);



            Glide.with(rootView.getContext())
                    .load(meal.getStrMealThumb())
                    .centerCrop()
                    .into(foodImage);
        }
    }
}