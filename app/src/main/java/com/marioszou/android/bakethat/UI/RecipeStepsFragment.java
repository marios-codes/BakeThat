package com.marioszou.android.bakethat.UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.marioszou.android.bakethat.Models.Ingredient;
import com.marioszou.android.bakethat.Models.Recipe;
import com.marioszou.android.bakethat.R;
import com.marioszou.android.bakethat.Utils.Ingredients;
import java.util.List;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link OnRecipeStepsFragmentItemClickListener} interface to handle interaction events.
 */
public class RecipeStepsFragment extends Fragment {

  private OnRecipeStepsFragmentItemClickListener mListener;

  @BindView(R.id.tv_ingredients)
  TextView ingredientsTextView;

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
      List<Ingredient> ingredientsList = recipe.getIngredients();
      Timber.d("Ingredients: %s", Ingredients.formatAllIngredientsToString(ingredientsList));
      ingredientsTextView.setText(Ingredients.formatAllIngredientsToString(ingredientsList));
    } else {
      Timber
          .e("RecipeStepsFragment Arguments Bundle does not contain key: RecipeStepsActivity.EXTRAS_RECIPE_ITEM");
    }
  }

  // TODO: Rename method, update argument and hook method into UI event
  public void onStepPressed(Uri uri) {
    if (mListener != null) {
      mListener.onRecipeStepClick(uri);
    }
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

  /**
   * This interface must be implemented by activities that contain this fragment to allow an
   * interaction in this fragment to be communicated to the activity and potentially other fragments
   * contained in that activity.
   * <p>
   * See the Android Training lesson <a href= "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnRecipeStepsFragmentItemClickListener {

    // TODO: Update argument type and name
    void onRecipeStepClick(Uri uri);
  }
}
