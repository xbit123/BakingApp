package com.udproj.bakingapp.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.udproj.bakingapp.model.Cake;
import com.udproj.bakingapp.utils.NetworkUtils;

import java.util.List;

//Holds Cakes data
public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<List<Cake>> cakes = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Cake>> getCakes() {
        if (cakes.getValue() == null) {
            NetworkUtils.loadCakes(cakes, getApplication());
        }
        return cakes;
    }
}
