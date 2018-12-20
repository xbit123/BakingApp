package com.udproj.bakingapp.details.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.PlayerView;
import com.udproj.bakingapp.R;
import com.udproj.bakingapp.details.ExoPlayerManager;
import com.udproj.bakingapp.details.DetailsSharedViewModel;
import com.udproj.bakingapp.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsFragment extends Fragment {

    @BindView(R.id.tv_recipe_step_instructions)
    TextView mTvInstructions;

    @BindView(R.id.pv_step_video)
    PlayerView mPvStep;

    private DetailsSharedViewModel mViewModel;
    private ExoPlayerManager mExoPlayerManager;

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, view);

        mExoPlayerManager = new ExoPlayerManager(mPvStep, getContext());
        mViewModel = ViewModelProviders.of(getActivity()).get(DetailsSharedViewModel.class);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        mExoPlayerManager.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mExoPlayerManager.onResume();

        mViewModel.getStepId().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer stepId) {
                Step step = mViewModel.getSteps().get(stepId);
                mTvInstructions.setText(step.getDescription());

                mExoPlayerManager.setUrl(step.getVideoURL());
                mExoPlayerManager.play();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mExoPlayerManager.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mExoPlayerManager.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mExoPlayerManager.saveState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mExoPlayerManager.restoreState(savedInstanceState);
    }
}
