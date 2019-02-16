package com.marioszou.android.bakethat.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.marioszou.android.bakethat.R;

public class StepDetailsActivity extends AppCompatActivity {

  public static final String EXTRA_STEP_ITEM = "step-item";
  public static final String EXTRA_RECIPE_NAME = "recipe-name";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_step_details);

    Bundle extras = getIntent().getExtras();

    //set toolbar text to recipe's name
    if (extras != null && extras.containsKey(EXTRA_RECIPE_NAME)) {
      setTitle(extras.getString(EXTRA_RECIPE_NAME));
    }

    StepDetailsFragment detailsFragment = StepDetailsFragment.newInstance("","");

    getSupportFragmentManager().beginTransaction().add(R.id.step_details_container, detailsFragment).commit();
  }
}
