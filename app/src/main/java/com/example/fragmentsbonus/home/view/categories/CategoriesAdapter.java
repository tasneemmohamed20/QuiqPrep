package com.example.fragmentsbonus.home.view.categories;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fragmentsbonus.home.model.categories.CategoriesItem;
import com.example.fragmentsbonus.home.view.cat_meals_list.CatMealsListFragment;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends FragmentStateAdapter {
    private final List<Fragment> fragments;
    private final List<String> fragmentTitles;
    private List<CategoriesItem> categories;

    public CategoriesAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.fragments = new ArrayList<>();
        this.fragmentTitles = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    public void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        fragmentTitles.add(title);
    }

    public void setCategories(List<CategoriesItem> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        CatMealsListFragment fragment = new CatMealsListFragment();
        if (position < categories.size()) {
            Bundle args = new Bundle();
            args.putString("category", categories.get(position).getStrCategory());
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public String getTitle(int position) {
        if (position < categories.size()) {
            return categories.get(position).getStrCategory();
        }
        return "";
    }
}