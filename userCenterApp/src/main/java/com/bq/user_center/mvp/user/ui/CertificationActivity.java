package com.bq.user_center.mvp.user.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.utilslib.EditFormatUtils;
import com.bumptech.glide.Glide;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.fan.baseuilibrary.view.PictureViewUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/13
 * 版权：
 */
@Route(path = AppArouter.USER_CENTER_CETIFICATION_ACTIVITY)
public class CertificationActivity extends BaseAcitivty {
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_right)
    TextView mTvRight;
    @BindView(R2.id.view_line)
    View mViewLine;
    @BindView(R2.id.rlt_title)
    RelativeLayout mRltTitle;
    @BindView(R2.id.det_name)
    DeletableEditText mDetName;
    @BindView(R2.id.det_id)
    DeletableEditText mDetId;
    @BindView(R2.id.iv_front)
    ImageView mIvFront;
    @BindView(R2.id.iv_back)
    ImageView mIvBack;
    @BindView(R2.id.iv_hand_id_card)
    ImageView mIvHandIdCard;
    @BindView(R2.id.tv_check)
    TextView mTvCheck;
    @BindView(R2.id.iv_scan)
    ImageView mIvScan;

    PictureViewUtils pictureUtils;
    private int index = 0;

    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_activity_certification;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("实名认证");
        EditFormatUtils.idCardAddSpace(mDetId);

        //  初始化本地质量控制模型,释放代码在onDestory中
        //  调用身份证扫描必须加上 intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true); 关闭自动初始化和释放本地模型
        CameraNativeHelper.init(this, OCR.getInstance(this).getLicense(),
                new CameraNativeHelper.CameraNativeInitCallback() {
                    @Override
                    public void onError(int errorCode, Throwable e) {
                        String msg;
                        switch (errorCode) {
                            case CameraView.NATIVE_SOLOAD_FAIL:
                                msg = "加载so失败，请确保apk中存在ui部分的so";
                                break;
                            case CameraView.NATIVE_AUTH_FAIL:
                                msg = "授权本地质量控制token获取失败";
                                break;
                            case CameraView.NATIVE_INIT_FAIL:
                                msg = "本地质量控制";
                                break;
                            default:
                                msg = String.valueOf(errorCode);
                        }
                    }
                });
    }


    @OnClick({R2.id.iv_front, R2.id.iv_back, R2.id.iv_hand_id_card, R2.id.tv_check,R2.id.iv_scan})
    public void onViewClicked(View view) {
        if (pictureUtils == null) {
            pictureUtils = new PictureViewUtils(this);
        }
        if(view.getId() == R.id.iv_front){
            index = 0;
            pictureUtils.chooseHeadImg();
        }else if(view.getId() == R.id.iv_back){
            index = 1;
            pictureUtils.chooseHeadImg();
        }else if(view.getId() == R.id.iv_hand_id_card){
            index = 2;
            pictureUtils.chooseHeadImg();
        } else if(view.getId() == R.id.iv_scan){
            Intent intent = new Intent(this, CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    getSaveFile(getApplication()).getAbsolutePath());
            intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                    true);
            // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
            // 请手动使用CameraNativeHelper初始化和释放模型
            // 推荐这样做，可以避免一些activity切换导致的不必要的异常
            intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                    true);
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
            startActivityForResult(intent, 111);

        }else if(view.getId() == R.id.tv_check){
            String name = mDetName.getText().toString();
            String idNum = mDetId.getText().toString().replaceAll(" ","");
            if(StringUtils.isEmpty(name)){
                ToastUtils.showToast(this,"请输入姓名");
                return;
            }
            if(StringUtils.isEmpty(idNum)){
                ToastUtils.showToast(this,"请输入身份证号");
                return;
            }
            ToastUtils.showToastOk(this,"认证成功");
            new Handler().postDelayed(()->{
                finish();
            },1000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureViewUtils.TAKE_PHOTO_CODE) {
                //处理拍照返回结果
                pictureUtils.startPhotoRectCrop(pictureUtils.mImageUri);
            } else if (requestCode == PictureViewUtils.CROP_PHOTO) {
                //处理裁剪返回结果
                if(index == 0){
                    Glide.with(this).load(pictureUtils.mCropImgUri).into(mIvFront);
                }else if(index == 1){
                    Glide.with(this).load(pictureUtils.mCropImgUri).into(mIvBack);
                }else if(index == 2){
                    Glide.with(this).load(pictureUtils.mCropImgUri).into(mIvHandIdCard);
                }
                try {
                    File file = new File(new URI(pictureUtils.mCropImgUri.toString()));
//                    uploadStart(file);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == pictureUtils.SELECT_PIC_CODE) {
                //处理从相册返回结果
                if (data != null) {
                    pictureUtils.startPhotoRectCrop(data.getData());

                }
            } else if(requestCode == 111){
//                Glide.with(this).load(getSaveFile(getApplicationContext()).getAbsolutePath()).into(mIvFront);
                if (data != null) {
                    String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                    String filePath = getSaveFile(getApplicationContext()).getAbsolutePath();
                    if (!TextUtils.isEmpty(contentType)) {
                        if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                            recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                        } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                            recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                        }
                    }
                }
            }
        }


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
        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    String idNumber = result.getIdNumber().toString();
                    mDetId.setText(idNumber);
                }
            }
            @Override
            public void onError(OCRError error) {
            }
        });
    }
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "234.jpg");
        return file;
    }

    @Override
    protected void onDestroy() {
        // 释放本地质量控制模型
        CameraNativeHelper.release();
        super.onDestroy();
    }

}
