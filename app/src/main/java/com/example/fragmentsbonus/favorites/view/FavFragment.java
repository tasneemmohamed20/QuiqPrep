package com.example.fragmentsbonus.favorites.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.click_listener.OnMealClickListener;
import com.example.fragmentsbonus.database.MealLocalDataSourceImp;
import com.example.fragmentsbonus.favorites.persenter.FavoritesPresenter;
import com.example.fragmentsbonus.favorites.persenter.FavoritesPresenterImp;
import com.example.fragmentsbonus.models.meals.MealsItem;
import com.example.fragmentsbonus.models.repository.MealsRepositoryImplementation;
import com.example.fragmentsbonus.network.MealsRemoteDataSourceImplementation;

import java.util.ArrayList;
import java.util.List;


public class FavFragment extends Fragment implements FavoritesView, OnMealClickListener {
    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private FavoritesPresenter presenter;
    private ProgressBar progressBar;

    public FavFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FavoritesPresenterImp((FavoritesView) this, MealsRepositoryImplementation.getInstance(MealsRemoteDataSourceImplementation.getInstance(requireContext()), MealLocalDataSourceImp.getInstance(requireContext())));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewFav);
        progressBar = view.findViewById(R.id.progressBar4);
        presenter.getFavorites();
        adapter = new FavoritesAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return view;
    }

    @Override
    public void showFavorites(List<MealsItem> meals) {
        adapter.setMeals(meals);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onMealClick(MealsItem meal) {
        Bundle args = new Bundle();
        args.putParcelable("meal", meal);
        Navigation.findNavController(requireView()).navigate(R.id.action_favFragment_to_detailsFragment, args);
    }
}