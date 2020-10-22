package com.clkj.user_center.mvp.user.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.clkj.user_center.R;
import com.clkj.user_center.R2;
import com.clkj.user_center.requset.bean.MessageInfo;

import butterknife.BindView;

/**
 * 文件名：
 * 描述：院校专业详情
 * 作者：梁帆
 * 时间：2020/7/30
 * 版权：
 */
@Route(path = AppArouter.USER_CENTER_MESSAGE_DETAIL_ACTIVITY)
public class MessageDetailActivity extends BaseActivity {

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
    @BindView(R2.id.tv_msg_title)
    TextView mTvmsgTitle;
    @BindView(R2.id.tv_time)
    TextView mTvTime;
    @BindView(R2.id.tv_content)
    TextView mTvContent;

    @Autowired
    MessageInfo mMessageInfo;
    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_message_detail_activity;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("消息详情");
        mTvmsgTitle.setText(mMessageInfo.getTitle());
        mTvTime.setText("发布时间: "+mMessageInfo.getCreate_time());
        mTvContent.setText("    "+mMessageInfo.getContent());
    }

}
