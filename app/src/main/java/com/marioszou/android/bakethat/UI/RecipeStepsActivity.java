package com.marioszou.android.bakethat.UI;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
      assert stepsFragment != null;
      stepsFragment.setArguments(args);
    } else {
      finish();
    }
  }

//  private Bundle getIntentExtras() {
//    Bundle intentExtras = getIntent().getExtras();
//    if (intentExtras != null && intentExtras.get(RecipeStepsActivity.EXTRAS_RECIPE_ITEM) != null) {
//      Recipe recipe = intentExtras.getParcelable(RecipeStepsActivity.EXTRAS_RECIPE_ITEM);
//      assert recipe != null;
//      List<Ingredient> ingredientsList = recipe.getIngredients();
//      Timber.d("Ingredients: %s", Ingredients.formatAllIngredientsToString(ingredientsList));
//    } else {
//      Timber.e("Recipe from intent extras is null");
//    }
//    return intentExtras;
//  }

  /*
  This is where RecipeStepsFragment communicates with RecipeStepsActivity and gives this activity
  a way to communicate with StepDetailsFragment
   */
  @Override
  public void onRecipeStepClick(Uri uri) {
    //TODO Implement logic
  }
}
