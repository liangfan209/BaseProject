package com.bq.user_center.mvp.user.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.bq.comm_config_lib.BaseApplication;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.api.bean.UserInfoConfigBean;
import com.bq.user_center.mvp.user.presenter.UserPresenter;
import com.bq.user_center.requset.bean.UserInfo;
import com.bq.utilslib.AppUtils;
import com.bq.utilslib.rsa.RSA;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.CircleImageView;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.fan.baseuilibrary.view.PictureViewUtils;
import com.fan.baseuilibrary.view.dialog.CustomDialog;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import skin.support.content.res.SkinCompatResources;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/24
 * 版权：
 */
@Route(path = AppArouter.USER_CENTER_USER_INFO_ACTIVITY)
public class UserinfoActivity extends BaseAcitivty implements UserBaseIView{
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.rlt_title)
    RelativeLayout mRltTitle;
    @BindView(R2.id.civ_header)
    CircleImageView mCivHeader;
    @BindView(R2.id.rlt_head)
    RelativeLayout mRltHead;
    @BindView(R2.id.llt_content)
    LinearLayout mLltContent;

    @BindView(R2.id.rcyView)
    RecyclerView mRcyView;

    private UserInfoConfigBean mUserInfoConfigBean;
    BaseQuickAdapter mBaseQuickAdapter;
    PictureViewUtils pictureUtils;

    private UserPresenter mUserPresenter;
    OkHttpClient mOkHttpClient;

    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_activity_userinfo;
    }

    @Override
    protected BasePresenter createPersenter() {
        mUserPresenter = new UserPresenter(this);
        return mUserPresenter;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("个人信息");
        String jsonStr = AppUtils.getAssetJson(this, "usercenter_userinfo_config.json");
        mUserInfoConfigBean = new Gson().fromJson(jsonStr, UserInfoConfigBean.class);
        updateView();
        mUserPresenter.showUserInfo();
        mOkHttpClient = new OkHttpClient();
    }

    @Override
    public void showUser(UserInfo info) {
        UserInfo.CustomerInfoBean customerInfo = info.getCustomer_info();
        List<UserInfoConfigBean.ModuleListBean> moduleList = mUserInfoConfigBean.getModuleList();
        for (UserInfoConfigBean.ModuleListBean moduleListBean : moduleList) {
            if(moduleListBean.getType().equals("name")){
                moduleListBean.setValue(customerInfo.getName());
            }else if(moduleListBean.getType().equals("gender")){
                moduleListBean.setValue(customerInfo.getGender());
            }else if(moduleListBean.getType().equals("birthday")){
                moduleListBean.setValue(customerInfo.getBirthday());
            }else if(moduleListBean.getType().equals("phone")){
                moduleListBean.setValue(customerInfo.getPhone());
            }else if(moduleListBean.getType().equals("email")){
                moduleListBean.setValue(customerInfo.getEmail());
            }else if(moduleListBean.getType().equals("qq")){
                moduleListBean.setValue(customerInfo.getQq());
            }else if(moduleListBean.getType().equals("education")){
                moduleListBean.setValue(customerInfo.getEducation());
            }else if(moduleListBean.getType().equals("wechat")){
                moduleListBean.setValue(customerInfo.getWechat());
            }
        }
        mBaseQuickAdapter.notifyDataSetChanged();
    }

    private void updateView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRcyView.setLayoutManager(linearLayoutManager);
        mBaseQuickAdapter =
                new BaseQuickAdapter<UserInfoConfigBean.ModuleListBean, BaseViewHolder>(R.layout.user_center_item_user_info,
                        mUserInfoConfigBean.getModuleList()) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper, UserInfoConfigBean.ModuleListBean moduleListBean) {
                        helper.setText(R.id.tv_name, moduleListBean.getName());
                        helper.setText(R.id.tv_value, moduleListBean.getValue());
                        boolean hasInterval = moduleListBean.isHasInterval();
                        View interValView = helper.getView(R.id.iv_interval);
                        interValView.setVisibility(hasInterval ? View.VISIBLE : View.GONE);
                    }
                };
        mRcyView.setAdapter(mBaseQuickAdapter);
        mBaseQuickAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                UserInfoConfigBean.ModuleListBean module = (UserInfoConfigBean.ModuleListBean) adapter.getData().get(position);
                String type = module.getType();
                if ("gender".equals(type)) {
                    chooseSex();
                    return;
                }else if("education".equals(type)){
                    chooseEducation();
                    return;
                }
                changeValueDialog(module);
            }
        });
    }

    /**
     * 弹出对话框，修改用户资料
     * @param module
     */
    void changeValueDialog(UserInfoConfigBean.ModuleListBean module) {
        View view = LinearLayout.inflate(this, R.layout.user_center_input_view, null);
        final DeletableEditText etValue = view.findViewById(R.id.et_value);
        etValue.setHint("请输入" + module.getName());
        new CustomDialog().showCustonViewDialog(this, view, "修改" + module.getName(), new CustomDialog.ClickCallBack() {
            @Override
            public void ok() {
                String text = etValue.getText().toString();
                mUserPresenter.updateUserInfo(module.getType(),text);
            }

            @Override
            public void cacel() {
            }
        });
    }


    void chooseSex() {
        KeyboardUtils.hideSoftInput(this);
        List<String> dataList = new ArrayList<>();
        dataList.add("男");
        dataList.add("女");
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String s = dataList.get(options1);
                mUserPresenter.updateUserInfo("gender",s);
            }
        }).setSubmitColor(SkinCompatResources.getColor(this,R.color.ui_primary_color))
                .setCancelColor(SkinCompatResources.getColor(this,R.color.ui_primary_color)).build();
        pvOptions.setPicker(dataList);
        pvOptions.show();
    }

    void chooseEducation(){
        KeyboardUtils.hideSoftInput(this);
        List<String> dataList = new ArrayList<>();
        dataList.add("研究生");
        dataList.add("本科");
        dataList.add("高中");
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String s = dataList.get(options1);
                mUserPresenter.updateUserInfo("education",s);
            }
        }).setSubmitColor(SkinCompatResources.getColor(this,R.color.ui_primary_color))
                .setCancelColor(SkinCompatResources.getColor(this,R.color.ui_primary_color)).build();
        pvOptions.setPicker(dataList);
        pvOptions.show();
    }

    //选择出生日期
    void chooseBirthday(){
//        OptionsPickerBuilder pvTime = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//
//            }
//        })
//                .setCancelText("Cancel")//取消按钮文字
//                .setSubmitText("Sure")//确认按钮文字
//                .setTitleText("Title")//标题文字
//                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                .setDate(new Date())// 默认是系统时间*/
//                .setLabel("年","月","日")
//                .build();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureViewUtils.TAKE_PHOTO_CODE) {
                //处理拍照返回结果
                pictureUtils.startPhotoCrop(pictureUtils.mImageUri);
            } else if (requestCode == PictureViewUtils.CROP_PHOTO) {
                //处理裁剪返回结果
                Glide.with(this).load(pictureUtils.mCropImgUri).into(mCivHeader);
                try {
                    File file = new File(new URI(pictureUtils.mCropImgUri.toString()));
                    uploadStart(file);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == pictureUtils.SELECT_PIC_CODE) {
                //处理从相册返回结果
                if (data != null) {
                    pictureUtils.startPhotoCrop(data.getData());
                }
            }
        }
    }


    @OnClick({R2.id.civ_header, R2.id.rlt_head})
    public void onViewClicked(View view) {
        if (pictureUtils == null) {
            pictureUtils = new PictureViewUtils(this);
        }
        pictureUtils.chooseHeadImg();
    }



    private void uploadStart(File file) {
        new Thread(() -> {
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

            System.out.println();


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
//                UploadImgBean uploadBean = new Gson().fromJson(responseBody,  UploadImgBean.class);
//                if ("10000".equals(uploadBean.getCode())) {
//                    String result = uploadBean.getResult();
//                    toMainTheardSaveUser(result);
//                    //将结果上传到服务器
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


}
