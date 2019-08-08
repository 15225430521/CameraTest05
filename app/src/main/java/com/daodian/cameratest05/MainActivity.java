package com.daodian.cameratest05;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;


public class MainActivity extends MyActivity {

    private Button confirmationOrder;
    private ImageView imageView;
    private CameraPreview mPreview;
    private FrameLayout preview;
    private int cameraId = 1;
    private int jiaodu = 180;
//    private Animation translate;//获取平移动画资源

    private CommodityAdapter adapter;
    private ListView listView;

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
        setContentView(R.layout.activity_main);

        initView();
        openScan();


        adapter = new CommodityAdapter(this);
        listView.setAdapter(adapter);


        confirmationOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

    }

    //扫描效果
    public void openScan(){
        imageView.bringToFront();
        imageView.startAnimation(MyApplication.getTranslate());
    }

    public void initView(){
        preview = findViewById(R.id.camera_preview);
        imageView = findViewById(R.id.image);
        listView = findViewById(R.id.list);
        imageView = findViewById(R.id.imageView);
        confirmationOrder = findViewById(R.id.confirmation_order);
    }

    public void init(){
        if (mPreview == null){
            mPreview = new CameraPreview(this, cameraId, jiaodu, preview);
            preview.addView(mPreview);

        }
    }

    //动态权限获取
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
