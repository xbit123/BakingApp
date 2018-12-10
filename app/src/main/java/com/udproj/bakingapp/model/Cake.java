package com.udproj.bakingapp.model;

import android.content.Context;

import com.udproj.bakingapp.R;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Cake {
    String name;
    List<Ingredient> ingredients;
    List<Step> steps;
    int servings;

    public Cake() {}

    public Cake(String name, List<Ingredient> ingredients, List<Step> steps, int servings) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public int getImageId() {
        int result;
        switch (name) {
            case "Brownies":
                result = R.drawable.ic_brownies;
                break;
            case "Cheesecake":
                result = R.drawable.ic_cheesecake;
                break;
            case "Nutella Pie":
                result = R.drawable.ic_nutella_pie;
                break;
            case "Yellow Cake":
                result = R.drawable.ic_yellow_cake;
                break;
            default:
                result = R.drawable.ic_launcher_foreground;
                break;
        }
        return result;
    }

    public String ingredientsToString(Context context) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            sb.append(ingredients.get(i).toString(context));
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }
}
