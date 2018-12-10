package com.udproj.bakingapp.details.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udproj.bakingapp.R;
import com.udproj.bakingapp.details.viewmodel.DetailsSharedViewModel;
import com.udproj.bakingapp.details.RecipeStepsAdapter;
import com.udproj.bakingapp.model.Cake;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment {

    @BindView(R.id.rv_steps)
    RecyclerView mRvSteps;

    @BindView(R.id.tv_recipe_ingredients)
    TextView mTvIngredients;

    private RecipeStepsAdapter mRecipeStepsAdapter;
    private DetailsSharedViewModel mViewModel;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, view);

        mViewModel = ViewModelProviders.of(getActivity()).get(DetailsSharedViewModel.class);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRvSteps.setLayoutManager(layoutManager);
        mRvSteps.setHasFixedSize(true);
        mRecipeStepsAdapter = new RecipeStepsAdapter(mViewModel);
        mRvSteps.setAdapter(mRecipeStepsAdapter);

        Cake cake = mViewModel.getCake();
        mTvIngredients.setText(cake.ingredientsToString(getContext()));

        mRecipeStepsAdapter.setStepsData(cake.getSteps());
    }
}
