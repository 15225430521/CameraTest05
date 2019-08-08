package com.daodian.cameratest05;

import android.util.Log;

import utils.ImageToBase64Util;

public class Test {

    public static  void main(String[] args){


        String img = "data:image/jpeg;base64," + ImageToBase64Util.ImageToBase64(
                "D:\\android workspace\\CameraTest05\\app\\src\\main\\res\\mipmap-xxxhdpi\\test.jpg") ;
        System.out.println(img.length());
        System.out.println(img);
    }
}
