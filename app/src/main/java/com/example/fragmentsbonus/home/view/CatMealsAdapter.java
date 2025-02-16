package com.example.fragmentsbonus.home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fragmentsbonus.R;

import com.example.fragmentsbonus.home.model.random_meal.MealsItem;

import java.util.List;

public class CatMealsAdapter extends RecyclerView.Adapter<CatMealsAdapter.CatMealsViewHolder> {
    private List<MealsItem> meals;

    public CatMealsAdapter(List<MealsItem> meals) {
        this.meals = meals;
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
            // Set title
            holder.titleText.setText(meal.getStrMeal() != null ?
                    meal.getStrMeal() : "No title");

//            holder.descriptionText.setText(meal.getStrInstructions() != null ?
//                    meal.getStrInstructions() : "No description");

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