package com.example.michael.cal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Anthony on 10/24/2014.
 */
public class DBDevFrag extends PlaceholderFragment{
    private static final String ARG_SECTION_NUMBER = "section_number";

    private Button mclearDbButton;
    private Button msendDbButton;

    dataService sensorService;

    public static DBDevFrag newInstance(int sectionNumber) {
        DBDevFrag fragment = new DBDevFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dbdev, container, false);

        mclearDbButton = (Button) rootView.findViewById(R.id.clearData);
        msendDbButton = (Button) rootView.findViewById(R.id.sendData);

        mclearDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sensorService.clearDatabase();
            }
        });

        msendDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorService.submitData();
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getActivity(), dataService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public void onDestroy() { //Michael claims "possibly dangerous" DO NOT STORE NEAR OPEN FLAMES
        super.onDestroy();
        getActivity().unbindService(mConnection);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            dataService.dataBinder binder = (dataService.dataBinder) service;
            sensorService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }
    };

}
