package com.example.michael.cal.CommAudio;


import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * Created by michael on 10/29/14.
 */
public class CommAudioEngine {

    private AudioRecord mRecorder = null;
    private Thread recordingThread = null;
    private boolean isRecording = false;

    int BufferElements2Rec = 1024;
    int BytesPerElement = 2; //16 bit PCM
    public CommAudioEngine()
    {
        int sampleRate = getValidSampleRates();
        mRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, BufferElements2Rec * BytesPerElement);
    }


    public int getValidSampleRates() {
        int r=44100;
        for (int rate : new int[] {/*8000,11025,16000,22050,*/ 44100, 48000}) {  // add the rates you wish to check against
            int bufferSize = AudioRecord.getMinBufferSize(rate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
            if (bufferSize > 0) {
                r= rate;
            }
        }
        return r;
    }

    public void startRecording()
    {
        mRecorder.startRecording();
        isRecording = true;
        recordingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                processAudio();
            }
        }, "Audio Communication Recording Thread");

        recordingThread.start();
    }

    private void processAudio()
    {
        //This function will process the audio stream for relevant commands
        short sData[] = new short[BufferElements2Rec];
        while (isRecording)
        {
            //Do processing
            mRecorder.read(sData, 0, BufferElements2Rec);
        }
    }

    public void stopRecording() {
        // stops the recording activity
        if (mRecorder != null) {
            isRecording = false;
            mRecorder.stop();
            //mRecorder.release();
            //mRecorder = null;
            recordingThread = null; //Dangerous?
        }
    }
}
