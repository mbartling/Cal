package com.example.michael.cal;

/**
 * Created by michael on 10/12/14.
 */
public class AccelReading {

    public float x,y,z;

    public AccelReading(float xIn, float yIn, float zIn) {
        x = xIn;
        y = yIn;
        z = zIn;
    }

    public AccelReading()
    {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }

}
