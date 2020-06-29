package com.bq.walletapp.wallet.ui;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.walletapp.R;
import com.bq.walletapp.R2;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/29
 * 版权：
 */
@Route(path = AppArouter.WALLET_BILL_DETAIL_ACTIVITY)
public class BillDetailActivity extends BaseAcitivty {


    @Override
    protected int getContentViewLayout() {
        return R.layout.wallet_activity_bill_detail;
    }

    @Override
    protected BasePersenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("我的钱包");
        mTvRight.setText("充值");
        mTvRight.setVisibility(View.VISIBLE);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        mFragment = new WalletListFragment();
        fragmentTransaction.add(R.id.flt_content,mFragment).commit();
    }


    @OnClick({R2.id.tv_title, R2.id.flt_content})
    public void onViewClicked(View view) {
    }
}
