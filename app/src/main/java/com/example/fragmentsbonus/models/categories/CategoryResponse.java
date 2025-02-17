package com.example.fragmentsbonus.models.categories;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CategoryResponse{

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	public void setCategories(List<CategoriesItem> categories){
		this.categories = categories;
	}

	public List<CategoriesItem> getCategories(){
		return categories;
	}
}