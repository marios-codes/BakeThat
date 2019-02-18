package com.marioszou.android.bakethat.UI;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.marioszou.android.bakethat.Models.Step;
import com.marioszou.android.bakethat.R;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass. Use the {@link StepDetailsFragment#newInstance} factory
 * method to create an instance of this fragment.
 */
public class StepDetailsFragment extends Fragment {

  // the fragment initialization parameters
  private static final String ARG_STEPS_LIST = "steps-list";
  private static final String ARG_CHOSEN_STEP_ID = "chosen-step-id";

  private List<Step> mStepsList;
  private int mChosenStepId;

  @BindView(R.id.tv_step_details_desc)
  TextView stepDescTV;
  @BindView(R.id.iv_step_details_previous)
  Button previousStepBtn;
  @BindView(R.id.iv_step_details_next)
  Button nextStepBtn;


  public StepDetailsFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param stepsList Recipe's entire steps list.
   * @param chosenStepId The step's id which is zero-based incrementation.
   * @return A new instance of fragment StepDetailsFragment.
   */
  public static StepDetailsFragment newInstance(List<Step> stepsList, int chosenStepId) {
    StepDetailsFragment fragment = new StepDetailsFragment();
    Bundle args = new Bundle();
    args.putParcelableArrayList(ARG_STEPS_LIST, (ArrayList<? extends Parcelable>) stepsList);
    args.putInt(ARG_CHOSEN_STEP_ID, chosenStepId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mStepsList = getArguments().getParcelableArrayList(ARG_STEPS_LIST);
      mChosenStepId = getArguments().getInt(ARG_CHOSEN_STEP_ID);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    //Fragment's layout
    View view = inflater.inflate(R.layout.fragment_step_details, container, false);
    ButterKnife.bind(this, view);

    initViews(mChosenStepId);

    // Inflate the layout for this fragment
    return view;
  }

  private void initViews(int mChosenStepId) {
    //get Step from step ID
    Step chosenStep = mStepsList.get(mChosenStepId);
    //Hide previous step button if the user navigates to the first step
    if (mChosenStepId == 0) {
      previousStepBtn.setVisibility(View.INVISIBLE);
    }
    //Hide next step button if the user navigates to the last step
    if (mChosenStepId == mStepsList.size() - 1) {
      nextStepBtn.setVisibility(View.INVISIBLE);
    }
    stepDescTV.setText(chosenStep.getDescription());
  }

}
