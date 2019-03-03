package com.marioszou.android.bakethat.UI;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;
import com.marioszou.android.bakethat.Models.Recipe;
import com.marioszou.android.bakethat.Models.Step;
import com.marioszou.android.bakethat.R;
import com.marioszou.android.bakethat.Utils.SharePrefsUtils;
import com.marioszou.android.bakethat.Widget.IngredientsWidgetProvider;
import java.util.ArrayList;
import timber.log.Timber;

public class RecipeStepsActivity extends AppCompatActivity implements
    RecipeStepsFragment.OnRecipeStepsFragmentItemClickListener {

  public static final String EXTRAS_RECIPE_ITEM = "recipe-item";
  public static final String SAVED_STEP_ID = "saved-step-id";
  private Recipe mRecipe;
  private int mStepId = 0;

  // A single-pane display refers to phone screens, and two-pane to larger tablet screens
  private boolean mTwoPane;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_steps);

    //get the saved step id to show the appropriate step in orientation change
    if (savedInstanceState != null) {
      mStepId = savedInstanceState.getInt(SAVED_STEP_ID);
    }

    RecipeStepsFragment stepsFragment = (RecipeStepsFragment) getSupportFragmentManager()
        .findFragmentById(R.id.master_steps_fragment);
    Bundle args = getIntent().getExtras();
    //Pass the intent extras to the fragment using a bundle
    if (args != null) {
      //show Dessert Name in Toolbar
      mRecipe = args.getParcelable(EXTRAS_RECIPE_ITEM);
      assert mRecipe != null;
      setTitle(mRecipe.getName());

      assert stepsFragment != null;
      stepsFragment.setArguments(args);
    } else {
      finish();
    }

    //check if our device is sw600dp
    if (findViewById(R.id.linear_activity_steps) != null) {
      mTwoPane = true;

      // Only create new StepDetailsFragment when there is no previously saved state
      if(savedInstanceState == null) {
        //load the first step detail fragment if an orientation change hasn't taken place
        //or load the step that was chosen before the orientation change
        StepDetailsFragment detailsFragment = StepDetailsFragment
            .newInstance(mRecipe.getSteps(), mStepId, mTwoPane);

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.step_details_container, detailsFragment).commit();
      }
    } else {
      mTwoPane = false;
    }
  }

  /*
  This is where RecipeStepsFragment communicates with RecipeStepsActivity and gives this activity
  a way to communicate with StepDetailsActivity
   */
  @Override
  public void onRecipeStepClick(Step step) {
    mStepId = step.getId();
    if (mTwoPane) {
      StepDetailsFragment detailsFragment = StepDetailsFragment
          .newInstance(mRecipe.getSteps(), mStepId, mTwoPane);

      getSupportFragmentManager().beginTransaction()
          .replace(R.id.step_details_container, detailsFragment).commit();
    } else {
      Intent openStepDetailsIntent = new Intent(RecipeStepsActivity.this,
          StepDetailsActivity.class);
      openStepDetailsIntent.putParcelableArrayListExtra(StepDetailsActivity.EXTRA_STEPS_LIST,
          (ArrayList<? extends Parcelable>) mRecipe.getSteps());
      openStepDetailsIntent.putExtra(StepDetailsActivity.EXTRA_STEP_ID, mStepId);
      openStepDetailsIntent.putExtra(StepDetailsActivity.EXTRA_RECIPE_NAME, mRecipe.getName());
      openStepDetailsIntent.putExtra(StepDetailsActivity.EXTRA_IS_TWO_PANE, mTwoPane);
      startActivity(openStepDetailsIntent);
    }

  }
  /*
  Updates the widget with the latest opened recipe ingredients
   */
  private void updateWidget(Context context) {
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
    ComponentName ingredientsWidget = new ComponentName(context, IngredientsWidgetProvider.class);
    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(ingredientsWidget);
    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_list_view);
  }

  @Override
  protected void onStop() {
    super.onStop();
    //save selected recipe to shared prefs so that we can show its ingredients on our widget
    SharePrefsUtils.saveRecipeIngredients(RecipeStepsActivity.this, mRecipe.getIngredients());
    Timber.d("Recipe Ingredients Successfully saved to shared prefs");
    updateWidget(RecipeStepsActivity.this);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(SAVED_STEP_ID, mStepId);
  }
}
