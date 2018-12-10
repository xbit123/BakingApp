package com.udproj.bakingapp.utils;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.widget.Toast;

import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.udproj.bakingapp.main.MainViewModel;
import com.udproj.bakingapp.model.Cake;
import com.udproj.bakingapp.model.Step;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class NetworkUtils {
    public static void loadCakes(final MutableLiveData<List<Cake>> cakes, final Application app) {
        //Register Idling Resource
        OkHttpClient client = new OkHttpClient();
        IdlingResource resource = OkHttp3IdlingResource.create("OkHttp", client);
        IdlingRegistry.getInstance().register(resource);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BakingApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        BakingApi api = retrofit.create(BakingApi.class);
        Call<List<Cake>> call = api.getCakes();

        call.enqueue(new Callback<List<Cake>>() {
            @Override
            public void onResponse(Call<List<Cake>> call, Response<List<Cake>> response) {
                //Sort steps in each cake by Id
                int cakeN = response.body().size();
                for (int i = 0; i < cakeN; i++) {
                    Collections.sort(response.body().get(i).getSteps(), new Comparator<Step>() {
                        @Override
                        public int compare(Step step, Step t1) {
                            return step.getId() - t1.getId();
                        }
                    });
                }
                cakes.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Cake>> call, Throwable t) {
                cakes.setValue(null);
                Toast.makeText(app, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
