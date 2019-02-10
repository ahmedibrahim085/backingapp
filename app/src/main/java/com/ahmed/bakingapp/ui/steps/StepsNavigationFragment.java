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
    PlayerView exoPlayerView;


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
        View stepsNavigationView = inflater.inflate(R.layout.fragment_recipe_steps_navigation,
                container, false);
        setNavigationFragmentUI(stepsNavigationView);
        return stepsNavigationView;
    }

    @Override
    public void onResume() {
        super.onResume();
        VideoPlayer.initializeMediaSession(getActivity());
        VideoPlayer.initializePlayer(Uri.parse(Constants.getRecipeSingleStepVideo()), exoPlayerView, getActivity());
        updateNavigationFragmentView();
    }

    @Override
    public void onStop() {
        super.onStop();
        if ( !Constants.isTwoPan() ) {
            VideoPlayer.releasePlayer();
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        onRecipeNavigationClickListener = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateNavigationFragmentView();
        int currentOrientation = getActivity().getResources().getConfiguration().orientation;
        if ( currentOrientation == Configuration.ORIENTATION_LANDSCAPE ) {
            hideSystemUiFullScreen();
        } else {
            hideSystemUi();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if ( VideoPlayer.getmExoPlayer() != null ) {
            // ExoPLayer Window Index
            outState.putInt(Constants.getPlayerWindowIndex(),
                    VideoPlayer.getmExoPlayer().getCurrentWindowIndex());
            // ExoPLayer Current Position
            outState.putLong(Constants.getPlayerCurrentPosition(),
                    VideoPlayer.getmExoPlayer().getCurrentPosition());
            // ExoPLayer boolean Play When Ready
            outState.putBoolean(Constants.getPlayerWhenReady(),
                    VideoPlayer.getmExoPlayer().getPlayWhenReady());
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

    private void hideSystemUiFullScreen() {
/*        this.exoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
        | View.SYSTEM_UI_FLAG_FULLSCREEN
        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_IMMERSIVE);*/


        if ( ((AppCompatActivity) getActivity()).getSupportActionBar() != null ) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
        this.exoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void hideSystemUi() {
        exoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }


    //  ======= ======= ======= Fragment Life Cycle ======= END/FIN =======

    //  ======= ======= ======= Fragment UI ======= START =======
    private void setNavigationFragmentUI(View view) {
        // This is Organized vertically as in the view
        // Video
        exoPlayerView = view.findViewById(R.id.video_view);
        // Recipe Step Instructions
        tv_oneRecipeStepInstruction = view.findViewById(R.id.tv_oneRecipeStepInstruction);
        // Navigation
        // Organized based on a normal Navigation logic
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
        } else {
            exoPlayerView.setVisibility(View.VISIBLE);
            Uri mediaUri = Uri.parse(Constants.getRecipeSingleStepVideo());
            if ( VideoPlayer.getmExoPlayer() == null ) {
                VideoPlayer.initializePlayer(mediaUri, exoPlayerView, getActivity());
            }
            VideoPlayer.setPlayerMediaSource(getActivity(), mediaUri);
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
                VideoPlayer.getmExoPlayer().stop();
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
                VideoPlayer.getmExoPlayer().stop();
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

    //  ======= ======= ======= Fragment UI ======= END/FIN =======



}
