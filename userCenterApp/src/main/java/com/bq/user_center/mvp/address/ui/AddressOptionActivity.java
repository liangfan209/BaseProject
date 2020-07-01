package com.bq.user_center.mvp.address.ui;

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
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.mvp.address.presenter.AddressManagerPresenter;
import com.bq.user_center.requset.bean.AddressInfo;
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
public class AddressOptionActivity extends BaseAcitivty implements AddressBaseIView {
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
    protected BasePersenter createPersenter() {
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
        mBtAddAddress.setText(optionType == ADDRESS_ADD ? "添加新地址" : "保存地址");


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
        EditFormatUtils.phoneNumAddSpace(mDetPhone);

        String gender = mAddressInfo.getGender();
        if("男".equals(gender)){
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
            String phone = mDetPhone.getText().toString();
            String gender = "";
            boolean isDefault = mSwitchView.isChecked();
            if (mRbMan.isChecked()) {
                gender = mRbMan.getText().toString();
            } else {
                gender = mRbWoman.getText().toString();
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
