package com.example.fragmentsbonus.search.view.filtered;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.models.meals.MealsItem;
import com.example.fragmentsbonus.search.presenter.filtered.by_category.CatFilterPresenter;
import com.example.fragmentsbonus.search.presenter.filtered.by_category.CatFilterPresenterImp;
import com.example.fragmentsbonus.search.presenter.filtered.by_country.CounFilterPresenter;
import com.example.fragmentsbonus.search.presenter.filtered.by_country.CounFilterPresenterImp;
import com.example.fragmentsbonus.search.presenter.filtered.by_ingredient.IngFilterPresenter;
import com.example.fragmentsbonus.search.presenter.filtered.by_ingredient.IngFilterPresenterImp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FilteredMealsFragment extends Fragment implements FilteredView {
    private String filterType;
    private String sourceFragment;
    private FilteredAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView filteredRecyclerView;
    private EditText searchBox;
    private CompositeDisposable disposables = new CompositeDisposable();
    private List<MealsItem> allMeals = new ArrayList<>();
    private IngFilterPresenter ingFilterPresenter;
    private CounFilterPresenter countriesPresenter;
    private CatFilterPresenter categoriesPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filterType = getArguments().getString("filterType");
            sourceFragment = getArguments().getString("sourceFragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtered_meals, container, false);

        // Initialize views
        progressBar = view.findViewById(R.id.progressBar4);
        filteredRecyclerView = view.findViewById(R.id.filter_recyclerView);
        searchBox = view.findViewById(R.id.searchBoxFiltered);

        // Setup RecyclerView
        adapter = new FilteredAdapter();
        filteredRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        filteredRecyclerView.setAdapter(adapter);

        OnMealClick();

        setupSearchObservable();

        // Initialize presenter
        ingFilterPresenter = new IngFilterPresenterImp(this, requireContext());
        countriesPresenter = new CounFilterPresenterImp(this, requireContext());
        categoriesPresenter = new CatFilterPresenterImp(this, requireContext());

        if ("ingredients".equals(sourceFragment)) {
            ingFilterPresenter.getMealsByIngredient(filterType);
        } else if ("countries".equals(sourceFragment)) {
            countriesPresenter.getMealsByCountry(filterType);
        }else {
            categoriesPresenter.getMealsByCategory(filterType);
        }
        return view;
    }

    @Override
    public void showFilteredMeals(List<MealsItem> meals) {
        adapter.setMeals(meals);
        this.allMeals = new ArrayList<>(meals);
    }

    @Override
    public void showFilteredError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnMealClick() {
        adapter.onMealClick(mealsItem -> {
            Bundle args = new Bundle();
            args.putParcelable("meal", mealsItem);
            Navigation.findNavController(requireView()).navigate(
                    R.id.action_filteredMealsFragment_to_detailsFragment,
                    args
            );
                }
        );
    }

    @Override
    public void setupSearchObservable() {
        Observable<String> searchObservable = Observable.create(emitter -> {
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    emitter.onNext(s.toString());
                }
            };

            searchBox.addTextChangedListener(textWatcher);
            // Clean up when disposed
            emitter.setCancellable(() -> searchBox.removeTextChangedListener(textWatcher));
        });

        disposables.add(
                searchObservable
                        .debounce(300, TimeUnit.MILLISECONDS) // Add delay to avoid rapid firing
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(query -> {
                            // Use appropriate presenter based on source fragment
                            if ("ingredients".equals(sourceFragment)) {
                                ingFilterPresenter.filterMeals(query);
                            } else if ("countries".equals(sourceFragment)) {
                                countriesPresenter.filterMeals(query);
                            } else {
                                categoriesPresenter.filterMeals(query);
                            }
                        })
        );
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (ingFilterPresenter != null) {
            ingFilterPresenter.dispose();
        }
        else if (countriesPresenter != null) {
            countriesPresenter.dispose();
        }
        else if (categoriesPresenter != null) {
            categoriesPresenter.dispose();
        }
    }
}