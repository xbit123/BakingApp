package com.udproj.bakingapp.details.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.udproj.bakingapp.R;
import com.udproj.bakingapp.details.viewmodel.DetailsSharedViewModel;
import com.udproj.bakingapp.model.Cake;
import com.udproj.bakingapp.utils.Constants;

import org.parceler.Parcels;

import butterknife.ButterKnife;

public class StepDetailsActivity extends AppCompatActivity {
    private DetailsSharedViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewModel = ViewModelProviders.of(this).get(DetailsSharedViewModel.class);

        if (getIntent().hasExtra(Constants.INTENT_CAKE) && getIntent().hasExtra(Constants.INTENT_STEP_ID)) {
            Cake cake = Parcels.unwrap(getIntent().getParcelableExtra(Constants.INTENT_CAKE));
            mViewModel.setCake(cake);
            int stepId = getIntent().getIntExtra(Constants.INTENT_STEP_ID, Constants.STEP_ID_DEFAULT_VALUE);
            mViewModel.setStepId(stepId);

            //Enter fullscreen mode
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getSupportActionBar().hide();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
