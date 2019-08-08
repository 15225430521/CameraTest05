package com.daodian.cameratest05;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;

public class MainActivity2 extends MyActivity {

    private RadioGroup radioGroup;
    private ImageView imageView;
    private CameraPreview mPreview;
    private int cameraId = 0;
    private int jiaodu = 270;

    @Override
    protected void onResume() {
        super.onResume();

        init();
    }

    public void onPause() {
        super.onPause();
        mPreview = null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){

                }
            }
        });

    }

    public void init(){
        if (mPreview == null){

            FrameLayout preview = findViewById(R.id.camera_preview);
            mPreview = new CameraPreview(this, cameraId, jiaodu, preview);
            preview.addView(mPreview);
        }
    }

    public void initView(){
        imageView = findViewById(R.id.image);
        radioGroup = findViewById(R.id.rg);
    }


}
