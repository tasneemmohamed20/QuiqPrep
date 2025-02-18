package com.example.fragmentsbonus.home.view.cat_meals_list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fragmentsbonus.R;

import com.example.fragmentsbonus.home.view.click_listener.OnMealClickListener;
import com.example.fragmentsbonus.models.meals.MealsItem;

import java.util.List;

public class CatMealsAdapter extends RecyclerView.Adapter<CatMealsAdapter.CatMealsViewHolder> {
    private final List<MealsItem> meals;
    private OnMealClickListener onMealClickListener;

    public CatMealsAdapter(List<MealsItem> meals, OnMealClickListener onMealClickListener) {
        this.meals = meals;
        this.onMealClickListener = onMealClickListener;

    }

    @NonNull
    @Override
    public CatMealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_card, parent, false);
        return new CatMealsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatMealsViewHolder holder, int position) {
        MealsItem meal = meals.get(position);
        if (meal != null) {
            holder.itemView.setOnClickListener(v -> {
                if (onMealClickListener != null) {
                    onMealClickListener.onMealClick(meal);
                }
            });
            // Set title
            holder.titleText.setText(meal.getStrMeal() != null ?
                    meal.getStrMeal() : "No title");
            // Load image
            if (meal.getStrMealThumb() != null && !meal.getStrMealThumb().isEmpty()) {
                try {
                    Glide.with(holder.itemView.getContext())
                            .load(meal.getStrMealThumb())
                            .placeholder(R.drawable.ic_favorite)
                            .error(R.drawable.ic_favorite)
                            .into(holder.foodImage);
                } catch (Exception e) {
                    holder.foodImage.setImageResource(R.drawable.ic_favorite);
                }
            } else {
                holder.foodImage.setImageResource(R.drawable.ic_favorite);
            }
        }
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class CatMealsViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
//        private final TextView descriptionText;
        private final ImageView foodImage;

        public CatMealsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.dishTitle);
//            descriptionText = itemView.findViewById(R.id.dishDescription);
            foodImage = itemView.findViewById(R.id.foodImage);
        }


    }
}