package com.marioszou.android.bakethat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.marioszou.android.bakethat.Adapters.RecipesAdapter;
import com.marioszou.android.bakethat.Adapters.RecipesAdapter.RecipesAdapterOnClickHandler;
import com.marioszou.android.bakethat.Models.Recipe;
import com.marioszou.android.bakethat.Network.RecipesNetworkService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class SelectRecipeActivity extends AppCompatActivity implements
    RecipesAdapterOnClickHandler {

  @BindView(R.id.recyclerview_recipes)
  RecyclerView mRecyclerView;
  @BindView(R.id.pb_loading_indicator)
  ProgressBar mLoadingIndicator;

  private RecipesAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select_recipe);

    ButterKnife.bind(this);

    initViews();
    fetchRecipesFromApi();
  }

  private void initViews() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(SelectRecipeActivity.this);
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setHasFixedSize(true);
    mAdapter = new RecipesAdapter(this);
    mRecyclerView.setAdapter(mAdapter);
  }

  private void fetchRecipesFromApi() {
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
        //hide loading indicator
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (response.isSuccessful()) {
          List<Recipe> recipesList = response.body();
          Timber.d("Number of objects in Recipe List: %s", recipesList.size());
          mAdapter.setRecipeList(recipesList);
        }
      }

      @Override
      public void onFailure(Call<List<Recipe>> call, Throwable t) {
        //hide loading indicator
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        Timber.e(t);
      }
    });
  }

  /**
   * This method is overridden by our SelectRecipe class in order to handle RecyclerView item
   * clicks.
   *
   * @param recipe The recipe that was clicked
   */
  @Override
  public void onClick(Recipe recipe) {

  }
}
