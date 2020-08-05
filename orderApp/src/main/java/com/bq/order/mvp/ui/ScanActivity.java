package com.bq.order.mvp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.order.R;
import com.fan.baseuilibrary.scan.CaptureFragment;
import com.fan.baseuilibrary.scan.CodeUtils;
import com.fan.baseuilibrary.scan.ImageUtil;
import com.fan.baseuilibrary.utils.ToastUtils;

import androidx.annotation.Nullable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/8/5/005
 * 版权：
 */
@Route(path = AppArouter.ORDER_SCAN_ACTIVITY)
public class ScanActivity extends BaseActivity {

    public static final int REQUEST_IMAGE = 112;
    private CaptureFragment captureFragment;

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_scan_activity;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.order_home_my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            ScanActivity.this.setResult(RESULT_OK, resultIntent);

//            Intent intent = new Intent(ScanActivity.this, BindDeviceActivity.class);
//            intent.putExtra("sn_code", "4790072188");
//            intent.putExtra("is_bind", "bind_sn");
//            startActivity(intent);
//            ScanActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {

            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            ScanActivity.this.setResult(RESULT_OK, resultIntent);
            ScanActivity.this.finish();
        }

        @Override
        public void onRightClick() {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE);
        }

        @Override
        public void onFlashLight(boolean flashLight) {


            CodeUtils.isLightEnable(flashLight);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
        if (data != null) {
            Uri uri = data.getData();
            try {
                CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                    @Override
                    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                        Intent resultIntent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
                        bundle.putString(CodeUtils.RESULT_STRING, result);
                        resultIntent.putExtras(bundle);
                        ScanActivity.this.setResult(RESULT_OK, resultIntent);
                        ScanActivity.this.finish();

                    }

                    @Override
                    public void onAnalyzeFailed() {
//                            Toast.makeText(ScanActivity.this, "解析二维码失败", Toast.LENGTH_SHORT).show();
                        ToastUtils.showToast(ScanActivity.this,"解析二维码失败");
                    }

                    // onRightClick


                    @Override
                    public void onRightClick() {

                    }

                    @Override
                    public void onFlashLight(boolean flashLight) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
//        if (requestCode == REQUEST_IMAGE) {
//            if (data != null) {
//                Uri uri = data.getData();
//                try {
//                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
//                        @Override
//                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//                            Intent resultIntent = new Intent();
//                            Bundle bundle = new Bundle();
//                            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
//                            bundle.putString(CodeUtils.RESULT_STRING, result);
//                            resultIntent.putExtras(bundle);
//                            ScanActivity.this.setResult(RESULT_OK, resultIntent);
//                            ScanActivity.this.finish();
//
//                        }
//
//                        @Override
//                        public void onAnalyzeFailed() {
////                            Toast.makeText(ScanActivity.this, "解析二维码失败", Toast.LENGTH_SHORT).show();
//                            Utils.showToast(ScanActivity.this,"解析二维码失败");
//                        }
//
//                        // onRightClick
//
//
//                        @Override
//                        public void onRightClick() {
//
//                        }
//
//                        @Override
//                        public void onFlashLight(boolean flashLight) {
//
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
