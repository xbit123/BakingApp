package com.udproj.bakingapp.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.udproj.bakingapp.R;
import com.udproj.bakingapp.details.activity.RecipeDetailsActivity;
import com.udproj.bakingapp.model.Cake;
import com.udproj.bakingapp.utils.Constants;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainCakesAdapter.CakesAdapterOnClickHandler {

    @BindView(R.id.rv_cakes)
    RecyclerView mRvMain;

    @BindView(R.id.btn_try_again)
    Button mBtnTryAgain;

    MainCakesAdapter mMainCakesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.col_count));
        mRvMain.setLayoutManager(layoutManager);
        mRvMain.setHasFixedSize(true);

        mMainCakesAdapter = new MainCakesAdapter(this);
        mRvMain.setAdapter(mMainCakesAdapter);

        loadCakes();
    }

    @Override
    public void onClick(Cake cake) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(Constants.INTENT_CAKE, Parcels.wrap(cake));
        startActivity(intent);
    }

    private void setViewsToErrorState() {
        mBtnTryAgain.setEnabled(true);
        mBtnTryAgain.setVisibility(View.VISIBLE);
        mRvMain.setVisibility(View.GONE);
    }

    private void setViewsToOkState() {
        mBtnTryAgain.setEnabled(true);
        mBtnTryAgain.setVisibility(View.GONE);
        mRvMain.setVisibility(View.VISIBLE);
    }

    private void setViewsToLoadingState() {
        mBtnTryAgain.setEnabled(false);
    }

    @OnClick(R.id.btn_try_again)
    public void tryAgain() {
        setViewsToLoadingState();
        loadCakes();
    }

    private void loadCakes() {
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getCakes().observe(this, new Observer<List<Cake>>() {
            @Override
            public void onChanged(@Nullable List<Cake> cakes) {
                if (cakes == null) {
                    setViewsToErrorState();
                } else {
                    setViewsToOkState();
                    mMainCakesAdapter.setCakesData(cakes);
                }
            }
        });
    }
}
