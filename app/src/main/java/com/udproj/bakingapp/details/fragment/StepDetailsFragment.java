package com.udproj.bakingapp.details.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udproj.bakingapp.R;
import com.udproj.bakingapp.details.viewmodel.DetailsSharedViewModel;
import com.udproj.bakingapp.details.viewmodel.PlayerViewModel;
import com.udproj.bakingapp.model.Step;
import com.udproj.bakingapp.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsFragment extends Fragment {

    @BindView(R.id.tv_recipe_step_instructions)
    TextView mTvInstructions;

    @BindView(R.id.pv_step_video)
    PlayerView mPvStep;

    private DetailsSharedViewModel mViewModel;
    private PlayerViewModel mPlayerViewModel;
    private SimpleExoPlayer mPlayer;

    public StepDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, view);

        mPlayerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        mViewModel = ViewModelProviders.of(getActivity()).get(DetailsSharedViewModel.class);
        mViewModel.getStepId().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer stepId) {
                Step step = mViewModel.getSteps().get(stepId);
                mTvInstructions.setText(step.getDescription());

                setupPlayer(step.getVideoURL());
            }
        });
        return view;
    }

    private void setupPlayer(String videoUrl) {
        if (mPlayer != null)
            mPlayer.release();
        if (videoUrl == null || videoUrl.isEmpty()) {
            mPvStep.setVisibility(View.GONE);
        } else {
            mPlayer = ExoPlayerFactory.newSimpleInstance(getActivity());
            mPvStep.setVisibility(View.VISIBLE);

            Uri videoUri = Uri.parse(videoUrl);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(),"exo_player"));
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
            mPlayer.prepare(videoSource);

            mPvStep.setPlayer(mPlayer);
            mPlayerViewModel.setIsPlaying(true);
            mPlayer.setPlayWhenReady(true);
        }
        mPvStep.setMinimumHeight(mPvStep.getWidth() * Constants.DEFAULT_ASPECT_RATIO_HEIGHT / Constants.DEFAULT_ASPECT_RATIO_WIDTH);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPlayer != null ) {
            mPlayer.setPlayWhenReady(mPlayerViewModel.isPlaying());
            mPlayer.seekTo(mPlayerViewModel.getPosition());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlayer != null ) {
            mPlayerViewModel.setIsPlaying(mPlayer.getPlayWhenReady());
            mPlayerViewModel.setPosition(mPlayer.getCurrentPosition());
            mPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null ) {
            mPlayer.release();
        }
    }
}
