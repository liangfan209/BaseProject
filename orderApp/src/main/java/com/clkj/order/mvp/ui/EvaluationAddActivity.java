package com.clkj.order.mvp.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.request.upload.UploadBean;
import com.bq.comm_config_lib.request.upload.UploadHelper;
import com.bq.comm_config_lib.utils.Utils;
import com.clkj.order.R;
import com.clkj.order.api.OrderEventKey;
import com.clkj.order.mvp.presenter.OrderPresenter;
import com.clkj.order.requset.bean.EvaluationHttpInfo;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import per.wsj.library.AndRatingBar;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import skin.support.widget.SkinCompatRadioButton;

/**
 * 文件名：
 * 作者：梁帆
 * 时间：2020/8/6
 * 版权：
 */
@Route(path = AppArouter.ORDER_EVALUATION_ADD_ACTIVITY)
public class EvaluationAddActivity extends BaseActivity implements  EasyPermissions.PermissionCallbacks,BGASortableNinePhotoLayout.Delegate,OrderIview{


    private static final int PRC_PHOTO_PICKER = 1;
    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;
    private BGASortableNinePhotoLayout mPhotosSnpl;
    private TextView tv_feedback;
    private SkinCompatRadioButton rb1,rb2,rb3;
    AndRatingBar arb1,arb2,arb3;
    private DeletableEditText etFeedBack;
//    List<String> tags = new ArrayList<>();
    private String descritStr = "";
    private TextView tvTitle;
    private int server_attitude = -1;
    private int course_quality = -1;
    private int major = -1;


    @Autowired
    String orderId;

    private OrderPresenter mOrderPresenter;

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_evaluation_add_activity;
    }

    @Override
    protected BasePresenter createPersenter() {
        return mOrderPresenter = new OrderPresenter(this);
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mPhotosSnpl = findViewById(R.id.snpl_moment_add_photos);
        etFeedBack = findViewById(R.id.et_feedback);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("立即评价");
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
//        AndRatingBar arb1,arb2,arb3;
        arb1 = findViewById(R.id.rating1);
        arb2 = findViewById(R.id.rating2);
        arb3 = findViewById(R.id.rating3);

        arb1.setOnRatingChangeListener(new AndRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(AndRatingBar ratingBar, float rating) {
                server_attitude = (int) rating;
            }
        });
        arb2.setOnRatingChangeListener(new AndRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(AndRatingBar ratingBar, float rating) {
                course_quality = (int) rating;
            }
        });
        arb3.setOnRatingChangeListener(new AndRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(AndRatingBar ratingBar, float rating) {
                major = (int) rating;
            }
        });

        rb1.setChecked(true);
        tv_feedback = findViewById(R.id.tv_feedback);
        tv_feedback.setOnClickListener(v->{
            if(!Utils.isFastDoubleClick(tv_feedback,1000)){
                feedBack();
            }
        });
        mPhotosSnpl.setDelegate(this);
    }

    String typeStr = "";
    private void feedBack() {

        descritStr = etFeedBack.getText().toString();
        if(StringUtils.isEmpty(descritStr)){
            ToastUtils.showToast(this,"请输入评价");
            return;
        }

        if(descritStr.length()<4){
            ToastUtils.showToast(this,"请输入3个以上字符");
            return;
        }

        if(rb1.isChecked()){
            typeStr = "good_service";
        }
        if(rb2.isChecked()){
            typeStr = "course_all";
        }
        if(rb3.isChecked()){
            typeStr = "sale_guarantee";
        }


        if(server_attitude == 0){
            ToastUtils.showToast(this,"请选择态度服务");
            return;
        }else if(course_quality == 0){
            ToastUtils.showToast(this,"请选择课程质量");
            return;

        }else if(major ==0) {
            ToastUtils.showToast(this, "请选择院校专业");
            return;
        }
//        if(StringUtils.isEmpty(typeStr)){
//            ToastUtils.showToast(this,"请选择反馈类型");
//            return;
//        }
//        tags.add(typeStr);


        showLoading();
        ArrayList<String> selectedPhotos = mPhotosSnpl.getData();
        final ArrayList<String> hList = new ArrayList<>();
        for (String selectedPhoto : selectedPhotos) {
            byte[] bytes = FileIOUtils.readFile2BytesByStream(selectedPhoto);
            Bitmap bitmap = ImageUtils.bytes2Bitmap(bytes);
            byte[] compressByte = ImageUtils.compressByQuality(bitmap, 10);
            long longTime = System.currentTimeMillis();
            String internalAppDataPath = PathUtils.getInternalAppDataPath()+File.separator+"img"+File.separator+longTime+".JPG";
            FileUtils.createOrExistsFile(internalAppDataPath);
            FileIOUtils.writeFileFromBytesByChannel(internalAppDataPath,compressByte,true);
            File file = new File(internalAppDataPath);
            UploadHelper.getInstance().uploadStart(file, new UploadHelper.CallBackInter() {
                @Override
                public void callBack(String str) {
                    UploadBean uploadBean = new Gson().fromJson(str,  UploadBean.class);
                    String status = uploadBean.getStatus();
                    if("ok".equals(status)) {
                        hList.add(uploadBean.getResult().getFile_paths().get(0));
                    }
                }

                @Override
                public void error() {

                }
            },"evaluation");
        }
        new Handler().postDelayed(()->{
            EvaluationHttpInfo infoBean = new EvaluationHttpInfo(descritStr,server_attitude,course_quality,major);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hList.size(); i++) {
                if(i == 0){
                    sb.append("[");
                }
                sb.append("\"");
                sb.append(hList.get(i));
                sb.append("\"");
                if(i == hList.size() -1){
                    sb.append("]");
                }else{
                    sb.append(",");
                }
            }

            if(hList.size() > 0){
                infoBean.setPics(sb.toString());
            }
            infoBean.setTags("[\""+typeStr+"\"]");
            mOrderPresenter.evaluationAdd(new Gson().toJson(infoBean),orderId);
        },1000);
    }



    @Override
    public void feedbackView() {
//        ToastUtils.showToast(this,"操作成功");
        EventBus.getDefault().post(OrderEventKey.UPDATE_ORDER_STATUS);
        new Handler().postDelayed(()->{
            ARouter.getInstance().build(AppArouter.ORDER_EVALUATION_SUCCESS_ACTIVITY).navigation();
            finish();
        },1000);
    }

    @Override
    public void feedbackCompleView() {
        onComplete();
    }

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                    .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                    .maxChooseCount(mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount()) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position,
                                           String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model,
                                     ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(this)
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(mPhotosSnpl.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {
        Toast.makeText(this, "排序发生变化", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == PRC_PHOTO_PICKER) {
            Toast.makeText(this, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
            //将数据传入到服务器


        } else if (requestCode == RC_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
        }
    }

}
