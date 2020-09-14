package com.clkj.wallet.mvp.wallet.ui;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.clkj.wallet.R;

@Route(path = AppArouter.WALLET_REFUND_PROCESS_ACTIVITY)
public class RefundProcessActivity extends BaseActivity implements WalletIView {



    @Override
    protected int getContentViewLayout() {
        return R.layout.wallet_activity_refund;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {

    }

}
