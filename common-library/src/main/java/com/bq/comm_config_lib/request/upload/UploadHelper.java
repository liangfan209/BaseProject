package com.bq.comm_config_lib.request.upload;

import android.os.Handler;
import android.os.Looper;

import com.bq.comm_config_lib.BaseApplication;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.utilslib.rsa.RSA;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/22
 * 版权：
 */
public class UploadHelper {

    private static UploadHelper instance;
    private static OkHttpClient mOkHttpClient;


    public interface CallBackInter{
        void callBack(String str);
        void error();
    }

    private UploadHelper() {
        mOkHttpClient = new OkHttpClient();
    }

    public static UploadHelper getInstance(){
        if(instance == null){
            instance = new UploadHelper();
        }
        return instance;
    }



    public void uploadStart(File file, CallBackInter inter) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MultipartBody.Builder builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM);
                Map<String,String> map = new HashMap();
                map.put("api","file.upload");
                map.put("role","customer");
                map.put("timestamp",String.valueOf(System.currentTimeMillis()));
                map.put("flag","file");
                map.put("auth", CommSpUtils.getToken());
                map.put("store_type","1");

//            map.put("token",token);

                Set<Map.Entry<String, String>> entries = map.entrySet();
                StringBuilder sb = new StringBuilder();
                for(Map.Entry<String,String> entry: entries){
                    String key = entry.getKey();
                    sb.append(entry.getKey()+"="+entry.getValue()+"&");

                    builder.addFormDataPart(entry.getKey(),entry.getValue());
                }
                String bufferStr = sb.toString();
                bufferStr =   bufferStr.substring(0,bufferStr.length()-1);
                String unSign = RSA.getSign(bufferStr);
                String s1 = RSA.sha1(unSign);
                String sign = RSA.sampling(s1, RSA.requestBodyStr2Map(bufferStr), 1.4);
                builder.addFormDataPart("sign",sign);
                builder.addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("image/png"), file));
                RequestBody requestBody = builder.build();
                Request request = new Request.Builder()
                        .url(BaseApplication.baseUrl)
                        .post(requestBody)
                        .build();
                try {
                    Response response = mOkHttpClient.newCall(request).execute();
                    String responseBody = response.body().string();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            inter.callBack(responseBody);
                        }
                    });

//                UploadImgBean uploadBean = new Gson().fromJson(responseBody,  UploadImgBean.class);
//                if ("10000".equals(uploadBean.getCode())) {
//                    String result = uploadBean.getResult();
//                    toMainTheardSaveUser(result);
//                    //将结果上传到服务器
//                }
                } catch (IOException e) {
                    e.printStackTrace();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            inter.error();
                        }
                    });
                }
            }
        }).start();
    }
}
