package com.udproj.bakingapp.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.udproj.bakingapp.R;
import com.udproj.bakingapp.model.Cake;

import java.util.List;

public class MainCakesAdapter extends RecyclerView.Adapter<MainCakesAdapter.CakesAdapterViewHolder> {
    private List<Cake> mCakes;

    private final CakesAdapterOnClickHandler mClickHandler;
    private Context mContext;

    public interface CakesAdapterOnClickHandler {
        void onClick(Cake cake);
    }

    public MainCakesAdapter(Context context) {
        mClickHandler = (CakesAdapterOnClickHandler) context;
        mContext = context;
    }

    public class CakesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mCakeImageView;

        public CakesAdapterViewHolder(View view) {
            super(view);
            mCakeImageView = view.findViewById(R.id.iv_cake_rv);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Cake cake = mCakes.get(adapterPosition);
            mClickHandler.onClick(cake);
        }
    }

    @Override
    public CakesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        int layoutIdForListItem = R.layout.cake_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new CakesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CakesAdapterViewHolder cakesAdapterViewHolder, int position) {
        int id = mCakes.get(position).getImageId();
        ImageView iv = cakesAdapterViewHolder.mCakeImageView;
        Glide.with(mContext).load(id).into(iv);
    }

    @Override
    public int getItemCount() {
        if (mCakes == null) {
            return 0;
        } else {
            return mCakes.size();
        }
    }

    public void setCakesData(List<Cake> cakes) {
        mCakes = cakes;
        notifyDataSetChanged();
    }
}
