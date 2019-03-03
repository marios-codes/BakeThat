package com.marioszou.android.bakethat.UI;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.test.espresso.IdlingResource;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.marioszou.android.bakethat.Adapters.RecipesAdapter;
import com.marioszou.android.bakethat.Adapters.RecipesAdapter.RecipesAdapterOnClickHandler;
import com.marioszou.android.bakethat.IdlingResource.SimpleIdlingResource;
import com.marioszou.android.bakethat.Models.Recipe;
import com.marioszou.android.bakethat.Network.RecipesNetworkService;
import com.marioszou.android.bakethat.R;
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

  // The Idling Resource which will be null in production.
  @Nullable
  private SimpleIdlingResource mIdlingResource;

  /**
   * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
   */
  @VisibleForTesting
  @NonNull
  public IdlingResource getIdlingResource() {
    if (mIdlingResource == null) {
      mIdlingResource = new SimpleIdlingResource();
    }
    return mIdlingResource;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select_recipe);

    ButterKnife.bind(this);

    // Get the IdlingResource instance
    getIdlingResource();

    mSavedInstanceState = savedInstanceState;
    initViews();
    fetchRecipesFromApi();
  }

  private void initViews() {
    //check if layout is sw600dp so that we can set the appropriate layout manager
    //for our recyclerview
    boolean is600dp = getResources().getBoolean(R.bool.is600dp);
    LayoutManager layoutManager;
    if (is600dp) {
      layoutManager = new GridLayoutManager(this, GRID_SPAN_COUNT);
    } else {
      layoutManager = new LinearLayoutManager(SelectRecipeActivity.this);
    }
    mRecyclerView.setLayoutManager(layoutManager);
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

    /**
     * The IdlingResource is null in production as set by the @Nullable annotation which means
     * the value is allowed to be null.
     *
     * If the idle state is true, Espresso can perform the next action.
     * If the idle state is false, Espresso will wait until it is true before
     * performing the next action.
     */
    if (mIdlingResource != null) {
      mIdlingResource.setIdleState(false);
      Timber.d("mIdlingResource state before retrofit call: %s", mIdlingResource.isIdleNow());
    }

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
          //Allow Espresso to perform the next action in Test
          if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
            Timber.d("mIdlingResource state after retrofit call: %s", mIdlingResource.isIdleNow());
          }
        }
      }

      @Override
      public void onFailure(Call<List<Recipe>> call, Throwable t) {
        //hide loading indicator
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        Timber.e(t);
        mIdlingResource.setIdleState(true);
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
    Intent openRecipeStepsIntent = new Intent(SelectRecipeActivity.this, RecipeStepsActivity.class);
    openRecipeStepsIntent.putExtra(RecipeStepsActivity.EXTRAS_RECIPE_ITEM, recipe);
    startActivity(openRecipeStepsIntent);
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
