package com.udproj.bakingapp.details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.udproj.bakingapp.model.Cake;
import com.udproj.bakingapp.model.Step;

import java.util.List;

//Holds current Cake data and MutableLiveData mStepId for step navigation
public class DetailsSharedViewModel extends ViewModel {
    private MutableLiveData<Integer> mStepId = new MutableLiveData<>();
    private Cake mCake;

    public void setStepId(int stepId) {
        mStepId.setValue(stepId);
    }

    public void incStepId() {
        mStepId.setValue(mStepId.getValue() + 1);
    }

    public void decStepId() {
        mStepId.setValue(mStepId.getValue() - 1);
    }

    public void resetStepId() {
        mStepId = new MutableLiveData<>();
    }

    public LiveData<Integer> getStepId() {
        return mStepId;
    }

    public void setCake(Cake cake) {
        mCake = cake;
    }

    public Cake getCake() {
        return mCake;
    }

    public List<Step> getSteps() {
        return mCake.getSteps();
    }
}
