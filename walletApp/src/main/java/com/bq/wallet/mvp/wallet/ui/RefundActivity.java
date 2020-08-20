package com.bq.wallet.mvp.wallet.ui;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.utilslib.KeyboardUtils;
import com.bq.wallet.R;
import com.bq.wallet.R2;
import com.fan.baseuilibrary.view.CustomPopWindow;
import com.fan.baseuilibrary.view.DeletableEditText;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = AppArouter.WALLET_REFUND_ACTIVITY)
public class RefundActivity extends BaseActivity implements WalletIView {


    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_right)
    TextView mTvRight;
    @BindView(R2.id.view_line)
    View mViewLine;
    @BindView(R2.id.rlt_title)
    RelativeLayout mRltTitle;
    @BindView(R2.id.det_refund_name)
    DeletableEditText mDetRefundName;
    @BindView(R2.id.det_refund_phone)
    DeletableEditText mDetRefundPhone;
    @BindView(R2.id.tv_reason)
    TextView mTvReason;
    @BindView(R2.id.rlt_refund_reason)
    RelativeLayout mRltRefundReason;
    @BindView(R2.id.det_refund_remark)
    DeletableEditText mDetRefundRemark;
    @BindView(R2.id.bt_refund)
    Button mBtRefund;

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


    @OnClick({R2.id.rlt_refund_reason, R2.id.bt_refund})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.rlt_refund_reason){
            showPopWindow();
        }
    }

    private void showPopWindow() {
        KeyboardUtils.hideSoftInput(this);
        View view = getLayoutInflater().inflate(R.layout.wallet_layout_refund_remak, null);
        final CustomPopWindow mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .size(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .setView(view)
                .enableBackgroundDark(true)
                .setAnimationStyle(R.style.public_dialog_inout_anim)
                .create()
                .showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }
}
