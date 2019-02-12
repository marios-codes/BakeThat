package com.marioszou.android.bakethat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.marioszou.android.bakethat.Models.Recipe;
import com.marioszou.android.bakethat.Network.RecipesNetworkService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class SelectRecipeActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select_recipe);

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://d17h27t6h515a5.cloudfront.net")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    RecipesNetworkService recipesService = retrofit.create(RecipesNetworkService.class);

    Call<List<Recipe>> recipesRequest = recipesService.getRecipes();

    recipesRequest.enqueue(new Callback<List<Recipe>>() {
      @Override
      public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        Timber.d("Retrofit Response is successful: %s", response.isSuccessful());
        if (response.isSuccessful()) {
          List<Recipe> recipesList = response.body();
          Timber.d("Number of objects in Recipe List: %s", recipesList.size());
        }
      }

      @Override
      public void onFailure(Call<List<Recipe>> call, Throwable t) {
        Timber.e(t);
      }
    });
  }
}
