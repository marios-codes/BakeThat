package com.marioszou.android.bakethat.UI;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
  private static final String ARG_CHOSEN_STEP = "chosen-step";

  private List<Step> mStepsList;
  private Step mChosenStep;

  @BindView(R.id.tv_step_details_desc)
  TextView stepDescTV;


  public StepDetailsFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of this fragment using the provided
   * parameters.
   *
   * @param stepsList Recipe's entire steps list.
   * @param chosenStep The step that the user clicked to show its details.
   * @return A new instance of fragment StepDetailsFragment.
   */
  public static StepDetailsFragment newInstance(List<Step> stepsList, Step chosenStep) {
    StepDetailsFragment fragment = new StepDetailsFragment();
    Bundle args = new Bundle();
    args.putParcelableArrayList(ARG_STEPS_LIST, (ArrayList<? extends Parcelable>) stepsList);
    args.putParcelable(ARG_CHOSEN_STEP, chosenStep);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mStepsList = getArguments().getParcelableArrayList(ARG_STEPS_LIST);
      mChosenStep = getArguments().getParcelable(ARG_CHOSEN_STEP);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    //Fragment's layout
    View view = inflater.inflate(R.layout.fragment_step_details, container, false);
    ButterKnife.bind(this, view);

    initViews(mChosenStep);

    // Inflate the layout for this fragment
    return view;
  }

  private void initViews(Step mChosenStep) {
    stepDescTV.setText(mChosenStep.getDescription());
  }

}
