package com.marioszou.android.bakethat.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.marioszou.android.bakethat.Models.Ingredient;
import com.marioszou.android.bakethat.Models.Recipe;
import com.marioszou.android.bakethat.R;
import java.util.List;
import timber.log.Timber;

public class RecipeStepsActivity extends AppCompatActivity {

  public static final String EXTRAS_RECIPE_ITEM = "recipe-item";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_steps);

    Bundle intentExtras = getIntent().getExtras();
    if (intentExtras != null && intentExtras.get(EXTRAS_RECIPE_ITEM) != null) {
      Recipe recipe = intentExtras.getParcelable(EXTRAS_RECIPE_ITEM);
      assert recipe != null;
      List<Ingredient> ingredients = recipe.getIngredients();
      Timber.d("Ingredients: %s", ingredients);
    } else {
      Timber.e("Recipe from intent extras is null");
      finish();
    }
  }
}
