package com.marioszou.android.bakethat.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.marioszou.android.bakethat.Adapters.StepsAdapter;
import com.marioszou.android.bakethat.Adapters.StepsAdapter.StepsAdapterOnClickHandler;
import com.marioszou.android.bakethat.Models.Ingredient;
import com.marioszou.android.bakethat.Models.Recipe;
import com.marioszou.android.bakethat.Models.Step;
import com.marioszou.android.bakethat.R;
import com.marioszou.android.bakethat.Utils.Ingredients;
import java.util.List;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link OnRecipeStepsFragmentItemClickListener} interface to handle interaction events.
 */
public class RecipeStepsFragment extends Fragment implements StepsAdapterOnClickHandler {

  private OnRecipeStepsFragmentItemClickListener mListener;

  private StepsAdapter mAdapter;

  @BindView(R.id.tv_ingredients)
  TextView ingredientsTextView;
  @BindView(R.id.rv_recipe_steps)
  RecyclerView mRecyclerView;

  public RecipeStepsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
    //View binding
    ButterKnife.bind(this, view);

    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    Bundle fragmentArgs = getArguments();
    assert fragmentArgs != null;
    if (fragmentArgs.get(RecipeStepsActivity.EXTRAS_RECIPE_ITEM) != null) {
      Recipe recipe = fragmentArgs.getParcelable(RecipeStepsActivity.EXTRAS_RECIPE_ITEM);
      assert recipe != null;
      setupViews(recipe);
    } else {
      Timber
          .e("RecipeStepsFragment Arguments Bundle does not contain key: RecipeStepsActivity.EXTRAS_RECIPE_ITEM");
    }
  }

  private void setupViews(Recipe recipe) {
    //show recipe's ingredients
    List<Ingredient> ingredientsList = recipe.getIngredients();
    Timber.d("Ingredients: %s", Ingredients.formatAllIngredientsToString(ingredientsList));
    ingredientsTextView.setText(Ingredients.formatAllIngredientsToString(ingredientsList));

    //setup and show recipe's steps in a recycler view
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setHasFixedSize(true);
    mAdapter = new StepsAdapter(this);
    mRecyclerView.setAdapter(mAdapter);
    //for smooth scrolling
    ViewCompat.setNestedScrollingEnabled(mRecyclerView, false);

    List<Step> stepsList = recipe.getSteps();
    mAdapter.setStepsList(stepsList);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnRecipeStepsFragmentItemClickListener) {
      mListener = (OnRecipeStepsFragmentItemClickListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnRecipeStepsFragmentItemClickListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /*
  Callback from StepsAdapter triggered when a Recipe step is clicked
   */
  @Override
  public void onStepClick(Step step) {
    Timber.d(step.getDescription());
    //Pass the Step that was clicked to the hosting activity (RecipeStepsActivity)
    if (mListener != null) {
      mListener.onRecipeStepClick(step);
    }
  }

  /**
   * This interface must be implemented by activities that contain this fragment to allow an
   * interaction in this fragment to be communicated to the activity and potentially other fragments
   * contained in that activity.
   * <p>
   * See the Android Training lesson <a href= "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnRecipeStepsFragmentItemClickListener {

    void onRecipeStepClick(Step step);
  }
}
