package com.example.fragmentsbonus.planner.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.click_listener.OnMealClickListener;
import com.example.fragmentsbonus.models.meals.MealsItem;

import java.util.ArrayList;
import java.util.List;

public class PlannerAdapter extends RecyclerView.Adapter<PlannerAdapter.PlannerViewHolder> {
    private List<MealsItem> meal = new ArrayList<>();
    private final OnMealClickListener onMealClickListener;

    public PlannerAdapter(List<MealsItem> meals,OnMealClickListener onMealClickListener) {
        this.onMealClickListener = onMealClickListener;
        this.meal.addAll(meals);
    }

    public void setMeal(List<MealsItem> meal) {
        this.meal = meal;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_card, parent, false);
        return new PlannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlannerViewHolder holder, int position) {
        MealsItem meal = this.meal.get(position);
            if (meal != null) {
                holder.itemView.setOnClickListener(v -> {
                    if (onMealClickListener != null) {
                        onMealClickListener.onMealClick(meal);
                    }
                });
            }
            holder.titleText.setText(meal.getStrMeal());
            holder.descriptionText.setText(meal.getStrInstructions());
            Glide.with(holder.itemView.getContext())
                    .load(meal.getStrMealThumb())
                    .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return meal.size();
    }

    static class PlannerViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final TextView descriptionText;
        private final ImageView imageView;

        public PlannerViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.dishTitle);
            descriptionText = itemView.findViewById(R.id.dishDescription);
            imageView = itemView.findViewById(R.id.foodImage);
        }

    }
}
