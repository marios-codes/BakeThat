package com.marioszou.android.bakethat.UI;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.marioszou.android.bakethat.R;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsFragment.OnRecipeStepsFragmentItemClickListener{

  public static final String EXTRAS_RECIPE_ITEM = "recipe-item";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_steps);

  }

  /*
  This is where RecipeStepsFragment communicates with RecipeStepsActivity and gives this activity
  a way to communicate with StepDetailsFragment
   */
  @Override
  public void onRecipeStepClick(Uri uri) {
    //TODO Implement logic
  }
}
