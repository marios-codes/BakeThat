package com.marioszou.android.bakethat.Utils;

import com.marioszou.android.bakethat.Models.Ingredient;
import java.util.List;

public class IngredientsUtils {

  public static String formatAllIngredientsToString (List<Ingredient> ingredients) {
    StringBuilder formattedListToString = new StringBuilder();
    for (Ingredient ingredient : ingredients) {
      formattedListToString.append(getFormattedIngredientInfo(ingredient)).append("\n");
    }
    return formattedListToString.toString();
  }

  public static String getFormattedIngredientInfo(Ingredient ingredient) {

    return "\u2022" + " " + ingredient.getIngredient() + " " + "(" + ingredient.getQuantity() + " "
        + ingredient.getMeasure() + ")";
  }

}
