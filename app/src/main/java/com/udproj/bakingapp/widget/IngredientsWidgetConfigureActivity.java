package com.udproj.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.udproj.bakingapp.R;
import com.udproj.bakingapp.model.Cake;
import com.udproj.bakingapp.model.Ingredient;
import com.udproj.bakingapp.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The configuration screen for the {@link IngredientsWidget IngredientsWidget} AppWidget.
 */
public class IngredientsWidgetConfigureActivity extends AppCompatActivity {

    @BindView(R.id.rg_widget_configure)
    RadioGroup rgCakes;

    @BindView(R.id.add_button)
    Button btnAdd;

    private static final String PREFS_NAME = "com.udproj.bakingapp.widget.IngredientsWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private MutableLiveData<List<Cake>> cakes = new MutableLiveData<>();
    private int mSelectedCake = 0;
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;


    public IngredientsWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, List<Ingredient> ingredients) {
        Gson gson = new Gson();
        String jsonText = gson.toJson(ingredients);

        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, jsonText);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static List<Ingredient> loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String jsonText = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        Gson gson = new Gson();
        if (jsonText != null) {
            Ingredient[] ingredientsSA = gson.fromJson(jsonText, Ingredient[].class);
            return new ArrayList<>(Arrays.asList(ingredientsSA));
        } else {
            return null;
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.ingredients_widget_configure);
        ButterKnife.bind(this);

        //findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        NetworkUtils.loadCakes(cakes, getApplication());
        cakes.observe(this, new Observer<List<Cake>>() {
            @Override
            public void onChanged(@Nullable final List<Cake> cakesList) {
                if (cakesList == null) {
                    //Error happened while loading data
                    btnAdd.setText(getResources().getText(R.string.try_again));
                    btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            NetworkUtils.loadCakes(cakes, getApplication());
                        }
                    });
                } else {
                    btnAdd.setText(getResources().getText(R.string.add_widget));
                    addRadioButtons(cakesList);

                    btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final Context context = IngredientsWidgetConfigureActivity.this;

                            // When the button is clicked, store the string locally
                            saveTitlePref(context, mAppWidgetId, cakesList.get(mSelectedCake).getIngredients());

                            // It is the responsibility of the configuration activity to update the app widget
                            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                            //IngredientsWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);
                            appWidgetManager.notifyAppWidgetViewDataChanged(mAppWidgetId, R.id.widget_grid_view);

                            // Make sure we pass back the original appWidgetId
                            Intent resultValue = new Intent();
                            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                            setResult(RESULT_OK, resultValue);
                            finish();
                        }
                    });
                }
            }
        });
    }

    private void addRadioButtons(List<Cake> cakes) {
        for (int i = 0; i < cakes.size(); i++) {
            RadioButton rb = new RadioButton(this);
            rb.setText(cakes.get(i).getName());
            rb.setId(i);
            rgCakes.addView(rb);
        }

        rgCakes.check(0);

        rgCakes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mSelectedCake = i;
            }
        });
    }
}

