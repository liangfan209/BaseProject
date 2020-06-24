package com.bq.user_center.mvp.address.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.mvp.address.presenter.AddressOptionPresenter;
import com.bq.utilslib.KeyboardUtils;
import com.fan.baseuilibrary.utils.provinces.CityUtils;
import com.fan.baseuilibrary.view.DeletableEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Autowired()
    int optionType;
    @BindView(R2.id.det_address)
    DeletableEditText mDetAddress;
    @BindView(R2.id.det_name)
    DeletableEditText mDetName;
    @BindView(R2.id.rb_man)
    RadioButton mRbMan;
    @BindView(R2.id.rb_woman)
    RadioButton mRbWoman;
    @BindView(R2.id.switchView)
    Switch mSwitchView;

    private AddressOptionPresenter mAddressOptionPresenter;


    @Override
    protected BasePersenter createPersenter() {
        mAddressOptionPresenter = new AddressOptionPresenter(this);
        return mAddressOptionPresenter;
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
    }

    @OnClick({R2.id.bt_add_address, R2.id.rlt_provinces,R2.id.rb_man, R2.id.rb_woman, R2.id.switchView})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.bt_add_address) {
            String name = mDetName.getText().toString();
            String address = mDetAddress.getText().toString();
            int sex = 0;
            if(mRbMan.isChecked()){
            }
            mAddressOptionPresenter.addAddress(null);
        } else if (view.getId() == R.id.rlt_provinces) {
            String arestr = mTvProvinece.getText().toString().trim();
            KeyboardUtils.hideSoftInput(this);
            CityUtils.getInstance(this).showPickerView(this, new CityUtils.CityCallBack() {
                @Override
                public void getCitys(String citys) {
                    mTvProvinece.setText(citys);
                }
            }, arestr);
        } else if(view.getId() == R.id.rb_man){
        } else if(view.getId() == R.id.rb_woman){
        } else if(view.getId() == R.id.switchView){
        }
    }

    @Override
    public void addAdress() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
