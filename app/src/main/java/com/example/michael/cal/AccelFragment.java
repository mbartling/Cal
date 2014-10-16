package com.example.michael.cal;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;


public class AccelFragment extends PlaceholderFragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    Activity activity;
    private boolean isRunning = false;
    private Button mRunButton;
    private ToggleButton mWalkButton;
    private static final String ARG_SECTION_NUMBER = "section_number";
    public AccelFragment() {
        // Required empty public constructor
    }


    public static AccelFragment newInstance(int sectionNumber) {
        AccelFragment fragment = new AccelFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TextView textView = new TextView(getActivity());
        //textView.setText(R.string.hello_blank_fragment);
        //return textView;

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRunButton = (Button) view.findViewById(R.id.take_data);
        mWalkButton = (ToggleButton) view.findViewById(R.id.walkingToggle);
        if(savedInstanceState == null) {
            mWalkButton.setEnabled(false);
        } else{
            isRunning = savedInstanceState.getBoolean("isRunning");
            boolean isWalking = savedInstanceState.getBoolean("isWalking");

            if(isRunning) mWalkButton.setEnabled(true);
            else mWalkButton.setEnabled(false);

            mWalkButton.setChecked(isWalking);

        }

        mRunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRunning = !isRunning;
                mWalkButton.setEnabled(!mWalkButton.isEnabled());
                /*if(mWalkButton.isEnabled()) mWalkButton.setEnabled(false);
                else
                {
                    mWalkButton.setEnabled(true);
                }
                */
                try{
                    ((AccelerometerInterface) activity).setIsTakingData(isRunning);
                }catch (ClassCastException cce){

                }
            }
        });

        mWalkButton.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                try{
                    ((AccelerometerInterface) activity).setWalkingState(mWalkButton.isChecked());
                }catch (ClassCastException cce){

                }
            }
        });
        return view;
    }




    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isRunning", isRunning);
        outState.putBoolean("isWalking", mWalkButton.isChecked());
    }

    public interface AccelerometerInterface {

        public void setIsTakingData(boolean state);
        public void setWalkingState(boolean state);
    }


    /*  @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }*/



}
