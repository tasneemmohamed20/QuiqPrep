package com.example.fragmentsbonus.network;


import android.content.Context;
import android.net.http.NetworkException;

import androidx.annotation.NonNull;

import com.example.fragmentsbonus.models.categories.CategoryResponse;
import com.example.fragmentsbonus.models.countries.CountriesResponse;
import com.example.fragmentsbonus.models.ingredients.IngredientResponse;
import com.example.fragmentsbonus.models.meals.MealResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSourceImplementation implements MealsRemoteDataSource {
    private static final String TAG = "MealsClient";
    private static MealsRemoteDataSourceImplementation client = null;
    private final ApiService apiService;
    Context context;

    private MealsRemoteDataSourceImplementation(Context context) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api_strings.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())

                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static synchronized MealsRemoteDataSourceImplementation getInstance(Context context) {
        if(client == null){
            client = new MealsRemoteDataSourceImplementation(context);
        }
        return client;
    }
    private Single<?> checkNetwork(Single<?> apiCall) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            return Single.error(new NetException("No internet connection. Please check your connection and try again."));
        }
        return apiCall;
    }
    @Override
    public  Single<MealResponse> getRandomMeals() {
        return (Single<MealResponse>) checkNetwork(apiService.getRandomMeal());
    }

    @Override
    public  Single<CategoryResponse> getCategories() {
        return (Single<CategoryResponse>) checkNetwork(apiService.getCategories());
    }

    @Override
    public  Single<MealResponse> getMealsByCategory(String category) {
        return (Single<MealResponse>) checkNetwork(apiService.getMealByCategory(category));
    }

    @Override
    public Single<MealResponse> getMealById(String id) {
        return (Single<MealResponse>) checkNetwork(apiService.getMealById(id));
    }

    @Override
    public Single<IngredientResponse> getIngredients() {
        return(Single<IngredientResponse>) checkNetwork(apiService.getIngredients());
    }

    @Override
    public Single<CountriesResponse> getCountries() {
        return (Single<CountriesResponse>) checkNetwork(apiService.getCountries());
    }

    @Override
    public Single<MealResponse> getMealsByIngredient(String ingredient) {
        return (Single<MealResponse>) checkNetwork(apiService.getMealsByIngredient(ingredient));
    }

    @Override
    public Single<MealResponse> getMealsByCountry(String country) {
        return (Single<MealResponse>) checkNetwork(apiService.getMealsByCountry(country));
    }

    @Override
    public void dispose() {
        client = null;
    }

}
