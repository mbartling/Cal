package com.example.michael.cal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by michael on 10/16/14.
 *
 * This is a placeholder for my data saving method. Later, I want to switch to an XML based system.
 */
public class DataSaverPlaceholder {
    private String filenameBase = "CalAccelData";
    private String filename;
    private String timeStamp;
    //private FileOutputStream outputStream;
    private File file;
    private File path;
    private int numTests = 0;
    private OutputStream os;

    DataSaverPlaceholder(){};

    DataSaverPlaceholder(File targetPath){

        // Create the Target Path
        path = targetPath;
        path.mkdirs();

        newFile();

    }
    DataSaverPlaceholder(File targetPath, String oldTimeStamp){

        // Create the Target Path
        path = targetPath;
        path.mkdirs();
        timeStamp = oldTimeStamp;
        openFile();

    }


    public void updateTimeStamp(){
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("_MM-dd-yyy_HH-mm-ss");
        timeStamp = format.format(now);
    }

    public void setFilenameBase(String newBaseName){
        filenameBase = newBaseName;
    }

    public void newFile(){
        updateTimeStamp();
        openFile();
    }

    public void setTimeStamp(String timeStamp1) {
        timeStamp = timeStamp1;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void openFile() {
        filename = filenameBase + timeStamp + ".csv";
        try {
            file = new File(path, filename);
            os = new FileOutputStream(file);
        } catch (Exception e ){
            e.printStackTrace();
        }
    }

    public void writeData(String data ){
        try {
            os.write(data.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
