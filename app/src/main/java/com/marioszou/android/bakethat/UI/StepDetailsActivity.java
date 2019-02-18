package com.marioszou.android.bakethat.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.marioszou.android.bakethat.Models.Step;
import com.marioszou.android.bakethat.R;
import java.util.List;

public class StepDetailsActivity extends AppCompatActivity {

  public static final String EXTRA_STEPS_LIST = "steps-list";
  public static final String EXTRA_STEP_ITEM = "step-item";
  public static final String EXTRA_RECIPE_NAME = "recipe-name";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_step_details);

    Bundle recipeAndStepsListBundle = getIntent().getExtras();

    //set toolbar text to recipe's name
    if (recipeAndStepsListBundle != null && recipeAndStepsListBundle.containsKey(EXTRA_RECIPE_NAME)) {
      setTitle(recipeAndStepsListBundle.getString(EXTRA_RECIPE_NAME));
    }

    //Pass the Steps List and the Step Item as StepDetailsFragment arguments to populate its views
    List<Step> stepsList = recipeAndStepsListBundle.getParcelableArrayList(EXTRA_STEPS_LIST);
    Step clickedStep = recipeAndStepsListBundle.getParcelable(EXTRA_STEP_ITEM);

//    if (stepsList != null && stepsList.contains(step)){
//      Timber.d("Position of step: %s with step description: %s", stepsList.indexOf(step), step.getDescription());
//    }

    StepDetailsFragment detailsFragment = StepDetailsFragment.newInstance(stepsList,clickedStep);

    getSupportFragmentManager().beginTransaction().add(R.id.step_details_container, detailsFragment).commit();
  }

  public void onPreviousStepClick(View view) {
    //TODO
    Toast.makeText(this, "Previous step clicked!", Toast.LENGTH_SHORT).show();
  }

  public void onNextStepClick(View view) {
    //TODO
    Toast.makeText(this, "Next step clicked!", Toast.LENGTH_SHORT).show();
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
}
