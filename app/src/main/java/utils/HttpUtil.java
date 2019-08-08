package utils;

import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 封装的网络请求
 */
public final class HttpUtil {

    private static HttpUtil httpUtil;
    private  OkHttpClient client;
    private static final String TAG = "HttpUtil";

    private HttpUtil(){
        client = new OkHttpClient();
    }

    public static HttpUtil getHttpUtil(){
        if (httpUtil == null){
            httpUtil = new HttpUtil();
        }

        return httpUtil;
    }



    /**
     * 通过get请求获取数据
     * @param url       地址
     */
    public void getRequest(String url, final MyCallBack mycallBack){
        final Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                mycallBack.callBack("-1");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mycallBack.callBack(response.body().string());
            }
        });


    }



    /**
     * okHttp post同步请求表单提交
     * @param requestUrl 接口地址
     * @param paramsMap 请求参数
     */
    private void postRequest(String requestUrl, HashMap<String, String> paramsMap, final MyCallBack mycallBack) {
        try {
            //创建一个FormBody.Builder
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                //追加表单信息
                builder.add(key, paramsMap.get(key));
            }
            //生成表单实体对象
            RequestBody formBody = builder.build();

            //创建一个请求
            final Request request = addHeaders().url(requestUrl).post(formBody).build();
            //创建一个Call
            final Call call = client.newCall(request);
            //执行请求
            Response response = call.execute();
            if (response.isSuccessful()) {

                mycallBack.callBack(response.body().string());
                Log.e(TAG, "response ----->" + response.body().string());
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 统一为请求添加头信息
     * @return
     */
    private Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", "keep-alive")
                .addHeader("platform", "2")
                .addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE)
                .addHeader("appVersion", "3.2.0");
        return builder;
    }

}
