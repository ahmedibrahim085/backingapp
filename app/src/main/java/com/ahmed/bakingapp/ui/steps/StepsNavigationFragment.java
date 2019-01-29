package com.ahmed.bakingapp.ui.steps;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.ui.UiConstants;
import com.ahmed.bakingapp.ui.listeners.OnRecipeNavigationClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link StepsNavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepsNavigationFragment extends Fragment {
    private static final String TAG = StepsNavigationFragment.class.getSimpleName();

    // the fragment initialization parameters,
    private static final String ARG_NUMBER_OF_STEPS = UiConstants.getRecipeStepsNumber();

    // TODO: Rename and change types of parameters
    private int numberOfRecipeSteps;

    private OnRecipeNavigationClickListener mListener;

    TextView tv_numberOfSteps;
    ImageView img_previousRecipe;
    ImageView img_nextRecipe;

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
    // TODO: Rename and change types and number of parameters
    public static StepsNavigationFragment newInstance(int stepsNumber) {
        StepsNavigationFragment fragment = new StepsNavigationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NUMBER_OF_STEPS, stepsNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( getArguments() != null ) {
            numberOfRecipeSteps = getArguments().getInt(ARG_NUMBER_OF_STEPS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Steps Navigation FrameLayout
        // Inflate the layout for this fragment
        View stepsNavigationView = inflater.inflate(R.layout.fragment_recipe_steps_navigation,
                container, false);
        //  =======  ======= Previous Recipe Step Instruction =======  =======
        img_previousRecipe = stepsNavigationView.findViewById(R.id.img_previous_recipe);
        img_previousRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mListener.onPreviousRecipeSelected(UiConstants.getCurrentStepId());
            }
        });
        //  =======  ======= Recipe Step Instruction Navigation Counter =======  =======
        tv_numberOfSteps = (TextView) stepsNavigationView.findViewById(R.id.tv_numberOfSteps);
        String stepCounter =
                String.format(getActivity().getResources().getString(R.string.recipe_step_counter),
                UiConstants.getCurrentStepId(), numberOfRecipeSteps);
        tv_numberOfSteps.setText(stepCounter);

        // =======  ======= Next Recipe Step Instruction =======  =======
        img_nextRecipe = stepsNavigationView.findViewById(R.id.img_next_recipe);
        img_nextRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mListener.onNextRecipeSelected(UiConstants.getCurrentStepId());
            }
        });
        //=======
        return stepsNavigationView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if ( context instanceof OnRecipeNavigationClickListener ) {
            mListener = (OnRecipeNavigationClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeNavigationClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
