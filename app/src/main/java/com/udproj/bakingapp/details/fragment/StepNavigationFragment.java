package com.udproj.bakingapp.details.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.udproj.bakingapp.R;
import com.udproj.bakingapp.details.DetailsSharedViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepNavigationFragment extends Fragment {

    @BindView(R.id.iv_previous_step)
    ImageView mIvPrevious;

    @BindView(R.id.iv_next_step)
    ImageView mIvNext;

    private DetailsSharedViewModel mViewModel;


    public StepNavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_navigation, container, false);
        ButterKnife.bind(this, view);

        mIvPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.decStepId();
            }
        });

        mIvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.incStepId();
            }
        });

        mViewModel = ViewModelProviders.of(getActivity()).get(DetailsSharedViewModel.class);
        mViewModel.getStepId().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer stepId) {
                mIvPrevious.setVisibility(View.VISIBLE);
                mIvNext.setVisibility(View.VISIBLE);
                if (stepId == 0) {
                    mIvPrevious.setVisibility(View.INVISIBLE);
                } else {
                    if (mViewModel.getSteps().size() == stepId + 1) {
                        mIvNext.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        return view;
    }
}
