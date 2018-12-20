package com.udproj.bakingapp.details.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udproj.bakingapp.R;
import com.udproj.bakingapp.details.DetailsSharedViewModel;
import com.udproj.bakingapp.model.Cake;
import com.udproj.bakingapp.utils.Constants;

import org.parceler.Parcels;

public class RecipeDetailsActivity extends AppCompatActivity{

    private DetailsSharedViewModel detailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        detailsViewModel = ViewModelProviders.of(this).get(DetailsSharedViewModel.class);

        if (getIntent().hasExtra(Constants.INTENT_CAKE)) {
            Cake cake = Parcels.unwrap(getIntent().getParcelableExtra(Constants.INTENT_CAKE));
            detailsViewModel.setCake(cake);
        }

        //Start step details activity on click if the current activity is in mobile singlepane mode
        if (!getResources().getBoolean(R.bool.has_two_panes)) {
            detailsViewModel.resetStepId();
            detailsViewModel.getStepId().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer stepId) {
                    startStepDetailsActivity(stepId);
                }
            });
        }
    }

    private void startStepDetailsActivity(int stepId) {
        Intent intent = new Intent(this, StepDetailsActivity.class);
        intent.putExtra(Constants.INTENT_CAKE, Parcels.wrap(detailsViewModel.getCake()));
        intent.putExtra(Constants.INTENT_STEP_ID, stepId);
        startActivity(intent);
    }
}
