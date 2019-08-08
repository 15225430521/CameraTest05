package com.daodian.cameratest05;

import android.content.Context;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


import utils.ImageToBase64Util;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "CameraPreview";
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private File mediaStorageDir;
    public static Camera.Parameters mParameters;

    private Uri outputMediaFileUri;
    private static int cameraId = 0;
    private static int jiaodu = 0;
    private View view;


    public CameraPreview(Context context, int cameraId, int jiaodu, View view) {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);

        this.cameraId = cameraId;
        this.jiaodu = jiaodu;
        this.view = view;
    }

    private static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(cameraId);
        } catch (Exception e) {
            Log.d(TAG, "camera is not available");
        }
        return c;
    }


    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = getCameraInstance();
        mParameters = mCamera.getParameters();

        List<String> list = mParameters.getSupportedFocusModes();
        init();

        int numberOfCameras = Camera.getNumberOfCameras();
        Log.e("摄像头数量", numberOfCameras + "");
        Log.e("对焦方式", list.toString());

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        // TODO Auto-generated method stub
                        if (success) {
                            if (camera != null) {

                            }
                        }
                    }

                });
            }
        });

        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            mCamera.setDisplayOrientation(jiaodu);



            //自动对焦
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {

                    // TODO Auto-generated method stub

                    // success为true表示对焦成功
                    if (success) {
                        if (camera != null) {


                            Log.e("对焦成功", "对焦成功");

                            // 停止预览
//                            camera.stopPreview();
                            // 拍照
//                            camera.takePicture(shutter, null, pictureCallback);
                        }
                    }
                }

            });

        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {


        if (mCamera != null) {
            mHolder.removeCallback(this);
            mCamera.setPreviewCallback(null) ;
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    }

//    @Override
//    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//
//    }


    /******************************************************************************************/
    private File getOutputMediaFile() {
        mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), TAG);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "failed to create directory");
                return null;
            }
        }

//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
//                "IMG_" + timeStamp + ".jpg");

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_TEST.jpg");

        Uri.fromFile(mediaFile);
//        outputMediaFileUri = Uri.fromFile(mediaFile);
        return mediaFile;
    }

    public Uri getOutputMediaFileUri() {
        return outputMediaFileUri;
    }

/******************************************************************************************/


    public void takePicture(final ImageView view) {
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                File pictureFile = getOutputMediaFile();

//                Log.e("pictureFile", pictureFile.getPath());
//                Log.e("pictureFile", pictureFile.toString());
//                Log.e("pictureFile", pictureFile.length() + "");

                if (pictureFile == null) {
                    Log.d(TAG, "Error creating media file, check storage permissions");
                    return;
                }
//                camera.startPreview();


                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);

//                    Bitmap myBitmap = BitmapFactory.decodeFile(mediaStorageDir.getPath() + File.separator +
//                            "IMG_TEST01.jpg");


                    fos.write(data);
                    fos.close();

//                    Log.e("图片路径", imagePath);
//                    Log.e("base64", "--------------------------------");
//                    Log.e("base64", ImageToBase64Util.ImageToBase64(imagePath));
//                    Log.e("base64", "--------------------------------");


                    byte[] bitmapdata = File2byte(pictureFile);

                    Log.e("base64", "--------------------------------");
                    Log.e("base64", "-->" + ImageToBase64Util.ImageToBase64(bitmapdata) + "<--");
                    Log.e("base64", "--------------------------------");

//                    Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
//                    view.setImageBitmap(bitmap);

                    camera.startPreview();
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d(TAG, "Error accessing file: " + e.getMessage());
                }
            }
        });
    }

    public static byte[] File2byte(File tradeFile){
        byte[] buffer = null;
        try
        {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return buffer;
    }


    public void destroy(){
        if (mCamera != null) {
            mCamera.setPreviewCallback(null) ;
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    public void init() {



//       setPictureSize("1920x1080");
//        setPictureSize("1280x720");
        setPictureSize("320x240");
//        setExposComp("0");
        setJpegQuality("100");
        setSceneMode("auto");
        setWhiteBalance("auto");


        mCamera.stopPreview();
        mCamera.setParameters(mParameters);
        mCamera.startPreview();
    }

    private static void setPreviewSize(String value) {
        String[] split = value.split("x");
        mParameters.setPreviewSize(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
//        mParameters.setPreviewSize(1440, 1080);
    }

    private static void setPictureSize(String value) {
        String[] split = value.split("x");
        mParameters.setPictureSize(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    private static void setFocusMode(String value) {
        mParameters.setFocusMode(value);
    }

    private static void setFlashMode(String value) {
        mParameters.setFlashMode(value);
    }

    private static void setWhiteBalance(String value) {
        mParameters.setWhiteBalance(value);
    }

    private static void setSceneMode(String value) {
        mParameters.setSceneMode(value);
    }

    private static void setExposComp(String value) {
        mParameters.setExposureCompensation(Integer.parseInt(value));
    }

    private static void setJpegQuality(String value) {
        mParameters.setJpegQuality(Integer.parseInt(value));
    }

    private static void setGpsData(Boolean value) {
        if (value.equals(false)) {
            mParameters.removeGpsData();
        }
    }



}