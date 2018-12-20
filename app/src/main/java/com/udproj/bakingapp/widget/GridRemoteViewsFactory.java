package com.udproj.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udproj.bakingapp.R;
import com.udproj.bakingapp.model.Ingredient;

import java.util.List;

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private List<Ingredient> mIngredients;
    private int mAppWidgetId;

    public GridRemoteViewsFactory(Context context, int appWidgetId) {
        this.mContext = context;
        this.mAppWidgetId = appWidgetId;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mIngredients = IngredientsWidgetConfigureActivity.loadTitlePref(mContext, mAppWidgetId);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredients == null ? 0 : mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredient_list_item);
        views.setTextViewText(R.id.widget_ingredient_text, mIngredients.get(position).toString(mContext));

        views.setOnClickFillInIntent(R.id.widget_fl_list_item, new Intent());

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
