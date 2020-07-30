package com.bq.user_center.mvp.user.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.request.Api;
import com.bq.comm_config_lib.request.upload.UploadBean;
import com.bq.comm_config_lib.request.upload.UploadHelper;
import com.bq.comm_config_lib.utils.ScanHelper;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.mvp.user.presenter.UserPresenter;
import com.bq.user_center.requset.bean.CertificationInfo;
import com.bq.utilslib.EditFormatUtils;
import com.bumptech.glide.Glide;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.fan.baseuilibrary.view.PictureViewUtils;
import com.google.gson.Gson;

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
public class CertificationActivity extends BaseActivity implements UserBaseIView{
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

    @Autowired
    int isCertication;

    private UserPresenter mUserPresenter;

    PictureViewUtils pictureUtils;
    private int index = 0;
    private ScanHelper mScanHelper;

    private String frontImg = "";
    private String backImg = "";
    private String handImg = "";




    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_activity_certification;
    }

    @Override
    protected BasePresenter createPersenter() {
        mUserPresenter = new UserPresenter(this);
        return mUserPresenter;
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("实名认证");
//        EditFormatUtils.idCardAddSpace(mDetId);
        //给scanHelper绑定生命周期
        mScanHelper = new ScanHelper(this);
        getLifecycle().addObserver(mScanHelper);

        if(isCertication == 1){
            mIvScan.setVisibility(View.GONE);
            mDetName.setEnabled(false);
            mDetId.setEnabled(false);
            mIvFront.setEnabled(false);
            mIvBack.setEnabled(false);
            mDetId.setTextColor(getResources().getColor(R.color.ui_txt_hint_color));
            mDetName.setTextColor(getResources().getColor(R.color.ui_txt_hint_color));
            mIvHandIdCard.setEnabled(false);
            mUserPresenter.getCertification();
            mTvCheck.setVisibility(View.GONE);
        }else{
            EditFormatUtils.idCardAddSpace(mDetId);
        }
    }

    @Override
    public void getCertificationView(CertificationInfo info) {
        mDetName.setText(info.getName());
        mDetId.setText(info.getIdentification());
        mDetName.setClearDrawableVisible(false);
        mDetId.setClearDrawableVisible(false);
        Glide.with(this).load(Api.BASE_API+info.getId_front()).into(mIvFront);
        Glide.with(this).load(Api.BASE_API+info.getId_back()).into(mIvBack);
        Glide.with(this).load(Api.BASE_API+info.getId_in_hand()).into(mIvHandIdCard);

    }

    @OnClick({R2.id.iv_front, R2.id.iv_back, R2.id.iv_hand_id_card, R2.id.tv_check,R2.id.iv_scan})
    public void onViewClicked(View view) {
        if (pictureUtils == null) {
            pictureUtils = new PictureViewUtils(this);
        }
        KeyboardUtils.hideSoftInput(this);
        if(view.getId() == R.id.iv_front){
            index = 0;
            if (!Utils.isFastDoubleClick(mIvFront, 500)) {
//                pictureUtils.chooseHeadImg();
//                mScanHelper.startScan(ScanHelper.ID_CARD_FRONT);
                mScanHelper.startScan(ScanHelper.BANK_CARD);
            }

        }else if(view.getId() == R.id.iv_back){
            index = 1;
            if (!Utils.isFastDoubleClick(mIvBack, 500)) {
//                pictureUtils.chooseHeadImg();
//                mScanHelper.startScan(ScanHelper.ID_CARD_BACK);
                mScanHelper.startScan(ScanHelper.BANK_CARD);
            }
        }else if(view.getId() == R.id.iv_hand_id_card){
            index = 2;
            if (!Utils.isFastDoubleClick(mIvHandIdCard, 500)) {
                mScanHelper.startScan(ScanHelper.BANK_CARD);
            }
        } else if(view.getId() == R.id.iv_scan){
            if (!Utils.isFastDoubleClick(mIvScan, 1000)) {
                mScanHelper.startScan(ScanHelper.ID_CARD_FRONT);
            }

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

            if(StringUtils.isEmpty(frontImg)){
                ToastUtils.showToast(this,"请上传正面身份证");
                return;
            }
            if(StringUtils.isEmpty(backImg)){
                ToastUtils.showToast(this,"请上传背面身份证");
                return;
            }
            if(StringUtils.isEmpty(handImg)){
                ToastUtils.showToast(this,"请上传手持身份证");
                return;
            }

            mUserPresenter.certification(name,idNum,frontImg,backImg,handImg);
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
                uploadImage(pictureUtils.mCropImgUri,null);
            } else if (requestCode == pictureUtils.SELECT_PIC_CODE) {
                //处理从相册返回结果
                if (data != null) {
                    pictureUtils.startPhotoRectCrop(data.getData());
                }
            } else if(requestCode == 111){
                final String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                mScanHelper.paseData(data,ScanHelper.ID_CARD_FRONT,new ScanHelper.IdCardInter(){
                    @Override
                    public void idCardCallBack(IDCardResult result) {
                        if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                            String idNumber = result.getIdNumber().toString();
                            String name = result.getName().toString();
                            if(StringUtils.isEmpty(idNumber) && StringUtils.isEmpty(name)){
                                ToastUtils.showToast(CertificationActivity.this,"识别失败");
                                return;
                            }

                            mDetId.setText(idNumber);
                            mDetName.setText(result.getName().toString());
                            mDetId.setClearDrawableVisible(false);
                            mDetName.setClearDrawableVisible(false);
                        } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)){
                            //身份证背面
                            String words = result.getSignDate().getWords();
                            if(StringUtils.isEmpty(words)){
                                ToastUtils.showToast(CertificationActivity.this,"识别失败");
                                return;
                            }
                        }

//                        String filePath = mScanHelper.getSaveFile(CertificationActivity.this.getApplicationContext()).getAbsolutePath();
//                        uploadImage(null,filePath);

                    }
                    @Override
                    public void error(String msg) {
                        ToastUtils.showToast(CertificationActivity.this,msg);
                    }
                });
            }else if(requestCode == 112){
                String filePath = mScanHelper.getSaveFile(CertificationActivity.this.getApplicationContext()).getAbsolutePath();
                uploadImage(null,filePath);
            }
        }
    }

    private void uploadImage(Uri cropImgUri,String filePath) {
        try {
            File file = null;
            if(StringUtils.isEmpty(filePath)){
                file  = new File(new URI(pictureUtils.mCropImgUri.toString()));
            }else{
                file = new File(filePath);
            }
            UploadHelper.getInstance().uploadStart(file,new UploadHelper.CallBackInter(){
                @Override
                public void callBack(String str) {
                    UploadBean uploadBean = new Gson().fromJson(str,  UploadBean.class);
                    String status = uploadBean.getStatus();
                    if("ok".equals(status)){
                        if(index == 0){
                            frontImg = uploadBean.getResult().getFile_paths().get(0);
                            Glide.with(CertificationActivity.this).load(Api.BASE_API+frontImg).into(mIvFront);
                        }else if(index == 1){
                            backImg = uploadBean.getResult().getFile_paths().get(0);
                            Glide.with(CertificationActivity.this).load(Api.BASE_API+backImg).into(mIvBack);
                        }else if(index == 2){
                            handImg = uploadBean.getResult().getFile_paths().get(0);
                            Glide.with(CertificationActivity.this).load(Api.BASE_API+handImg).into(mIvHandIdCard);
                        }
                    }else{
                        ToastUtils.showToast(CertificationActivity.this,"上传失败");
                    }
                }
                @Override
                public void error() {
                    ToastUtils.showToast(CertificationActivity.this,"上传失败");
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void certificationView() {
        ToastUtils.showToastOk(this,"实名认证成功");
        new Handler().postDelayed(()->{
            finish();
        },1000);
    }

    @Override
    protected void onDestroy() {
        // 释放本地质量控制模型
        CameraNativeHelper.release();
        super.onDestroy();
    }

}
