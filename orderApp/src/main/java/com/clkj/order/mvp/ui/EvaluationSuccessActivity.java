package com.clkj.order.mvp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.clkj.order.R;
import com.clkj.order.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述： 支付成功
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
@Route(path = AppArouter.ORDER_EVALUATION_SUCCESS_ACTIVITY)
public class EvaluationSuccessActivity extends BaseActivity {


    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_goback)
    TextView mTvGoback;
    @BindView(R2.id.tv_evaluation_detail)
    TextView mTvEvaluationDetail;

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_evaluation_success;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("支付成功");
    }


    @OnClick({R2.id.tv_goback, R2.id.tv_evaluation_detail})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_goback) {
            finish();
        } else if (view.getId() == R.id.tv_evaluation_detail) {
            ARouter.getInstance().build(AppArouter.ORDER_MY_EVALUATION_ACTIVITY)
                    .navigation();
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
