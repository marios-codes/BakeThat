package com.marioszou.android.bakethat.UI;


import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource.Factory;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
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
  private static final String SAVED_PLAYER_POSITION = "saved-player-position";

  private List<Step> mStepsList;
  private int mChosenStepId;
  private SimpleExoPlayer mPlayer;

  @BindView(R.id.tv_step_details_desc)
  TextView stepDescTV;
  @BindView(R.id.iv_step_details_previous)
  Button previousStepBtn;
  @BindView(R.id.iv_step_details_next)
  Button nextStepBtn;
  @BindView(R.id.exoPlayerView)
  PlayerView mPlayerView;


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

    //continue video playback to the point it was in an orientation change
    long savedVideoPosition = 0;
    if (savedInstanceState != null && savedInstanceState.containsKey(SAVED_PLAYER_POSITION)) {
      savedVideoPosition = savedInstanceState.getLong(SAVED_PLAYER_POSITION);
    }

    //get Step from step ID
    Step chosenStep = mStepsList.get(mChosenStepId);

    initViews(chosenStep);
    //check if step has video instructions to initialize the exo player
    if (!chosenStep.getVideoURL().isEmpty()){
      initPlayer(chosenStep.getVideoURL(), savedVideoPosition);
    }

    //το παραθυρο με το βινδεο εχει ασπεκτ ρεισιο να πουμε, δεν μου αρεσει!


    // Inflate the layout for this fragment
    return view;
  }

  private void initViews(Step step) {
    //get Step from step ID
    //Hide previous step button if the user navigates to the first step
    if (step.getId() == 0) {
      previousStepBtn.setVisibility(View.INVISIBLE);
    }
    //Hide next step button if the user navigates to the last step
    if (step.getId() == mStepsList.size() - 1) {
      nextStepBtn.setVisibility(View.INVISIBLE);
    }
    stepDescTV.setText(step.getDescription());
  }

  private void initPlayer(String videoURL, long savedPositionTimestamp) {
    mPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
    // Bind the player to the view.
    mPlayerView.setPlayer(mPlayer);
    // Produces DataSource instances through which media data is loaded.
    Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
        Util.getUserAgent(getContext(), "BakeThatApplication"));
    // This is the MediaSource representing the media to be played.
    MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
        .createMediaSource(Uri.parse(videoURL));
    // Prepare the player with the source.
    mPlayer.prepare(videoSource);
    mPlayer.seekTo(savedPositionTimestamp);
    mPlayer.setPlayWhenReady(true);
  }

  private void releasePlayer() {
    if (mPlayer != null){
      mPlayer.stop();
      mPlayer.release();
      mPlayer = null;
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    releasePlayer();
  }

  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    if (mPlayer != null) {
      outState.putLong(SAVED_PLAYER_POSITION, mPlayer.getCurrentPosition());
    }
  }
}
