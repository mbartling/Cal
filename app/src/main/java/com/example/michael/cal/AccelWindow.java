package com.example.michael.cal;

/**
 * Created by michael on 10/12/14.
 */
public class AccelWindow {

    int numEntries;
    int i;
    public double variance;
    private AccelReading accelReadings[];


    public AccelWindow(int size) {
        numEntries = size;
        i = 0;
        variance = 0.0;
        accelReadings = new AccelReading[numEntries];
    }

    public AccelWindow() {
        numEntries = 15;
        i = 0;
        variance = 0.0;
        accelReadings = new AccelReading[numEntries];
    }

    public int addReading(AccelReading reading) {
        accelReadings[i] = reading;
        i++;
        i = i % numEntries;

        //Note can keep running sum here
        if(i == 0) {
            do_process();
            return 1;
        }
        return 0;
    }

    public AccelReading independentAverage() {

        float x = 0.0f;
        float y = 0.0f;
        float z = 0.0f;
        AccelReading reading;

        for(int j = 0; j < numEntries; j++) {
            reading = accelReadings[j];

            x += reading.x;
            y += reading.y;
            z += reading.z;
        }
        reading = new AccelReading();
        reading.x = x/ (float) numEntries;
        reading.y = y/ (float) numEntries;
        reading.z = z/ (float) numEntries;

        return reading;
    }

    public void do_process() {

        AccelReading avg = independentAverage();

        // Naive Normalize

        for(int j = 0; j < numEntries; j++) {
            accelReadings[j].x -= avg.x;
            accelReadings[j].y -= avg.y;
            accelReadings[j].z -= avg.z;
        }


        // Calculate Variance
        double dist[] = new double[numEntries];
        for(int j = 0; j < numEntries; j++) {
            dist[j] = Math.sqrt(accelReadings[j].x * accelReadings[j].x +
                accelReadings[j].y * accelReadings[j].y +
                accelReadings[j].z * accelReadings[j].z);
        }

        double Ex2 = 0.0;
        double E2x = 0.0;

        for(int j = 0; j < numEntries; j++) {

            Ex2 += dist[j]*dist[j];
            E2x += dist[j];
        }

        Ex2 = Ex2/(double)numEntries;
        E2x = E2x*E2x/(double) numEntries;

        variance = Ex2 - E2x;


    }
}
