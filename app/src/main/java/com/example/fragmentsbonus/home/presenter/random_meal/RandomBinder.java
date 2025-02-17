package com.example.fragmentsbonus.home.presenter.random_meal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.models.meals.MealsItem;

public class RandomBinder {
    private ImageView foodImage;
    private TextView titleText;
    private TextView descriptionText;
    private TextView timeText;
    private TextView ratingText;
    private TextView priceText;
    private View rootView;

    public void bind(View view, MealsItem meal) {
        this.rootView = view;
        initializeViews();
        setMealData(meal);
    }

    private void initializeViews() {
        foodImage = rootView.findViewById(R.id.foodImagerand);
        titleText = rootView.findViewById(R.id.dishTitlerand);
        descriptionText = rootView.findViewById(R.id.dishDescriptionrand);
    }

    private void setMealData(MealsItem meal) {
        if (meal != null) {
            titleText.setText(meal.getStrMeal());
            String instructions = meal.getStrInstructions();

            descriptionText.setText(instructions);



            Glide.with(rootView.getContext())
                    .load(meal.getStrMealThumb())
                    .centerCrop()
                    .into(foodImage);
        }
    }
}