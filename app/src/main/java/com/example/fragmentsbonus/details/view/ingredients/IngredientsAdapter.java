package com.example.fragmentsbonus.details.view.ingredients;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.models.meals.MealsItem;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private final MealsItem meal;
    private final List<Pair<String, String>> ingredients;

    public IngredientsAdapter(MealsItem meal) {
        this.meal = meal;
        this.ingredients = new ArrayList<>();
        processIngredients();
    }

    private void processIngredients() {
        if (meal == null) {
            Log.e("IngredientsAdapter", "Meal object is null");
            return;
        }
        // Use reflection to get all ingredient fields dynamically
        Class<?> mealClass = meal.getClass();
        for (int i = 1; i <= 20; i++) {
            try {
                String ingredientField = "StrIngredient" + i;
                String measureField = "StrMeasure" + i;

                String ingredient = (String) mealClass.getMethod("get" + ingredientField).invoke(meal);
                String measure = (String) mealClass.getMethod("get" + measureField).invoke(meal);

                Log.d("IngredientsAdapter", "Ingredient " + i + ": " + ingredient + ", Measure: " + measure);


                // Add only if ingredient is not null or empty
                if (ingredient != null && !ingredient.trim().isEmpty()) {
                    ingredients.add(new Pair<>(ingredient.trim(),
                            measure != null ? measure.trim() : ""));
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            Log.d("IngredientsAdapter", "Total ingredients processed: " + ingredients.size());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingerdients_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder holder, int position) {
        Pair<String, String> ingredient = ingredients.get(position);
        holder.ingredient.setText(ingredient.first);
        holder.measure.setText(ingredient.second);

        String imageUrl = "https://www.themealdb.com/images/ingredients/" +
                ingredient.first.replace(" ", "%20") + ".png";
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView ingredient, measure;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient = itemView.findViewById(R.id.ingrediant);
            measure = itemView.findViewById(R.id.measurment);
            imageView = itemView.findViewById(R.id.imageView6);

        }
    }
}