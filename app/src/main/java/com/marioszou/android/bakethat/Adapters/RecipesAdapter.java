package com.marioszou.android.bakethat.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.marioszou.android.bakethat.Adapters.RecipesAdapter.RecipesAdapterViewHolder;
import com.marioszou.android.bakethat.Models.Recipe;
import com.marioszou.android.bakethat.R;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapterViewHolder> {


  /**
   * The interface that receives onClick messages.
   */
  public interface RecipesAdapterOnClickHandler {

    void onClick(Recipe recipe);
  }

  /*
   * An on-click handler to make it easy for an Activity to interface with the RecyclerView
   */
  private final RecipesAdapterOnClickHandler mClickHandler;
  private List<Recipe> mRecipeList = new ArrayList<>();

  /**
   * Creates a MoviesAdapter.
   *
   * @param clickHandler The on-click handler for this adapter. This single handler is called when
   * an item is clicked.
   */
  public RecipesAdapter(RecipesAdapterOnClickHandler clickHandler) {
    this.mClickHandler = clickHandler;
  }

  @NonNull
  @Override
  public RecipesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
    View view = inflater.inflate(R.layout.item_recipe_cardview, viewGroup, false);

    return new RecipesAdapterViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull RecipesAdapterViewHolder viewHolder, int position) {
    String recipeName = mRecipeList.get(position).getName();
    viewHolder.recipeNameTextView.setText(recipeName);

    int imageResourceId = 0;
    switch (recipeName) {
      case "Nutella Pie":
        imageResourceId = R.drawable.nutella_pie;
        break;
      case "Brownies":
        imageResourceId = R.drawable.brownies;
        break;
      case "Yellow Cake":
        imageResourceId = R.drawable.yellow_cake;
        break;
      case "Cheesecake":
        imageResourceId = R.drawable.cheesecake;
        break;
      default:
        Timber.e("No image resource found for given imageResourceId");
        break;
    }
    if (imageResourceId != 0) {
      viewHolder.recipeImageView.setImageResource(imageResourceId);
    }
  }

  @Override
  public int getItemCount() {
    if (null == mRecipeList) {
      return 0;
    }
    return mRecipeList.size();
  }

  public void setRecipeList(List<Recipe> recipeList) {
    mRecipeList = recipeList;
    notifyDataSetChanged();
  }

  public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

    @BindView(R.id.tv_card_recipe_name)
    TextView recipeNameTextView;
    @BindView(R.id.iv_card_recipe_photo)
    ImageView recipeImageView;

    public RecipesAdapterViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      Recipe recipe = mRecipeList.get(getAdapterPosition());
      mClickHandler.onClick(recipe);
    }
  }

}
