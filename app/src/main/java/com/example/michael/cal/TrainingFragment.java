package com.example.michael.cal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;
import android.os.IBinder;

/**
 * Created by michael on 10/21/14.
 */
public class TrainingFragment extends PlaceholderFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private ToggleButton mTakingDataButton;
    private ToggleButton mWalkButton;

    NthSense sensorService;
    boolean mBound = false;

    public static TrainingFragment newInstance(int sectionNumber) {
        TrainingFragment fragment = new TrainingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_training, container, false);


        mTakingDataButton = (ToggleButton) rootView.findViewById(R.id.take_data1);
        mWalkButton = (ToggleButton) rootView.findViewById(R.id.walkingToggle1);

        mTakingDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorService.set_is_takingData(mTakingDataButton.isChecked());
            }
        });

        mWalkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorService.set_is_walking(mWalkButton.isChecked());
            }
        });
        return rootView;


    }

    @Override
    public void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(getActivity(), NthSense.class);
        //Context context = getActivity().getApplicationContext();
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mBound){
            getActivity().unbindService(mConnection);
            mBound = !mBound;
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            NthSense.NthBinder binder = (NthSense.NthBinder) service;
            sensorService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

}
