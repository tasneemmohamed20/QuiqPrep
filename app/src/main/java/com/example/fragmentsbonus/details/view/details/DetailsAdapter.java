package com.example.fragmentsbonus.details.view.details;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fragmentsbonus.details.view.ingredients.IngredientsFragment;
import com.example.fragmentsbonus.details.view.instructions.InstructionsFragment;
import com.example.fragmentsbonus.details.view.tutorial.TutorialFragment;
import com.example.fragmentsbonus.models.meals.MealsItem;

// DetailsPagerAdapter.java
public class DetailsAdapter extends FragmentStateAdapter {
    private MealsItem meal;
    private final String[] tabs = {"Ingredients", "Instructions", "Tutorial"};

    public DetailsAdapter(@NonNull FragmentActivity fragmentActivity, MealsItem meal) {
        super(fragmentActivity);
        this.meal = meal;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return IngredientsFragment.newInstance(meal);
           case 1:
               return InstructionsFragment.newInstance(meal);
            case 2:
                return TutorialFragment.newInstance(meal);
            default:
                return new Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return tabs.length;
    }

    public String getTitle(int position) {
        return tabs[position];
    }
}