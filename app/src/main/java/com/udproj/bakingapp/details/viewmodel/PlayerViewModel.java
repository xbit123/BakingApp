package com.udproj.bakingapp.details.viewmodel;

import android.arch.lifecycle.ViewModel;

//Holds state of the video player
public class PlayerViewModel extends ViewModel {
    private boolean mIsPlaying;
    private long mPosition;

    public boolean isPlaying() {
        return mIsPlaying;
    }

    public void setIsPlaying(boolean mIsPlaying) {
        this.mIsPlaying = mIsPlaying;
    }

    public long getPosition() {
        return mPosition;
    }

    public void setPosition(long mPosition) {
        this.mPosition = mPosition;
    }
}
