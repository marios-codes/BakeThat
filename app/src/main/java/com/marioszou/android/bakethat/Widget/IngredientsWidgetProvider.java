package com.marioszou.android.bakethat.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.marioszou.android.bakethat.R;
import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

  public final static String EXTRA_APP_WIDGET_INGREDIENTS_LIST = "widget-ingredients-list";

  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
      int appWidgetId) {

    Timber.d("Update app widget just got called");

    // Set up the intent that starts the StackViewService, which will
    // provide the views for this collection.
    Intent listWidgetServiceIntent = new Intent(context, ListWidgetService.class);


    // Instantiate the RemoteViews object for the app widget layout.
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

    // The empty view is displayed when the collection has no items.
    // It should be in the same layout used to instantiate the RemoteViews
    // object above.
    views.setEmptyView(R.id.appwidget_list_view, R.id.widget_list_empty_view);

    // Set up the RemoteViews object to use a RemoteViews adapter.
    // This adapter connects
    // to a RemoteViewsService  through the specified intent.
    // This is how we populate the data.
    views.setRemoteAdapter(R.id.appwidget_list_view, listWidgetServiceIntent);

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

  @Override
  public void onEnabled(Context context) {
    // Enter relevant functionality for when the first widget is created
  }

  @Override
  public void onDisabled(Context context) {
    // Enter relevant functionality for when the last widget is disabled
  }
}

