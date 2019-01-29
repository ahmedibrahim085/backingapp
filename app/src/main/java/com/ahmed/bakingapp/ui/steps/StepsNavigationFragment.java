package com.ahmed.bakingapp.ui.steps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmed.bakingapp.R;
import com.ahmed.bakingapp.ui.UiConstants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepsNavigationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepsNavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepsNavigationFragment extends Fragment {
    private static final String TAG = StepsNavigationFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NUMBER_OF_STEPS = UiConstants.getRecipeStepsNumber();
    static int currentStepId;

    // TODO: Rename and change types of parameters
    private int numberOfRecipeSteps;

    private OnFragmentInteractionListener mListener;

    TextView tv_numberOfSteps;

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
        // Inflate the layout for this fragment
        View stepsNavigationView = inflater.inflate(R.layout.fragment_recipe_steps_navigation,
                container, false);
        // Video FrameLayout

        // Steps FrameLayout

        // Steps Navigation FrameLayout
        tv_numberOfSteps = (TextView) stepsNavigationView.findViewById(R.id.tv_numberOfSteps);
        Log.e(TAG, "getCurrentStepId : " + getCurrentStepId());
        Log.e(TAG, "numberOfRecipeSteps : " + numberOfRecipeSteps);

        String stepCounter =
                String.format(getActivity().getResources().getString(R.string.recipe_step_counter),
                getCurrentStepId(), numberOfRecipeSteps);
        Log.e(TAG, "stepCounter : " + stepCounter);

//        String stepsCount = "Recipe Steps" +getCurrentStepId()+"/"+String.valueOf(numberOfRecipeSteps);
        tv_numberOfSteps.setText(stepCounter);
        return stepsNavigationView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if ( mListener != null ) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if ( context instanceof OnFragmentInteractionListener ) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static int getCurrentStepId() {
        return StepsNavigationFragment.currentStepId;
    }

    public static void setCurrentStepId(int currentStepId) {
        // the +1 is because list starts counting from 0
        StepsNavigationFragment.currentStepId = currentStepId+1;
    }
}
