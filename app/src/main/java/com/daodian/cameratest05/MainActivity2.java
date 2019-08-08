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

public class MainActivity2 extends AppCompatActivity {

    private Button button, change;
    private ImageView imageView;
    private CameraPreview mPreview;
    private int cameraId = 1;
    private int jiaodu = 180;

    @Override
    protected void onResume() {
        super.onResume();

        //去寻找是否已经有了相机的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){

            //Toast.makeText(MainActivity.this,"您申请了动态权限",Toast.LENGTH_SHORT).show();
            //如果有了相机的权限有调用相机
//            startCamera();

        }else{
            //否则去请求相机权限
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},100);

        }

        init();
    }

    public void onPause() {
        super.onPause();
        mPreview = null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);

        button = findViewById(R.id.button_capture_photo);
        change = findViewById(R.id.change);
        imageView = findViewById(R.id.image);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPreview.takePicture(imageView);
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPreview.destroy();
                mPreview = null;

                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void init(){
        if (mPreview == null){

            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            mPreview = new CameraPreview(this, cameraId, jiaodu, preview);
            preview.addView(mPreview);
        }
    }



}
