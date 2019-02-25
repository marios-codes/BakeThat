package com.marioszou.android.bakethat.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.marioszou.android.bakethat.Models.Ingredient;
import com.marioszou.android.bakethat.R;
import com.marioszou.android.bakethat.Utils.IngredientsUtils;
import com.marioszou.android.bakethat.Utils.SharePrefsUtils;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class ListWidgetService extends RemoteViewsService {

  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
  }

  class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Ingredient> mIngredientsList = new ArrayList<>();
    private Context mContext;

    public ListRemoteViewsFactory(Context context, Intent intent) {
      mContext = context;
    }

    @Override
    public void onCreate() {

    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
      //Get ingredients from shared preferences, this list will be null
      //if the user places the widget before opening a recipe inside the app
      List<Ingredient> ingredientList = SharePrefsUtils.getRecipeIngredients(mContext);
      Timber.d("Ingedient List on List Widget's onDataSetChanged() state: %s", ingredientList);
      if (ingredientList != null) {
        mIngredientsList = ingredientList;
      }
    }

    @Override
    public void onDestroy() {
      // In onDestroy() you should tear down anything that was setup for your data source,
      // eg. cursors, connections, etc.
      mIngredientsList.clear();
    }

    @Override
    public int getCount() {
      return mIngredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = mIngredientsList.get(position);

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
            R.layout.widget_ingredient_list_item);
        remoteViews.setTextViewText(R.id.widget_list_item_ingredient_tv,
            IngredientsUtils.getFormattedIngredientInfo(ingredient));

      return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
      return null;
    }

    @Override
    public int getViewTypeCount() {
      return 1; // Treat all items in the GridView the same;
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public boolean hasStableIds() {
      return true;
    }
  }
}
