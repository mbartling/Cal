package com.example.michael.cal.CalSQL;

/**
 * Created by Kyle on 10/21/2014.
 */
public class CalSQLObj {
   // public long insertData(long timeStamp, float x, float y, float z, float proximityVal, float lux, int d_isWalking, int d_isTrainingData) {
    private long unixTime = System.currentTimeMillis();
    private long timeStamp;
    private float x;
    private float y;
    private float z;
    private float proximityVal;
    private float lux;
    private int d_isWalking;
    private int d_isTrainingData;

    public CalSQLObj(long timeStamp, float x, float y, float z, float proximityVal, float lux, int d_isWalking, int d_isTrainingData) {
        this.setUnixTime(unixTime);
        this.setTimeStamp(timeStamp);
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setProximityVal(proximityVal);
        this.setLux(lux);
        this.setD_isWalking(d_isWalking);
        this.setD_isTrainingData(d_isTrainingData);
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getProximityVal() {
        return proximityVal;
    }

    public void setProximityVal(float proximityVal) {
        this.proximityVal = proximityVal;
    }

    public float getLux() {
        return lux;
    }

    public void setLux(float lux) {
        this.lux = lux;
    }

    public int getD_isWalking() {
        return d_isWalking;
    }

    public void setD_isWalking(int d_isWalking) {
        this.d_isWalking = d_isWalking;
    }

    public int getD_isTrainingData() {
        return d_isTrainingData;
    }

    public void setD_isTrainingData(int d_isTrainingData) {
        this.d_isTrainingData = d_isTrainingData;
    }

    public long getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(long unixTime) {
        this.unixTime = unixTime;
    }
}