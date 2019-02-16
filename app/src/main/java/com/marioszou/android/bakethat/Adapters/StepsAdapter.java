package com.marioszou.android.bakethat.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.marioszou.android.bakethat.Adapters.StepsAdapter.StepsAdapterViewHolder;
import com.marioszou.android.bakethat.Models.Step;
import com.marioszou.android.bakethat.R;
import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapterViewHolder> {

  /**
   * The interface that receives onClick messages.
   */
  public interface StepsAdapterOnClickHandler {

    void onStepClick(Step step);

  }

  private final StepsAdapterOnClickHandler mClickHandler;
  private List<Step> mStepsList = new ArrayList<>();

  public StepsAdapter(StepsAdapterOnClickHandler clickHandler) {

    this.mClickHandler = clickHandler;
  }


  @NonNull
  @Override
  public StepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
    View view = inflater.inflate(R.layout.item_step_cardview, viewGroup, false);

    return new StepsAdapterViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull StepsAdapterViewHolder stepsAdapterViewHolder, int i) {
    String recipeShortStepDesc = mStepsList.get(i).getShortDescription();
    stepsAdapterViewHolder.shortDescTV.setText(recipeShortStepDesc);
  }


  @Override
  public int getItemCount() {
    if (null == mStepsList) {
      return 0;
    }
    return mStepsList.size();
  }

  public void setStepsList(List<Step> stepsList) {
    mStepsList = stepsList;
    notifyDataSetChanged();
  }

  public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

    @BindView(R.id.tv_card_step_short_desc)
    TextView shortDescTV;

    public StepsAdapterViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      Step step = mStepsList.get(getAdapterPosition());
      mClickHandler.onStepClick(step);
    }
  }
}
