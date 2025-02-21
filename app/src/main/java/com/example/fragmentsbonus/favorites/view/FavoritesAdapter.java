package com.example.fragmentsbonus.favorites.view;

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

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private final List<MealsItem> meals = new ArrayList<>();
    private final OnMealClickListener onMealClickListener;

    public FavoritesAdapter(List<MealsItem> meals, OnMealClickListener onMealClickListener) {
        this.onMealClickListener = onMealClickListener;
        this.meals.addAll(meals);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealsItem meal = meals.get(position);
        if (meal != null) {
            holder.itemView.setOnClickListener(v -> {
                if (onMealClickListener != null) {
                    onMealClickListener.onMealClick(meal);
                }
            });
        }
        holder.tvTitle.setText(meal.getStrMeal());
        String area = meal.getStrArea() +" recipe";
        holder.tvArea.setText(area);
        Glide.with(holder.itemView.getContext())
                .load(meal.getStrMealThumb())
                .into(holder.ivMeal);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setMeals(List<MealsItem> meals) {
        this.meals.clear();
        this.meals.addAll(meals);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle, tvArea;
        private final ImageView ivMeal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.dishTitle);
            ivMeal = itemView.findViewById(R.id.foodImage);
            tvArea = itemView.findViewById(R.id.dishDescription);

        }
    }
}
