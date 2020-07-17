package com.bq.user_center.mvp.user.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
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
import com.bq.utilslib.AccountValidatorUtil;
import com.bq.utilslib.AppUtils;
import com.bq.utilslib.rsa.RSA;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.utils.ToastUtils;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
                    chooseSex(module);
                    return;
                }else if("education".equals(type)){
                    chooseEducation(module);
                    return;
                }else if("birthday".equals(type)){
                    chooseBirthday(module);
                    return;
                }else if("phone".equals(type)){
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
        if(!StringUtils.isEmpty(module.getValue())){
            etValue.setText(module.getValue());
        }
        new CustomDialog().showCustonViewDialog(this, view, "修改" + module.getName(), new CustomDialog.ClickCallBack() {
            @Override
            public void ok() {
                String text = etValue.getText().toString();
                String type = module.getType();
                if("email".equals(type)){
                    if(!AccountValidatorUtil.isEmail(text)){
                        ToastUtils.showToast(UserinfoActivity.this,"邮箱输入不正确！");
                        return;
                    }
                }
                mUserPresenter.updateUserInfo(module.getType(),text);
            }

            @Override
            public void cacel() {
            }
        });
    }


    void chooseSex(UserInfoConfigBean.ModuleListBean module) {
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
        String sexStr = module.getValue();
        if("女".equals(sexStr)){
            pvOptions.setSelectOptions(1);
        }else{
            pvOptions.setSelectOptions(0);
        }

        pvOptions.setPicker(dataList);


        pvOptions.show();
    }

    void chooseEducation(UserInfoConfigBean.ModuleListBean module){
        KeyboardUtils.hideSoftInput(this);
        List<String> dataList = new ArrayList<>();
        dataList.add("研究生");
        dataList.add("本科");
        dataList.add("专科");
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
        int index = 0;
        for (int i = 0; i <dataList.size() ; i++) {
            if(module.getValue().equals(dataList.get(i))){
                index = i;
                break;
            }
        }
        pvOptions.setSelectOptions(index);
        pvOptions.show();

    }

    //选择出生日期
    void chooseBirthday(UserInfoConfigBean.ModuleListBean module){
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        selectedDate.set(1990, 1, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.set(1970, 1, 1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2069, 2, 28);
        TimePickerView pvTime = new TimePickerBuilder(this, (date, v) -> {//选中事件回调
            String s =new SimpleDateFormat("yyyy-MM-dd").format(date);
            mUserPresenter.updateUserInfo("birthday",s);
        }).setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setContentTextSize(18)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", null, null, null)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setSubmitColor(SkinCompatResources.getColor(this,R.color.ui_primary_color))
                .setCancelColor(SkinCompatResources.getColor(this,R.color.ui_primary_color))
                .build();

        String valueStr = module.getValue();
        if(!StringUtils.isEmpty(valueStr) && valueStr.contains("-")){
            String[] split = valueStr.split("-");
            Calendar instance = Calendar.getInstance();
            instance.set(Integer.valueOf(split[0]),Integer.valueOf(split[1])-1,Integer.valueOf(split[2]));
            pvTime.setDate(instance);
        }
//        pvTime.setDate();
        pvTime.show();
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
