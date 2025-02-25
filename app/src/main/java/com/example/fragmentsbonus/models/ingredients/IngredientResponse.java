package com.example.fragmentsbonus.models.ingredients;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientResponse{
	@SerializedName("meals")
	private List<IngredientItem> Ingredient;

	public List<IngredientItem> getIngredient(){
		return Ingredient;
	}
}