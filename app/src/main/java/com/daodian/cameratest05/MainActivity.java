package com.daodian.cameratest05;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button button, change;
    private ImageView imageView;
    private CameraPreview mPreview;
    private FrameLayout preview;
    private int cameraId = 0;
    private int jiaodu = 270;

    @Override
    protected void onResume() {
        super.onResume();

        verifyStoragePermissions(MainActivity.this);



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
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(MainActivity.this);

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

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });



    }

    public void init(){
        if (mPreview == null){
            preview = (FrameLayout) findViewById(R.id.camera_preview);
            mPreview = new CameraPreview(this, cameraId, jiaodu, preview);

            preview.addView(mPreview);

        }
    }


    public static void verifyStoragePermissions(Activity activity) {
        final int REQUEST_EXTERNAL_STORAGE = 1;
        final String[] PERMISSIONS_STORAGE = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE" };

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //去寻找是否已经有了相机的权限
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(MainActivity.this,"您申请了动态权限",Toast.LENGTH_SHORT).show();
            //如果有了相机的权限有调用相机
//            startCamera();

        }else{
            //否则去请求相机权限
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA},100);

        }
    }



}
