package com.clkj.user_center.mvp.address.ui;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.clkj.user_center.R;
import com.clkj.user_center.R2;
import com.clkj.user_center.mvp.address.presenter.AddressManagerPresenter;
import com.clkj.user_center.requset.bean.AddressInfo;
import com.bq.utilslib.AccountValidatorUtil;
import com.bq.utilslib.EditFormatUtils;
import com.bq.utilslib.KeyboardUtils;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.utils.provinces.CityUtils;
import com.fan.baseuilibrary.view.DeletableEditText;

import butterknife.BindView;
import butterknife.OnClick;
import skin.support.widget.SkinCompatCheckBox;

/**
 * 文件名：
 * 描述：添加或编辑地址页面
 * 作者：梁帆
 * 时间：2020/6/18
 * 版权：
 */
@Route(path = AppArouter.USER_CENTER_ADDRESS_OPTION)
public class AddressOptionActivity extends BaseActivity implements AddressBaseIView {
    public static final int ADDRESS_ADD = 1;
    public static final int ADDRESS_EDIT = 2;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.rlt_title)
    RelativeLayout mRltTitle;
    @BindView(R2.id.bt_add_address)
    Button mBtAddAddress;
    @BindView(R2.id.tv_provinece)
    TextView mTvProvinece;
    @BindView(R2.id.det_address)
    DeletableEditText mDetAddress;
    @BindView(R2.id.det_phone)
    DeletableEditText mDetPhone;
    @BindView(R2.id.det_name)
    DeletableEditText mDetName;
    @BindView(R2.id.rb_man)
    RadioButton mRbMan;
    @BindView(R2.id.rb_woman)
    RadioButton mRbWoman;
    @BindView(R2.id.switchView)
    SkinCompatCheckBox mSwitchView;

    @Autowired()
    int optionType;
    @Autowired
    AddressInfo mAddressInfo;

    private AddressManagerPresenter mAddressManagerPresenter;


    @Override
    protected BasePresenter createPersenter() {
        mAddressManagerPresenter = new AddressManagerPresenter(this);
        return mAddressManagerPresenter;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_activity_add_address;
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText(optionType == ADDRESS_ADD ? "新增地址" : "编辑地址");
        mTvProvinece.setTextColor(getResources().getColor(optionType == ADDRESS_ADD?R.color.ui_txt_hint_color:R.color.ui_txt_normal_color));
        mBtAddAddress.setText(optionType == ADDRESS_ADD ? "确定" : "保存");
        EditFormatUtils.phoneNumAddSpace(mDetPhone);
        if(optionType == ADDRESS_EDIT){
            updateView();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAddressManagerPresenter.unRegister();
    }

    void updateView(){
        mTvProvinece.setText(mAddressInfo.getCity());
        mDetAddress.setText(mAddressInfo.getAddress());
        mDetName.setText(mAddressInfo.getContacts());
        mDetPhone.setText(mAddressInfo.getPhone());

        mDetAddress.setClearDrawableVisible(false);
        mDetName.setClearDrawableVisible(false);
        mDetPhone.setClearDrawableVisible(false);

        String gender = mAddressInfo.getGender();
        if("man".equals(gender)){
            mRbMan.setChecked(true);
        }else{
            mRbWoman.setChecked(true);
        }
        mSwitchView.setChecked(mAddressInfo.getIs_default()== 1?true:false);
    }


    @OnClick({R2.id.bt_add_address, R2.id.rlt_provinces, R2.id.rb_man, R2.id.rb_woman, R2.id.switchView})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.bt_add_address) {
            String city = mTvProvinece.getText().toString();
            String address = mDetAddress.getText().toString();
            String name = mDetName.getText().toString();
            String phone = mDetPhone.getText().toString().replaceAll(" ","").trim();
            String gender = "";
            boolean isDefault = mSwitchView.isChecked();
            if (mRbMan.isChecked()) {
                gender = "man";
            } else {
                gender = "woman";
            }
            if (StringUtils.isEmpty(city)) {
                ToastUtils.showToast(this, "请选择地区");
                return;
            }
            if (StringUtils.isEmpty(address)) {
                ToastUtils.showToast(this, "请填写详细地址");
                return;
            }
            if (StringUtils.isEmpty(city)) {
                ToastUtils.showToast(this, "请填写联系人");
                return;
            }
            if(StringUtils.isEmpty(phone)){
                ToastUtils.showToast(this, "请填写手机号");
                return;
            }
            if(!AccountValidatorUtil.isMobile(phone)){
                ToastUtils.showToast(this, "请填写正确的手机号");
                return;
            }
            AddressInfo info = new AddressInfo(name, gender, phone, city, address, isDefault ? 1 : 0);
            if(optionType == ADDRESS_ADD){
                mAddressManagerPresenter.addAddress(info);
            }else{
                mAddressManagerPresenter.updateAddress(mAddressInfo.getId(),info);
            }


        } else if (view.getId() == R.id.rlt_provinces) {
            String arestr = mTvProvinece.getText().toString().trim();
            KeyboardUtils.hideSoftInput(this);
            CityUtils.getInstance(this).showPickerView(this, new CityUtils.CityCallBack() {
                @Override
                public void getCitys(String citys) {
                    mTvProvinece.setTextColor(getResources().getColor(R.color.ui_txt_normal_color));
                    mTvProvinece.setText(citys);
                }
            }, arestr);
        }
    }

    @Override
    public void updateAddress() {
        ToastUtils.showToastOk(this,"修改成功");
        new Handler().postDelayed(()->{
            finish();
        },1000);
    }

    @Override
    public void addAdress() {
        ToastUtils.showToastOk(this, "添加成功");
        new Handler().postDelayed(() -> {
            finish();
        }, 1000);
    }


}
