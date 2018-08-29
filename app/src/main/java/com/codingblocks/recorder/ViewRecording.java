package com.codingblocks.recorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ViewRecording extends AppCompatActivity {

    private RecyclerView rvRecording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recording);

        rvRecording=findViewById(R.id.rvRecording);
        rvRecording.setLayoutManager(new LinearLayoutManager(this));
        ViewRecordingAdapter viewRecordingAdapter=new ViewRecordingAdapter(this);
        rvRecording.setAdapter(viewRecordingAdapter);
    }
}
