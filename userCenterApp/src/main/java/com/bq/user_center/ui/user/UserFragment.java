package com.bq.user_center.ui.user;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.ui.BaseFragment;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.bean.UserInfo;
import com.bq.user_center.mvp.user.UserIView;
import com.bq.user_center.mvp.user.UserPersenter;
import com.fan.baseuilibrary.view.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/10
 * 版权：
 */
@Route(path = AppArouter.USER_CENTER_USER_FRAGMENT)
public class UserFragment extends BaseFragment implements UserIView {

    
    @BindView(R2.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R2.id.tv_nick_name)
    TextView mTvNickName;
    @BindView(R2.id.rlt_head)
    RelativeLayout mRltHead;
    @BindView(R2.id.tv_name)
    TextView mTvName;
    @BindView(R2.id.rlt_user_identification)
    RelativeLayout mRltUserIdentification;
    @BindView(R2.id.tv_bank_type)
    TextView mTvBankType;
    @BindView(R2.id.rlt_bank)
    RelativeLayout mRltBank;

    private UserPersenter mUserPersenter;
    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_fragment_user;
    }

    @Override
    protected void attach() {
        //获取用户信息
        mUserPersenter = new UserPersenter(this);
        mUserPersenter.showUserInfo();

    }


    @OnClick({R2.id.rlt_head, R2.id.rlt_user_identification, R2.id.rlt_bank})
    public void onViewClicked(View view) {
        if(view.getId() ==  R.id.rlt_bank){
            ARouter.getInstance().build(AppArouter.USER_CENTER_BANK_LIST).navigation();
        }
    }

    @Override
    public void showUser(UserInfo info) {
        mTvNickName.setText(info.getUser_nick());
    }
}
