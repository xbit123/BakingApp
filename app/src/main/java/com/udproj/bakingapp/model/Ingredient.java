package com.udproj.bakingapp.model;

import android.content.Context;
import android.content.res.Resources;

import com.udproj.bakingapp.utils.Constants;

import org.parceler.Parcel;

import java.text.DecimalFormat;

@Parcel
public class Ingredient {
    double quantity;
    String measure;
    String ingredient;

    public Ingredient() {}

    public Ingredient(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String toString(Context context) {
        DecimalFormat df = new DecimalFormat("#.##");
        return context.getString(Constants.INGREDIENT_MAP.get(measure), df.format(quantity), ingredient);
    }
}
