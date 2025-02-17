package com.example.fragmentsbonus.network;

public interface MealsRemoteDataSource {
    void getRandomMeals(NetworkCallBack networkCallBack);
    void getCategories(NetworkCallBack networkCallBack);
    void getMealsByCategory(String category, NetworkCallBack networkCallBack);
    
}
