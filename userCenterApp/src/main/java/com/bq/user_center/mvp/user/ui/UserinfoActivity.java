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
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.api.bean.UserInfoConfigBean;
import com.bq.user_center.mvp.user.presenter.UserPresenter;
import com.bq.user_center.requset.bean.UserInfo;
import com.bq.utilslib.AppUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.CircleImageView;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.fan.baseuilibrary.view.UserPicture;
import com.fan.baseuilibrary.view.dialog.CustomDialog;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
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
    UserPicture pictureUtils;

    private UserPresenter mUserPresenter;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == UserPicture.TAKE_PHOTO_CODE) {
                //处理拍照返回结果
                pictureUtils.startPhotoCrop(pictureUtils.mImageUri);
            } else if (requestCode == UserPicture.CROP_PHOTO) {
                //处理裁剪返回结果
                Glide.with(this).load(pictureUtils.mCropImgUri).into(mCivHeader);
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
            pictureUtils = new UserPicture(this);
        }
        pictureUtils.chooseHeadImg();
    }
}
