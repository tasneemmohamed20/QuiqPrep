package com.example.fragmentsbonus.search.view.all_ingredients;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.models.ingredients.IngredientItem;
import com.example.fragmentsbonus.search.click_listener.OnItemClicked;

import java.util.ArrayList;
import java.util.List;

public class AllIngredientsAdapter extends RecyclerView.Adapter<AllIngredientsAdapter.ViewHolder> {
    private List<IngredientItem> ingredients;
    private OnItemClicked listener;

    public AllIngredientsAdapter() {
        this.ingredients = new ArrayList<>();
    }
    public void clickItem(OnItemClicked listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingrediant_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientItem ingredient = ingredients.get(position);
        holder.dishTitle.setText(ingredient.getStrIngredient());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClicked(ingredient.getStrIngredient());
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void setIngredients(List<IngredientItem> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dishTitle;
        ImageView foodImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dishTitle = itemView.findViewById(R.id.dishTitleIng);
            foodImage = itemView.findViewById(R.id.foodImageIng);
        }
    }
}