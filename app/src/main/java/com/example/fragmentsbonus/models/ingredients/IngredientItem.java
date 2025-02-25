package com.example.fragmentsbonus.models.ingredients;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class IngredientItem implements Parcelable {
	private String strIngredient;

	public String getStrIngredient(){
		return strIngredient;
	}

	protected IngredientItem(Parcel in) {
		strIngredient = in.readString();
	}
	public static final Creator<IngredientItem> CREATOR = new Creator<IngredientItem>() {
		@Override
		public IngredientItem createFromParcel(Parcel in) {
			return new IngredientItem(in);
		}

		@Override
		public IngredientItem[] newArray(int size) {
			return new IngredientItem[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel dest, int flags) {
		dest.writeString(this.strIngredient);
	}
}
