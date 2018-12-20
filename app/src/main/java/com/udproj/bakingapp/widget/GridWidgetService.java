package com.udproj.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext(), intent.getIntExtra("appWidgetId", 0));
    }
}

