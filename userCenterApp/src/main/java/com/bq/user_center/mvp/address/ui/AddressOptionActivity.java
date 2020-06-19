package com.bq.user_center.mvp.address.ui;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.user_center.R;
import com.bq.user_center.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：添加或编辑地址页面
 * 作者：梁帆
 * 时间：2020/6/18
 * 版权：
 */
@Route(path = AppArouter.USER_CENTER_ADDRESS_OPTION)
public class AddressOptionActivity extends BaseAcitivty {
    public static final int ADDRESS_ADD = 1;
    public static final int ADDRESS_EDIT = 2;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.rlt_title)
    RelativeLayout mRltTitle;
    @BindView(R2.id.bt_add_address)
    Button mBtAddAddress;

    @Autowired()
    int optionType;


    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_activity_add_address;
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText(optionType== ADDRESS_ADD?"新增地址":"编辑地址");
        mBtAddAddress.setText(optionType== ADDRESS_ADD?"添加新地址":"保存地址");
    }

    @OnClick(R2.id.bt_add_address)
    public void onViewClicked(View view) {
        if(view.getId() == R.id.bt_add_address){
        }
    }
}
