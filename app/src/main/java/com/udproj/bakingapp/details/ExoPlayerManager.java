package com.udproj.bakingapp.details;

import android.content.Context;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udproj.bakingapp.utils.Constants;

public class ExoPlayerManager {
    private static final String LOGTAG = "EXOPLAYERMANAGERTAG";
    private static final String POSITION_KEY = "POSITION";
    private static final String IS_PLAYING_KEY = "IS_PLAYING";
    private SimpleExoPlayer mPlayer;
    private PlayerView mPvStep;
    private long mPosition;
    private boolean mPlaying;
    private Context mContext;
    private String mUrl;

    public ExoPlayerManager(PlayerView playerView, Context context) {
        Log.v(LOGTAG, "constructor");
        mPvStep = playerView;
        mContext = context;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void play() {
        Log.v(LOGTAG, "play");
        if (mUrl == null || mUrl.isEmpty()) {
            mPvStep.setVisibility(View.GONE);
            mPlayer.setPlayWhenReady(false);
        } else {
            mPvStep.setVisibility(View.VISIBLE);
            Uri videoUri = Uri.parse(mUrl);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, "exo_player"));
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
            mPlayer.prepare(videoSource);

            mPlayer.seekTo(mPosition);
            mPlayer.setPlayWhenReady(mPlaying);
            mPosition = 0;
            mPlaying = true;
        }
    }

    public void onStart() {
        if (Util.SDK_INT > 23) {
            createPlayer();
        }
    }

    public void onResume() {
        if (Util.SDK_INT <= 23) {
            createPlayer();
        }
    }

    public void onPause() {
        mPosition = mPlayer.getCurrentPosition();
        mPlaying = mPlayer.getPlayWhenReady();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    public void onStop() {
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        Log.v(LOGTAG, "releasePlayer");
        if (mPlayer != null) {
            mPlayer.setPlayWhenReady(false);
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void createPlayer() {
        Log.v(LOGTAG, "createPlayer");
        if (mPlayer != null) {
            mPlayer.release();
        }
        mPlayer = ExoPlayerFactory.newSimpleInstance(mPvStep.getContext());
        mPvStep.setVisibility(View.VISIBLE);
        mPvStep.setMinimumHeight(mPvStep.getWidth() * Constants.DEFAULT_ASPECT_RATIO_HEIGHT / Constants.DEFAULT_ASPECT_RATIO_WIDTH);
        mPvStep.setPlayer(mPlayer);
    }

    public void saveState(Bundle outBundle) {
        /*if (Util.SDK_INT <= 23) {*/
            outBundle.putLong(POSITION_KEY, mPosition);
            outBundle.putBoolean(IS_PLAYING_KEY, mPlaying);
        /*} else {
            outBundle.putLong(POSITION_KEY, mPlayer.getCurrentPosition());
            outBundle.putBoolean(IS_PLAYING_KEY, mPlayer.getPlayWhenReady());
        }*/
        Log.v(LOGTAG, "saveState " + outBundle.toString());
    }

    public void restoreState(Bundle bundle) {
        if (bundle != null) {
            mPosition = bundle.getLong(POSITION_KEY);
            mPlaying = bundle.getBoolean(IS_PLAYING_KEY);

            Log.v(LOGTAG, "restoreState " + bundle.toString());
        } else {
            mPosition = 0;
            mPlaying = true;
        }
    }
}
