package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ImageToBase64Util {

    public static String ImageToBase64(String imgPath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        Base64Encoder encoder = new Base64Encoder();
        // 返回Base64编码过的字节数组字符串
        System.out.println("本地图片转换Base64:" + encoder.encode(Objects.requireNonNull(data)));

        return encoder.encode(Objects.requireNonNull(data));

    }

    public static String ImageToBase64(byte[] data) {
//        byte[] data = null;
//        // 读取图片字节数组
//        try {
//            InputStream in = new FileInputStream(imgPath);
//            data = new byte[in.available()];
//            in.read(data);
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // 对字节数组Base64编码
        Base64Encoder encoder = new Base64Encoder();
        // 返回Base64编码过的字节数组字符串
        System.out.println("本地图片转换Base64:" + encoder.encode(Objects.requireNonNull(data)));

        return "data:image/jpg;base64,/9j" + encoder.encode(Objects.requireNonNull(data));

    }



}
