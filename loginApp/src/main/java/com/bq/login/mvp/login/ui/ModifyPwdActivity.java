package com.bq.login.mvp.login.ui;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.login.R;
import com.bq.login.R2;
import com.bq.login.mvp.login.presenter.LoginPresenter;
import com.bq.utilslib.Md5Utils;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/22
 * 版权：
 */
@Route(path = AppArouter.LOGIN_MODIFY_ACTIVITY)
public class ModifyPwdActivity extends BaseAcitivty implements LoginBaseIView{
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_right)
    TextView mTvRight;
    @BindView(R2.id.rlt_title)
    RelativeLayout mRltTitle;
    @BindView(R2.id.et_old_pwd)
    DeletableEditText mEtOldPwd;
    @BindView(R2.id.et_pwd)
    DeletableEditText mEtPwd;
    @BindView(R2.id.et_re_pwd)
    DeletableEditText mEtRePwd;
    @BindView(R2.id.tv_modify)
    TextView mtvModify;

    private LoginPresenter mLoginPresenter;

    @Override
    protected int getContentViewLayout() {
        return R.layout.login_activity_modify;
    }

    @Override
    protected BasePresenter createPersenter() {
        mLoginPresenter = new LoginPresenter(this,false);
        return mLoginPresenter;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("修改密码");
    }


    @Override
    public void modifyPwdView() {
        ToastUtils.showToastOk(this,"修改成功");
        new Handler().postDelayed(()->{
            ARouter.getInstance().build(AppArouter.MAIN_ACTIVITY).navigation();
            finish();
        },1000);
    }

    @OnClick({R2.id.iv_title_left, R2.id.tv_modify})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.tv_modify){
            String oldPwd = mEtOldPwd.getText().toString();
            String newPwd = mEtPwd.getText().toString();
            String rePwd = mEtRePwd.getText().toString();

            if(StringUtils.isEmpty(oldPwd)){
                ToastUtils.showToast(this,"请输入原密码");
                return;
            }
            if(StringUtils.isEmpty(newPwd)){
                ToastUtils.showToast(this,"请输入新密码");
                return;
            }
            if(StringUtils.isEmpty(rePwd)){
                ToastUtils.showToast(this,"请确认新密码");
                return;
            }
            if(!newPwd.equals(rePwd)){
                ToastUtils.showToast(this,"确认密码不一致");
                return;
            }
            mLoginPresenter.modifypwd(Md5Utils.md5(oldPwd),Md5Utils.md5(newPwd));
        }
    }
}
