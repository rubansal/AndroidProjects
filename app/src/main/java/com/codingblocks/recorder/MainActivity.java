package com.codingblocks.recorder;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int RECORD_AUDIO = 1234;
    private FloatingActionButton recordButton;
    private Chronometer chronometer = null;

    private boolean startRecording = true;

    private Button btnViewRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordButton = findViewById(R.id.btnRecord);
        chronometer = findViewById(R.id.chronometer);
        btnViewRecord=findViewById(R.id.btnViewRecord);

        btnViewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,ViewRecording.class);
                startActivity(i);
            }
        });

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int perm = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO);
                int perm1 = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (perm != PackageManager.PERMISSION_GRANTED && perm1 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{
                                    Manifest.permission.RECORD_AUDIO,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },
                            121
                    );
                    return;
                } else {
                    onRecord(startRecording);
                    startRecording = !startRecording;
                }
            }
        });
    }

    private void onRecord(boolean start) {
        Intent i = new Intent(MainActivity.this, RecordService.class);

        if (start) {
            recordButton.setImageResource(R.drawable.ic_stop);
            Toast.makeText(this, "Recording Started", Toast.LENGTH_SHORT).show();
            File folder = new File(Environment.getExternalStorageDirectory() + "/Recorder");
            if (!folder.exists()) {
                folder.mkdir();
            }
            //Log.d("RecordFile", "onRecord: "+Environment.getExternalStorageDirectory());

            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            MainActivity.this.startService(i);
            MainActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            recordButton.setImageResource(R.drawable.ic_mic);
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            MainActivity.this.stopService(i);
            MainActivity.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }
}
