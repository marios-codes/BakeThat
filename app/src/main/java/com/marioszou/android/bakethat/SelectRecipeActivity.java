package com.marioszou.android.bakethat;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
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

  private static final String SAVED_LIST_POSITION_KEY = "list-position";
  private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
  private static final int GRID_SPAN_COUNT = 3;

  @BindView(R.id.recyclerview_recipes)
  RecyclerView mRecyclerView;
  @BindView(R.id.pb_loading_indicator)
  ProgressBar mLoadingIndicator;

  private RecipesAdapter mAdapter;
  private Bundle mSavedInstanceState;
  private LayoutManager mLayoutManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select_recipe);

    ButterKnife.bind(this);

    mSavedInstanceState = savedInstanceState;
    initViews();
    fetchRecipesFromApi();
  }

  private void initViews() {
    //check if layout is sw600dp so that we can set the appropriate layout manager
    //for our recyclerview
    boolean is600dp = getResources().getBoolean(R.bool.is600dp);
    if (is600dp) {
      mLayoutManager = new GridLayoutManager(this, GRID_SPAN_COUNT);
    } else {
      mLayoutManager = new LinearLayoutManager(SelectRecipeActivity.this);
    }
    mRecyclerView.setLayoutManager(mLayoutManager);
    mRecyclerView.setHasFixedSize(true);
    mAdapter = new RecipesAdapter(this);
    mRecyclerView.setAdapter(mAdapter);
  }

  private void fetchRecipesFromApi() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
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
          scrollListToSavedPosition();
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

  private void scrollListToSavedPosition() {
    if (mSavedInstanceState != null && mSavedInstanceState
        .containsKey(SAVED_LIST_POSITION_KEY)) {
      Parcelable savedRecyclerLayoutState = mSavedInstanceState
          .getParcelable(SAVED_LIST_POSITION_KEY);
      mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
    } else {
      mRecyclerView.scrollToPosition(0);
    }
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

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    /* save the scroll position of the list and the preferred sorting type
    in order to retain it on a configuration change
    Also check for null, otherwise app will crash on orientation change with no internet connection
    */
    if (mRecyclerView != null && mRecyclerView.getLayoutManager() != null) {
      outState.putParcelable(SAVED_LIST_POSITION_KEY,
          mRecyclerView.getLayoutManager().onSaveInstanceState());
    }
  }
}
