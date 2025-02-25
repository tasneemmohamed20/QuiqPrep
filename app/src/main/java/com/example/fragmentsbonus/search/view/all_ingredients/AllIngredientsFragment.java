package com.example.fragmentsbonus.search.view.all_ingredients;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.fragmentsbonus.models.ingredients.IngredientItem;
import com.example.fragmentsbonus.search.presenter.ingredients.all_ingredients.AllIngredientsPresenter;
import com.example.fragmentsbonus.search.presenter.ingredients.all_ingredients.AllIngredientsPresenterImp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AllIngredientsFragment extends Fragment implements AllIngredientsView {
    private ArrayList<IngredientItem> ingredients;
    private AllIngredientsPresenter presenter;
    private AllIngredientsAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private EditText searchBox;
    private CompositeDisposable disposables = new CompositeDisposable();

    public AllIngredientsFragment() {
        // Required empty public constructor
    }


    public static AllIngredientsFragment newInstance(String param1, String param2) {
        AllIngredientsFragment fragment = new AllIngredientsFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ingredients =
                    getArguments().getParcelableArrayList("ingredientsList");
        }
//        Toast.makeText(getContext(), "Ingredients: " + ingredients.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_ingredients, container, false);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars()
                    | WindowInsetsCompat.Type.ime());

            // Apply padding to avoid content going under the navigation bar
            view.setPadding(0, insets.top, 0, 0);

            return WindowInsetsCompat.CONSUMED;
        });
        recyclerView = view.findViewById(R.id.ingredientRecyclerAll);
//        progressBar = view.findViewById(R.id.progressBar);
        searchBox = view.findViewById(R.id.searchBoxAll);
        setupSearchObservable();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AllIngredientsAdapter();
        recyclerView.setAdapter(adapter);
        onIngredientClick();

        presenter = new AllIngredientsPresenterImp(this, ingredients);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getIngredients();
    }

    @Override
    public void showIngredients(List<IngredientItem> ingredients) {
        adapter.setIngredients(ingredients);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
            emitter.setCancellable(() -> searchBox.removeTextChangedListener(textWatcher));
        });

        disposables.add(
                searchObservable
                        .debounce(300, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(searchText -> presenter.filterIngredients(searchText))
        );
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onIngredientClick() {
        adapter.clickItem(ingredientName -> {
            Bundle args = new Bundle();
            args.putString("filterType", ingredientName);
            args.putString("sourceFragment", "ingredients");
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_allIngredientsFragment_to_filteredMealsFragment, args);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
        presenter.detachView();
    }

}