package com.example.fragmentsbonus.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragmentsbonus.R;
import com.example.fragmentsbonus.models.countries.CountryItem;
import com.example.fragmentsbonus.models.ingredients.IngredientItem;
import com.example.fragmentsbonus.search.presenter.categories.CategoriesPresenter;
import com.example.fragmentsbonus.search.presenter.categories.CategoriesPresenterImp;
import com.example.fragmentsbonus.search.presenter.countries.countries_row.CountriesPresenter;
import com.example.fragmentsbonus.search.presenter.countries.countries_row.CountriesPresenterImp;
import com.example.fragmentsbonus.search.presenter.ingredients.ingredients_row.IngredientsPresenter;
import com.example.fragmentsbonus.search.presenter.ingredients.ingredients_row.IngredientsPresenterImp;
import com.example.fragmentsbonus.search.view.categories.CategoriesAdapter;
import com.example.fragmentsbonus.search.view.countries.CountriesAdapter;
import com.example.fragmentsbonus.search.view.ingredients.IngredientAdapter;
import com.google.android.material.transition.platform.MaterialContainerTransform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchFragment extends Fragment implements SearchView {
    private IngredientAdapter adapter;
    private IngredientsPresenter ingredientsPresenter;
    private ProgressBar progressBar;
    private RecyclerView ingredientsRecycler, countriesRecycler, categoriesRecycler;
    private List<IngredientItem> ingredientsList;
    private CountriesPresenter countriesPresenter;
    private CountriesAdapter countriesAdapter;
    private CategoriesAdapter categoriesAdapter;
    private CategoriesPresenter categoriesPresenter;

    private EditText searchEditText;
    CompositeDisposable disposables = new CompositeDisposable();

    TextView veiwAll;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSharedElementEnterTransition(new MaterialContainerTransform());
        setSharedElementReturnTransition(new MaterialContainerTransform());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        CardView searchCard = view.findViewById(R.id.searchCard2);
        searchCard.setTransitionName("searchTransition");
        veiwAll = view.findViewById(R.id.viewAll);
        searchEditText = view.findViewById(R.id.searchBox);
        countriesRecycler = view.findViewById(R.id.countriesRecycler);
        ingredientsRecycler = view.findViewById(R.id.ingredientRecycler);
        categoriesRecycler = view.findViewById(R.id.CategotiesRecycler);

        // ingredients Recycler
        adapter = new IngredientAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        ingredientsRecycler.setLayoutManager(layoutManager);
        ingredientsRecycler.setAdapter(adapter);
        OnIngredientClick();

        // countries Recycler
        countriesAdapter = new CountriesAdapter();
        LinearLayoutManager countriesManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        countriesRecycler.setLayoutManager(countriesManager);
        countriesRecycler.setAdapter(countriesAdapter);
        OnCountryClick();

        // categories Recycler
        categoriesAdapter = new CategoriesAdapter();
        LinearLayoutManager categoriesManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        categoriesRecycler.setLayoutManager(categoriesManager);
        categoriesRecycler.setAdapter(categoriesAdapter);
        OnCategoryClick();

        // Initialize presenters
        ingredientsPresenter = new IngredientsPresenterImp(this, requireContext());
        countriesPresenter = new CountriesPresenterImp(requireContext(), this);
        categoriesPresenter = new CategoriesPresenterImp( this,requireContext());
        setupSearchObservable();
        onVeiwAllClick(ingredientsList);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ingredientsPresenter.getIngredients();
        countriesPresenter.getCountries();
        categoriesPresenter.getCategories();
    }

    @Override
    public void showIngredients(List<IngredientItem> ingredients) {
        adapter.setIngredients(ingredients);
        ingredientsList = ingredients;
    }

    @Override
    public void showProgress() {
//        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
//        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCountries(List<CountryItem> countries) {
        countriesAdapter.setCountries(countries);
    }

    @Override
    public void showCategories(List<String> categories) {
        categoriesAdapter.setCategories(categories);
    }

    @Override
    public void OnIngredientClick() {
        adapter.clickItem(ingredientName -> {
            Bundle args = new Bundle();
            args.putString("filterType", ingredientName);
            args.putString("sourceFragment", "ingredients");
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_searchFragment_to_filteredMealsFragment, args);
        });
    }

    @Override
    public void OnCountryClick() {
        countriesAdapter.clickItem(countryName -> {
            Bundle args = new Bundle();
            args.putString("filterType", countryName);
            args.putString("sourceFragment", "countries");
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_searchFragment_to_filteredMealsFragment, args);
        });
    }

    @Override
    public void OnCategoryClick() {
        categoriesAdapter.clickItem(categoryName -> {
            Bundle args = new Bundle();
            args.putString("filterType", categoryName);
            args.putString("sourceFragment", "categories");
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_searchFragment_to_filteredMealsFragment, args);
        });
    }

    @Override
    public void onVeiwAllClick(List<IngredientItem> ingredients) {
        veiwAll.setOnClickListener(v -> {
            Bundle args = new Bundle();
            ArrayList<IngredientItem> parcelableList = new ArrayList<>(ingredientsList);

            args.putParcelableArrayList("ingredientsList", parcelableList);

            Navigation.findNavController(requireView())
                    .navigate(R.id.action_searchFragment_to_allIngredientsFragment, args);

        });
    }

    private void setupSearchObservable() {
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

            searchEditText.addTextChangedListener(textWatcher);
            emitter.setCancellable(() -> searchEditText.removeTextChangedListener(textWatcher));
        });

        disposables.add(
                searchObservable
                        .debounce(300, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(query -> {
                            filterLists(query);
                        })
        );
    }

    private void filterLists(String query) {
        if (query.isEmpty()) {
            if (ingredientsList != null) {
                adapter.setIngredients(ingredientsList);
            }
            countriesPresenter.getCountries();
            categoriesPresenter.getCategories();
        } else {

            if (ingredientsList != null) {
                List<IngredientItem> filteredIngredients = ingredientsList.stream()
                        .filter(ingredient ->
                                ingredient.getStrIngredient().toLowerCase()
                                        .contains(query.toLowerCase()))
                        .collect(Collectors.toList());

                // If no matches found, show original list
                if (filteredIngredients.isEmpty()) {
                    adapter.setIngredients(ingredientsList);
                } else {
                    adapter.setIngredients(filteredIngredients);
                }
            }

            countriesPresenter.filterCountries(query);
            categoriesPresenter.filterCategories(query);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (ingredientsPresenter != null) {
            ingredientsPresenter.detachView();
        }
        if (countriesPresenter != null) {
            countriesPresenter.detachView();
        }
        if (categoriesPresenter != null) {
            categoriesPresenter.detachView();
        }
    }
}