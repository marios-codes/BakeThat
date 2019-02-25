package com.marioszou.android.bakethat.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marioszou.android.bakethat.Models.Ingredient;
import com.marioszou.android.bakethat.R;
import java.lang.reflect.Type;
import java.util.List;

public class SharePrefsUtils {

  private static final String INGREDIENTS_LIST_KEY = "ingredients_list_key";

    public static List<Ingredient> getRecipeIngredients(Context context) {
      SharedPreferences sharedPref = context.getSharedPreferences(
          context.getString(R.string.saved_ingredients_list), Context.MODE_PRIVATE);
      String ingredientsListJsonString = sharedPref.getString(INGREDIENTS_LIST_KEY, "");
      Gson gson = new Gson();
      Type type = new TypeToken<List<Ingredient>>(){}.getType();
      List<Ingredient> ingredientList = gson.fromJson(ingredientsListJsonString, type);

      return ingredientList;
    }

    public static void saveRecipeIngredients(Context context, List<Ingredient> ingredientList) {
      SharedPreferences sharedPref = context.getSharedPreferences(
          context.getString(R.string.saved_ingredients_list), Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sharedPref.edit();
      Gson gson = new Gson();
      String ingredientListJsonString = gson.toJson(ingredientList);
      editor.putString(INGREDIENTS_LIST_KEY, ingredientListJsonString);
      editor.apply();
    }

}
