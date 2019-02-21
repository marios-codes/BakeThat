package com.marioszou.android.bakethat.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import com.marioszou.android.bakethat.Models.Step;
import com.marioszou.android.bakethat.R;
import java.util.ArrayList;
import java.util.List;

public class StepDetailsActivity extends AppCompatActivity {

  public static final String EXTRA_STEPS_LIST = "steps-list";
  public static final String EXTRA_STEP_ID = "step-id";
  public static final String EXTRA_RECIPE_NAME = "recipe-name";
  public static final String EXTRA_IS_TWO_PANE = "is-two-pane";

  private int mCurrentStepId = -1;
  private List<Step> mStepsList = new ArrayList<>();
  private boolean mIsTwoPane;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_step_details);

    Bundle recipeAndStepsListBundle = getIntent().getExtras();

    //set toolbar text to recipe's name
    if (recipeAndStepsListBundle != null && recipeAndStepsListBundle
        .containsKey(EXTRA_RECIPE_NAME)) {
      setTitle(recipeAndStepsListBundle.getString(EXTRA_RECIPE_NAME));
    }

    //Pass the Steps List and the Step Item as StepDetailsFragment arguments to populate its views
    mStepsList = recipeAndStepsListBundle.getParcelableArrayList(EXTRA_STEPS_LIST);
    mCurrentStepId = recipeAndStepsListBundle.getInt(EXTRA_STEP_ID);
    mIsTwoPane = recipeAndStepsListBundle.getBoolean(EXTRA_IS_TWO_PANE);

    // Only create new fragments when there is no previously saved state
    if (savedInstanceState == null) {
      StepDetailsFragment detailsFragment = StepDetailsFragment.newInstance(mStepsList, mCurrentStepId, mIsTwoPane);

      getSupportFragmentManager().beginTransaction()
          .add(R.id.step_details_container, detailsFragment).commit();
    } else {
      mCurrentStepId = savedInstanceState.getInt(EXTRA_STEP_ID);
    }
  }

  public void onPreviousStepClick(View view) {
    StepDetailsFragment newDetailsFragment = StepDetailsFragment
        .newInstance(mStepsList, mCurrentStepId - 1, mIsTwoPane);
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.step_details_container, newDetailsFragment).commit();
    mCurrentStepId -= 1;
  }

  public void onNextStepClick(View view) {
    StepDetailsFragment newDetailsFragment = StepDetailsFragment
        .newInstance(mStepsList, mCurrentStepId + 1, mIsTwoPane);
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.step_details_container, newDetailsFragment).commit();
    mCurrentStepId += 1;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(EXTRA_STEP_ID, mCurrentStepId);
  }
}
