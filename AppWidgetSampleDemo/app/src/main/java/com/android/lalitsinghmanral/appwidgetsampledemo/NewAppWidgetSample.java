package com.android.lalitsinghmanral.appwidgetsampledemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidgetSample extends AppWidgetProvider {

    private static final String mSharedFile = "com.android.lalitsinghmanral.appwidgetsampledemo";
    private static final String COUNT_KEY = "count";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        SharedPreferences prefs = context.getSharedPreferences(mSharedFile,Context.MODE_PRIVATE);
        int count = prefs.getInt(COUNT_KEY + appWidgetId,0);
        count++;

        //Adding Pending Intent call for immediate update
        Intent i1 = new Intent(context, NewAppWidgetSample.class);
        i1.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int idArray[] = new int[]{appWidgetId};
        i1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

        PendingIntent pd = PendingIntent.getBroadcast(context,appWidgetId,i1,
                PendingIntent.FLAG_UPDATE_CURRENT);

        String dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.new_app_widget_sample);
        views.setTextViewText(R.id.app_widget_id, String.valueOf(appWidgetId) );
        views.setTextViewText(R.id.app_widget_update,
                context.getResources().getString(R.string.count_date_format,
                        count, dateString));
        views.setOnClickPendingIntent(R.id.button_update, pd);

        SharedPreferences.Editor mEdit = prefs.edit();
        mEdit.putInt(COUNT_KEY + appWidgetId, count);
        mEdit.apply();
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }



}

