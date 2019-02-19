package com.ahmed.bakingapp.ui.steps;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.ui.listeners.OnRecipeNavigationClickListener;
import com.ahmed.bakingapp.ui.recipeDetails.RecipeDetailsFragment;
import com.ahmed.bakingapp.utils.Constants;
import com.ahmed.bakingapp.utils.VideoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link StepsNavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepsNavigationFragment extends Fragment implements Player.EventListener {
    private static final String TAG = StepsNavigationFragment.class.getSimpleName();

    // the fragment initialization parameters,
    private static final String ARG_NUMBER_OF_STEPS = Constants.getRecipeStepsNumber();

    private int numberOfRecipeSteps;

    private OnRecipeNavigationClickListener onRecipeNavigationClickListener;
    RecipeDetailsFragment recipeDetailsFragment;
    TextView tv_numberOfSteps;
    TextView tv_oneRecipeStepInstruction;
    ImageView img_previousRecipe;
    ImageView img_nextRecipe;
    View stepsNavigationView;
    View v_description_divider;
    View v_navigation_divider;
    PlayerView exoPlayerView;

    private static int currentWindow;
    private static long playbackPosition;
    private static boolean playWhenReady;

    public StepsNavigationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param stepsNumber Parameter 1. // Number of Steps
     * @return A new instance of fragment StepsNavigationFragment.
     */
    public static StepsNavigationFragment newInstance(int stepsNumber) {
        StepsNavigationFragment fragment = new StepsNavigationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NUMBER_OF_STEPS, stepsNumber);
        fragment.setArguments(args);
        return fragment;
    }

    //  ======= ======= ======= Fragment Life Cycle ======= START =======
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if ( context instanceof OnRecipeNavigationClickListener ) {
            onRecipeNavigationClickListener = (OnRecipeNavigationClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeNavigationClickListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( savedInstanceState == null ) {
            if ( getArguments() != null ) {
                numberOfRecipeSteps = getArguments().getInt(ARG_NUMBER_OF_STEPS);
            }
            if ( recipeDetailsFragment == null ) {
                recipeDetailsFragment = new RecipeDetailsFragment();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Steps Navigation FrameLayout
        // Inflate the layout for this fragment
        stepsNavigationView = inflater.inflate(R.layout.fragment_recipe_steps_navigation,
                container, false);
        setNavigationFragmentUI(stepsNavigationView);
        return stepsNavigationView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if ( VideoPlayer.getmMediaSession() == null ) {
            VideoPlayer.initializeMediaSession(getActivity());
        }
        if ( VideoPlayer.getmExoPlayer() == null ) {
            VideoPlayer.initializePlayer(Uri.parse(Constants.getRecipeSingleStepVideo()), exoPlayerView, getActivity());
        }
        int currentOrientation = getActivity().getResources().getConfiguration().orientation;
        if ( currentOrientation == Configuration.ORIENTATION_LANDSCAPE ) {
            if ( !Constants.isTwoPan() ) {
                hideSystemUiFullScreen();
            }
        } else {
            if ( !Constants.isTwoPan() ) {
                hideSystemUi();
            }
        }
        if ( exoPlayerView.getVisibility() == View.VISIBLE ) {
            if ( VideoPlayer.getmExoPlayer() == null ) {
                VideoPlayer.initializeMediaSession(getActivity());
                VideoPlayer.initializePlayer(Uri.parse(Constants.getRecipeSingleStepVideo()), exoPlayerView, getActivity());
            }

            VideoPlayer.getmExoPlayer().seekTo(StepsNavigationFragment.currentWindow,
                    StepsNavigationFragment.playbackPosition);
            VideoPlayer.getmExoPlayer().setPlayWhenReady(StepsNavigationFragment.playWhenReady);
        }
        // updating the player seek will be down the tree of the update NavigationFragment
        updateNavigationFragmentView();
    }

    @Override
    public void onPause() {
        super.onPause();
        VideoPlayer.stopPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        VideoPlayer.stopPlayer();
        VideoPlayer.releasePlayer();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        onRecipeNavigationClickListener = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if ( VideoPlayer.getmExoPlayer() != null ) {
            // ExoPLayer Window Index
            StepsNavigationFragment.currentWindow =
                    VideoPlayer.getmExoPlayer().getCurrentWindowIndex();
            outState.putInt(Constants.getPlayerWindowIndex(),
                    StepsNavigationFragment.currentWindow);
            // ExoPLayer Current Position
            StepsNavigationFragment.playbackPosition =
                    VideoPlayer.getmExoPlayer().getCurrentPosition();
            outState.putLong(Constants.getPlayerCurrentPosition(),
                    StepsNavigationFragment.playbackPosition);
            // ExoPLayer boolean Play When Ready
            StepsNavigationFragment.playWhenReady = VideoPlayer.getmExoPlayer().getPlayWhenReady();
            outState.putBoolean(Constants.getPlayerWhenReady(),
                    StepsNavigationFragment.playWhenReady);
        }
        outState.putInt(Constants.getRecipeStepsNumber(), numberOfRecipeSteps);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if ( savedInstanceState != null ) {
            numberOfRecipeSteps = savedInstanceState.getInt(Constants.getRecipeStepsNumber());
            if ( exoPlayerView.getVisibility() == View.VISIBLE ) {
                if ( VideoPlayer.getmExoPlayer() == null ) {
                    VideoPlayer.initializeMediaSession(getActivity());
                    VideoPlayer.initializePlayer(Uri.parse(Constants.getRecipeSingleStepVideo()), exoPlayerView, getActivity());
                }
                int windowIndex = savedInstanceState.getInt(Constants.getPlayerWindowIndex());
                long positionMs = savedInstanceState.getLong(Constants.getPlayerCurrentPosition());
                VideoPlayer.getmExoPlayer().seekTo(windowIndex, positionMs);
                VideoPlayer.getmExoPlayer().setPlayWhenReady(savedInstanceState.getBoolean(Constants.getPlayerWhenReady()));
            }
        }

    }


    //  ======= ======= ======= Fragment Life Cycle ======= END/FIN =======

    //  ======= ======= ======= Fragment UI ======= START =======
    private void setNavigationFragmentUI(View view) {
        // Organized based on a normal Navigation logic
        // This is Organized vertically as in the view
        // Video
        exoPlayerView = view.findViewById(R.id.video_view);
        // Divider
        v_description_divider = stepsNavigationView.findViewById(R.id.v_description_divider);
        // Recipe Step Instructions
        tv_oneRecipeStepInstruction = view.findViewById(R.id.tv_oneRecipeStepInstruction);
        // Navigation
        // Divider - Navigation
        v_navigation_divider = stepsNavigationView.findViewById(R.id.v_navigation_divider);
        // Navigation - Previous
        img_previousRecipe = view.findViewById(R.id.img_previous_recipe);
        // Navigation - position
        tv_numberOfSteps = (TextView) view.findViewById(R.id.tv_numberOfSteps);
        // Navigation - Next
        img_nextRecipe = view.findViewById(R.id.img_next_recipe);
        setPreviousStepNavigation();
        setNextStepNavigation();
    }

    public void updateNavigationFragmentView() {
        tv_oneRecipeStepInstruction.setText(Constants.getRecipeSingleStepDescription());
        updateNavigationImageButtons();
        updateRecipeStepCounter();
        updateRecipeVideoURL();
    }

    //  ======= ======= ======= Video Controllers ======= START ======= =======

    private void updateRecipeVideoURL() {
        if ( Constants.getRecipeSingleStepVideo().isEmpty() ) {
            exoPlayerView.setVisibility(View.GONE);
            VideoPlayer.stopPlayer();
        } else {
            Uri mediaUri = Uri.parse(Constants.getRecipeSingleStepVideo());
            exoPlayerView.setVisibility(View.VISIBLE);
            if ( VideoPlayer.getmExoPlayer() == null ) {
                VideoPlayer.initializePlayer(mediaUri, exoPlayerView, getActivity());
            }
            VideoPlayer.setPlayerMediaSource(getActivity(), mediaUri);
            VideoPlayer.getmExoPlayer().seekTo(StepsNavigationFragment.currentWindow,
                    StepsNavigationFragment.playbackPosition);
        }
    }


    // ======= ======= ======= Video Controllers ======= END/FIN ======= =======
    // ======= ======= ======= Steps Navigation Controllers - SET - ======= START ======= =======
    private void setPreviousStepNavigation() {
        img_previousRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.setCurrentStepId(Constants.getCurrentStepId() - 1);
                if ( Constants.getCurrentStepId() < 0 ) {
                    Constants.setCurrentStepId(0);
                }
                VideoPlayer.stopPlayer();
                onRecipeNavigationClickListener.onPreviousRecipeSelected(Constants.getCurrentStepId());
                updateNavigationFragmentView();
            }
        });
    }

    private void setNextStepNavigation() {
        img_nextRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.setCurrentStepId(Constants.getCurrentStepId() + 1);
                if ( Constants.getCurrentStepId() >= numberOfRecipeSteps ) {
                    Constants.setCurrentStepId(numberOfRecipeSteps - 1);
                }
                VideoPlayer.stopPlayer();
                onRecipeNavigationClickListener.onNextRecipeSelected(Constants.getCurrentStepId());
                updateNavigationFragmentView();
            }
        });
    }

    //  ======= ======= ======= Steps Navigation Controllers - SET - ======= END/FIN ======= =======
    // ======= Steps Navigation Controllers - User Navigates - ======= START =======
    private void updateNavigationImageButtons() {
        if ( Constants.getCurrentStepId() == 0 ) {
            img_previousRecipe.setVisibility(View.GONE);
            img_nextRecipe.setVisibility(View.VISIBLE);
        } else if ( Constants.getCurrentStepId() == (numberOfRecipeSteps - 1) ) {
            img_nextRecipe.setVisibility(View.GONE);
            img_previousRecipe.setVisibility(View.VISIBLE);
        } else {
            img_nextRecipe.setVisibility(View.VISIBLE);
            img_previousRecipe.setVisibility(View.VISIBLE);
        }
    }

    private void updateRecipeStepCounter() {
        if ( Constants.getCurrentStepId() == 0 ) {
            tv_numberOfSteps.setText(getActivity().getResources().getString(R.string.navigation_goto_instructions));
        } else if ( Constants.getCurrentStepId() == (numberOfRecipeSteps - 1) ) {
            tv_numberOfSteps.setText(getActivity().getResources().getString(R.string.navigation_back_instructions));
        } else {
            String stepCounter =
                    String.format(getActivity().getResources().getString(R.string.recipe_step_counter),
                            Constants.getCurrentStepId(), numberOfRecipeSteps - 1);
            tv_numberOfSteps.setText(stepCounter);
        }
    }
    // ======= Steps Navigation Controllers - User Navigates - ======= END/FIN =======


    private void hideSystemUiFullScreen() {
        if ( ((AppCompatActivity) getActivity()).getSupportActionBar() != null ) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
        showOrHideOtherViewElements(View.GONE);
        this.exoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void hideSystemUi() {
        if ( ((AppCompatActivity) getActivity()).getSupportActionBar() != null ) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }
        showOrHideOtherViewElements(View.VISIBLE);
        exoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    void showOrHideOtherViewElements(int visibility) {
        if ( tv_numberOfSteps != null ) {
            tv_numberOfSteps.setVisibility(visibility);
        }
        if ( tv_oneRecipeStepInstruction != null ) {
            tv_oneRecipeStepInstruction.setVisibility(visibility);
        }

        if ( img_previousRecipe != null ) {
            img_previousRecipe.setVisibility(visibility);
        }
        if ( img_nextRecipe != null ) {
            img_nextRecipe.setVisibility(visibility);
        }
        if ( v_description_divider != null ) {
            v_description_divider.setVisibility(visibility);
        }

        if ( v_navigation_divider != null ) {
            v_navigation_divider.setVisibility(visibility);
        }
    }


    //  ======= ======= ======= Fragment UI ======= END/FIN =======

}
