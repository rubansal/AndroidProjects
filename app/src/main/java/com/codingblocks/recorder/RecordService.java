package com.codingblocks.recorder;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class RecordService extends Service {

    private MediaRecorder mediaRecorder=null;

    private String fileName=null;
    private String filePath=null;

    private DBHelper dbHelper;

    private long startingTimeMillis=0;
    private long elapsedTimeMillis=0;
    public RecordService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper=new DBHelper(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if(mediaRecorder!=null){
            stopRecording();
        }
        super.onDestroy();
    }

    public void startRecording(){
        setFileNameAndPath();
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(filePath);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setAudioChannels(1);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            startingTimeMillis=System.currentTimeMillis();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFileNameAndPath(){
        int count=0;
        File f;
        do{
            count++;
            fileName="My_Recording"+"_"+count+".mp4";
            filePath= Environment.getExternalStorageDirectory().getAbsolutePath() ;
            filePath+="/Recorder/"+fileName;
            f=new File(filePath);
            Log.d("FileName", "setFileNameAndPath: "+filePath);
        }while(f.exists()&&!f.isDirectory());
    }

    public void stopRecording(){
        mediaRecorder.stop();
        elapsedTimeMillis=System.currentTimeMillis()-startingTimeMillis;
        mediaRecorder.release();
        mediaRecorder=null;
        dbHelper.addRecording(fileName,filePath,elapsedTimeMillis);
    }
}
