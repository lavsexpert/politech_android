package ru.mospolytech.firstwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;

public class Widget extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context){
        super.onEnabled(context);
        Log.d("first", "onEnabled()");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager manager, int[] ids){
        super.onUpdate(context, manager, ids);
        Log.e("first", "onUpdate()");
    }

    @Override
    public void onDeleted(Context context, int[] ids){
        super.onDeleted(context, ids);
        Log.i("first", "onDeleted()");
    }

    @Override
    public void onDisabled(Context context){
        super.onDisabled(context);
        Log.v("first", "onDisabled()");
    }
}
