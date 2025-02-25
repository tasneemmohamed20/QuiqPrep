package com.example.fragmentsbonus.search.view.filtered;

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

public class FilteredAdapter extends RecyclerView.Adapter<FilteredAdapter.ViewHolder> {
    private List<MealsItem> meals;
    private OnMealClickListener listener;
    public FilteredAdapter() {
        this.meals = new ArrayList<>();
    }
    public void onMealClick(OnMealClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealsItem meal = meals.get(position);
        holder.titleText.setText(meal.getStrMeal());
        String area = meal.getStrArea() +" recipe";
        holder.areaText.setText(meal.getStrArea() + " recipe");

        Glide.with(holder.itemView.getContext())
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.ic_favorite)
                .error(R.drawable.ic_favorite)
                .into(holder.foodImage);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMealClick(meal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setMeals(List<MealsItem> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView areaText;
        ImageView foodImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.dishTitle);
            areaText = itemView.findViewById(R.id.dishDescription);
            foodImage = itemView.findViewById(R.id.foodImage);
        }
    }
}
