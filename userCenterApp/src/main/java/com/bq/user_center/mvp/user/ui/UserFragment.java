package com.bq.user_center.mvp.user.ui;

import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.mvp.user.presenter.UserPresenter;
import com.bq.user_center.requset.bean.UserInfo;
import com.fan.baseuilibrary.utils.ToastUtils;
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

    private UserPresenter mUserPersenter;

    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_fragment_user;
    }

    @Override
    protected void attach() {
        //获取用户信息
        mUserPersenter = new UserPresenter(this);
        mUserPersenter.showUserInfo();

    }


    @OnClick({R2.id.rlt_head, R2.id.rlt_user_identification, R2.id.rlt_bank, R2.id.rlt_logout,R2.id.rlt_address})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.rlt_bank) {
            ARouter.getInstance().build(AppArouter.USER_CENTER_BANK_LIST).navigation();
        } else if (view.getId() == R.id.rlt_logout) {
            mUserPersenter.logout();
        } else if(view.getId() == R.id.rlt_address){
            ARouter.getInstance().build(AppArouter.USER_CENTER_ADDRESS_LIST).navigation();
        }
    }

    @Override
    public void logout() {
        ToastUtils.showToast(this.getActivity(), "退出成功");
        new Handler().postDelayed(()->{
            getActivity().finish();
        },1000);
    }

    @Override
    public void showUser(UserInfo info) {
        mTvNickName.setText(info.getUser_nick());
    }
}
