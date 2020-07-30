package com.bq.comm_config_lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.RecognizeService;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/22
 * 版权：
 */
public class ScanHelper implements LifecycleObserver {

    public static final int BANK_CARD = 1;
    public static final int ID_CARD_FRONT = 2;
    public static final int ID_CARD_BACK = 3;


    public static interface IdCardInter{
        default void idCardCallBack(IDCardResult result){}
        default void bankCard(String bankCardId){}
        default void error(String msg){};
    }

    private Context mContext;
    private IdCardInter mIdCardInter;

    public ScanHelper(Context context) {
        this.mContext = context;

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(@NonNull LifecycleOwner owner){
//        CameraNativeHelper.init(mContext, OCR.getInstance(mContext).getLicense(),
//                new CameraNativeHelper.CameraNativeInitCallback() {
//                    @Override
//                    public void onError(int errorCode, Throwable e) {
//                        String msg;
//                        switch (errorCode) {
//                            case CameraView.NATIVE_SOLOAD_FAIL:
//                                msg = "加载so失败，请确保apk中存在ui部分的so";
//                                break;
//                            case CameraView.NATIVE_AUTH_FAIL:
//                                msg = "授权本地质量控制token获取失败";
//                                break;
//                            case CameraView.NATIVE_INIT_FAIL:
//                                msg = "本地质量控制";
//                                break;
//                            default:
//                                msg = String.valueOf(errorCode);
//                        }
//                    }
//                });
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(@NonNull LifecycleOwner owner){
        // 释放本地质量控制模型
        CameraNativeHelper.release();
    }

    public void paseData(@Nullable Intent data,int type,IdCardInter inter){
        this.mIdCardInter = inter;
        if (data != null) {
            if(type == ID_CARD_FRONT || type == ID_CARD_BACK){
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = getSaveFile(mContext.getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
            }else if(type == BANK_CARD){
                paseBankcard();
            }

        }
    }

    private void paseBankcard(){
        RecognizeService.recBankCard(mContext, getSaveFile(mContext.getApplicationContext()).getAbsolutePath(),
                new RecognizeService.ServiceListener() {
                    @Override
                    public void onResult(String result) {
                        if(mIdCardInter != null){
                            mIdCardInter.bankCard(result);
                        }
                        if(result.contains("erro")){
                            return;
                        }

                    }
                });
    }

    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);
        OCR.getInstance(mContext).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null && mIdCardInter != null) {
                    mIdCardInter.idCardCallBack(result);
                }
            }
            @Override
            public void onError(OCRError error) {
                if(mIdCardInter != null)
                    mIdCardInter.error(error.getMessage());
            }
        });
    }

    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "234.jpg");
        return file;
    }

    public void startScan(int type) {
        if(type == ID_CARD_BACK || type == ID_CARD_FRONT){
//            Intent intent = new Intent(mContext, CameraActivity.class);
//            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
//                    getSaveFile(mContext).getAbsolutePath());
//            intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
//                    true);
//            // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
//            // 请手动使用CameraNativeHelper初始化和释放模型
//            // 推荐这样做，可以避免一些activity切换导致的不必要的异常
//            intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
//                    true);
//            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
//
//            ((Activity)mContext).startActivityForResult(intent, 111);


            Intent intent = new Intent(mContext, CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    getSaveFile(mContext).getAbsolutePath());
            if(type == ID_CARD_FRONT){
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
            }else{
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
            }
            ((Activity)mContext).startActivityForResult(intent, 111);

        }else if(type == BANK_CARD){
            Intent intent = new Intent(mContext, CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    getSaveFile(mContext).getAbsolutePath());

            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                    CameraActivity.CONTENT_TYPE_BANK_CARD);

            ((Activity)mContext).startActivityForResult(intent, 112);
        }

    }
}
