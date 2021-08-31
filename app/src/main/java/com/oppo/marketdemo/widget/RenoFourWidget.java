package com.oppo.marketdemo.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.oppo.marketdemo.R;
import com.oppo.marketdemo.SubPageActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Implementation of App Widget functionality.
 */
public class RenoFourWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.reno_four_widget);

        Intent intent1 = new Intent(context, SubPageActivity.class);
        intent1.putExtra(SubPageActivity.VIEW_PAGER_POSITION, 0);
        intent1.setFlags(FLAG_ACTIVITY_NEW_TASK);
        views.setOnClickPendingIntent(R.id.iv_widget1, PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT));

        Intent intent2 = new Intent(context, SubPageActivity.class);
        intent2.putExtra(SubPageActivity.VIEW_PAGER_POSITION, 1);
        intent2.setFlags(FLAG_ACTIVITY_NEW_TASK);
        views.setOnClickPendingIntent(R.id.iv_widget2, PendingIntent.getActivity(context, 1, intent2, PendingIntent.FLAG_CANCEL_CURRENT));

        Intent intent3 = new Intent(context, SubPageActivity.class);
        intent3.putExtra(SubPageActivity.VIEW_PAGER_POSITION, 2);
        intent3.setFlags(FLAG_ACTIVITY_NEW_TASK);
        views.setOnClickPendingIntent(R.id.iv_widget3, PendingIntent.getActivity(context, 2, intent3, PendingIntent.FLAG_CANCEL_CURRENT));

        Intent intent4 = new Intent(context, SubPageActivity.class);
        intent4.putExtra(SubPageActivity.VIEW_PAGER_POSITION, 3);
        intent4.setFlags(FLAG_ACTIVITY_NEW_TASK);
        views.setOnClickPendingIntent(R.id.iv_widget4, PendingIntent.getActivity(context, 3, intent4, PendingIntent.FLAG_CANCEL_CURRENT));

        Intent intent5 = new Intent(context, SubPageActivity.class);
        intent5.putExtra(SubPageActivity.VIEW_PAGER_POSITION, 4);
        intent5.setFlags(FLAG_ACTIVITY_NEW_TASK);
        views.setOnClickPendingIntent(R.id.iv_widget5, PendingIntent.getActivity(context, 4, intent5, PendingIntent.FLAG_CANCEL_CURRENT));

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

