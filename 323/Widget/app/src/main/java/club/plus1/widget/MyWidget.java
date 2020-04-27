package club.plus1.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;

public class MyWidget extends AppWidgetProvider {

    @Override
    public void onEnabled(Context context){
        super.onEnabled(context);
        Log.d("mywi", "onEnabled");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.e("mywi", "onUpdate");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds){
        super.onDeleted(context, appWidgetIds);
        Log.i("mywi", "onDeleted");
    }

    @Override
    public void onDisabled(Context context){
        super.onDisabled(context);
        Log.v("mywi", "onDisabled");
    }
}
