package com.udproj.bakingapp.details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udproj.bakingapp.R;
import com.udproj.bakingapp.model.Step;

import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.StepsAdapterViewHolder> {
    private List<Step> mSteps;

    DetailsSharedViewModel mViewModel;

    public RecipeStepsAdapter(DetailsSharedViewModel viewModel) {
        mViewModel = viewModel;
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTvStep;

        public StepsAdapterViewHolder(View view) {
            super(view);
            mTvStep = view.findViewById(R.id.tv_recipe_step_description);
            view.setOnClickListener(this);
        }

        //Change stepId in ViewModel. All observers will be notified about the click.
        @Override
        public void onClick(View view) {
            mViewModel.setStepId(getAdapterPosition());
        }
    }

    @Override
    public StepsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        int layoutIdForListItem = R.layout.recipe_step_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsAdapterViewHolder stepsAdapterViewHolder, int position) {
        stepsAdapterViewHolder.mTvStep.setText(mSteps.get(position).toString());
    }

    @Override
    public int getItemCount() {
        if (mSteps == null) {
            return 0;
        } else {
            return mSteps.size();
        }
    }

    public void setStepsData(List<Step> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }
}
