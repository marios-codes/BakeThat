package com.marioszou.android.bakethat.UI;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.marioszou.android.bakethat.Models.Recipe;
import com.marioszou.android.bakethat.R;

public class RecipeStepsActivity extends AppCompatActivity implements
    RecipeStepsFragment.OnRecipeStepsFragmentItemClickListener {

  public static final String EXTRAS_RECIPE_ITEM = "recipe-item";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_steps);

    RecipeStepsFragment stepsFragment = (RecipeStepsFragment) getSupportFragmentManager()
        .findFragmentById(R.id.master_steps_fragment);
    Bundle args = getIntent().getExtras();
    //Pass the intent extras to the fragment using a bundle
    if (args != null) {
      //show Dessert Name in Toolbar
      Recipe recipe = args.getParcelable(EXTRAS_RECIPE_ITEM);
      assert recipe != null;
      setTitle(recipe.getName());

      assert stepsFragment != null;
      stepsFragment.setArguments(args);
    } else {
      finish();
    }
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
