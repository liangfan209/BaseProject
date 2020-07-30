package com.bq.order.mvp.wallet.ui;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.wallet.presenter.WalletPresenter;

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
@Route(path = AppArouter.WALLET_MY_ACTIVITY)
public class MyWalletActivity extends BaseActivity implements WalletIView {
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_right)
    TextView mTvRight;
    @BindView(R2.id.tv_take_cash)
    TextView mTvTakeCash;
    @BindView(R2.id.view_line)
    View mViewLine;
    @BindView(R2.id.rlt_title)
    RelativeLayout mRltTitle;
    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;

    @BindView(R2.id.tv_momey_left)
    TextView mTvMoneyLeft;
    @BindView(R2.id.tv_money_right)
    TextView mTvMoneyRight;


    WalletListFragment mFragment;

    private WalletPresenter mWalletPresenter;

    @Override
    protected int getContentViewLayout() {
        return R.layout.wallet_activity_my_wallet;
    }

    @Override
    protected BasePresenter createPersenter() {
        mWalletPresenter = new WalletPresenter(this);
        return mWalletPresenter;
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
        mWalletPresenter.getBalance();
    }

    @Override
    public void getBalanceView(long balance) {
        String moneyStr = Utils.getDouble2(balance);
        String moneyLeftStr = moneyStr.substring(0,moneyStr.indexOf("."));
        String moneyRightStr = moneyStr.substring(moneyStr.indexOf("."));
        mTvMoneyLeft.setText(moneyLeftStr);
        mTvMoneyRight.setText(moneyRightStr);
    }

    @OnClick({R2.id.tv_title, R2.id.tv_right, R2.id.tv_take_cash})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.tv_right){
            ARouter.getInstance().build(AppArouter.WALLET_RECHARGE_ACTIVITY).navigation();
        }else if(view.getId() == R.id.tv_take_cash){
            ARouter.getInstance().build(AppArouter.WALLET_TACK_CASH_ACTIVITY).navigation();
        }
    }
}
