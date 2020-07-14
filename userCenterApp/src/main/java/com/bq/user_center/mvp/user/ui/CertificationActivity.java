package com.bq.user_center.mvp.user.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.ocr.ui.RecognizeService;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.user_center.R;
import com.bq.user_center.R2;
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
    }


    @OnClick({R2.id.iv_front, R2.id.iv_back, R2.id.iv_hand_id_card, R2.id.tv_check})
    public void onViewClicked(View view) {
        if (pictureUtils == null) {
            pictureUtils = new PictureViewUtils(this);
        }
        if(view.getId() == R.id.iv_front){
//            Intent intent = new Intent(this, CameraActivity.class);
//            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
//                    getSaveFile(getApplication()).getAbsolutePath());
//            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
//                    CameraActivity.CONTENT_TYPE_BANK_CARD);
//            startActivityForResult(intent, 111);

            Intent intent = new Intent(this, CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    getSaveFile(getApplication()).getAbsolutePath());
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
            startActivityForResult(intent, 111);


//            index = 0;
//            pictureUtils.chooseHeadImg();
        }else if(view.getId() == R.id.iv_back){
            index = 1;
            pictureUtils.chooseHeadImg();
        }else if(view.getId() == R.id.iv_hand_id_card){
            index = 2;
            pictureUtils.chooseHeadImg();
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
                Glide.with(this).load(getSaveFile(getApplicationContext()).getAbsolutePath()).into(mIvFront);
                RecognizeService.recBankCard(this, getSaveFile(getApplicationContext()).getAbsolutePath(),
                        new RecognizeService.ServiceListener() {
                            @Override
                            public void onResult(String result) {
                                if(result.contains("erro")){
                                    ToastUtils.showToast(CertificationActivity.this,result);
                                    return;
                                }
//                            mEtCardno.setText(result);
//                            mEtCardno.setSelection(result.length());
                            }
                        });
            }
        }


    }
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "abc.jpg");
        return file;
    }

}
