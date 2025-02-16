package com.example.fragmentsbonus.home.presenter.categories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.example.fragmentsbonus.home.model.categories.CategoriesItem;
import com.example.fragmentsbonus.home.model.categories.CategoryResponse;
import com.example.fragmentsbonus.home.view.categories.CategoriesView;
import com.example.fragmentsbonus.network.ApiClient;
import com.example.fragmentsbonus.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesPresenterImplementation implements  CategoriesPresenter {

    private CategoriesView view;
    private final ApiService apiService;
    private LifecycleOwner lifecycleOwner;

    public CategoriesPresenterImplementation(CategoriesView view, LifecycleOwner lifecycleOwner, Context context) {
        apiService = ApiClient.getClient().create(ApiService.class);
        this.view = view;
    }

    @Override
    public void loadCategories() {

        view.showLoading();

        Call<CategoryResponse> call = apiService.getCategories();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    List<CategoriesItem> categories = response.body().getCategories();
                    view.showCategories(categories);
                } else {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable t) {
                view.hideLoading();
                view.onErrorLoading("Error Loading");
            }
        });
    }

    @Override
    public void detachView() {
        view = null;
    }
}
