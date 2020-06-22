package com.bq.login.mvp.login.presenter;

import android.text.TextUtils;

import com.bq.comm_config_lib.msgService.MessageBody;
import com.bq.comm_config_lib.msgService.MessageEvent;
import com.bq.comm_config_lib.msgService.MessageInter;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.utils.CheckUtils;
import com.bq.login.R;
import com.bq.login.api.bean.LoginConfigBean;
import com.bq.login.mvp.login.ui.LoginBaseIView;
import com.bq.login.requset.bean.LoginInfo;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/2
 * 版权：
 */
public class LoginPresenter implements BasePersenter {
    private LoginBaseIView mIView;
    private LoginConfigBean mLoginConfigBean;
//    private LoginModel mLoginModel;

    public LoginPresenter(LoginBaseIView IView) {
        this(IView, false);
    }

    public LoginPresenter(LoginBaseIView IView, boolean hasConfig) {
        this.mIView = IView;
        if (hasConfig) {
            //发送消息读取配置文件
            EventBus.getDefault().post(new MessageEvent("config/login", new MessageInter() {
                @Override
                public void callBack(MessageBody data) {
                    mLoginConfigBean = new Gson().fromJson(data.getContent(), LoginConfigBean.class);
                    mIView.updateView(mLoginConfigBean);
                }
            }));
        }
    }

    public void login(String name, String pwd) {
//        new LoginHttpReqeustImp().login(name, pwd, new AbstractReqeustCallback<LoginInfo>(mIView) {
//            @Override
//            public void onSuccess(LoginInfo loginInfo) {
//                mIView.loginView(loginInfo);
//            }
//        });
        mIView.loginView(new LoginInfo(""));

    }

    public void getVertificatCode(String type, String phone) {
    }

    public void forgetPwd(String phone, String pwd, String checkCode) {
        mIView.forgetPwdView();
    }

    public void register(String phone, String pwd, String checkCode) {
        mIView.registerView();
    }

    /**
     * 验证注册表单
     *
     * @param phoneNumber
     * @param newPwd
     * @param rePwd
     * @param checkCode
     * @return
     */
    public boolean checkRegisterFrom(String phoneNumber, String newPwd, String rePwd, String checkCode) {
        if (!CheckUtils.checkPhoneNumber(mIView.getActivity(), phoneNumber)) {
            return false;
        }
        if (TextUtils.isEmpty(newPwd)) {
            ToastUtils.showToast(mIView.getActivity(), R.string.new_pwd_not_null);
            return false;
        }

        if (TextUtils.isEmpty(rePwd)) {
            ToastUtils.showToast(mIView.getActivity(), R.string.confirm_pwd_not_null);
            return false;
        }
        if (TextUtils.isEmpty(checkCode)) {
            ToastUtils.showToast(mIView.getActivity(), R.string.vertication_code_not_null);
            return false;
        }
        if (!newPwd.equals(rePwd)) {
            ToastUtils.showToast(mIView.getActivity(), R.string.pwd_not_compaire);
            return false;
        }
        return true;
    }

    /**
     * 设置密码
     */
    public void setPwd(String pwd) {
        mIView.settingPwdView();
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

    }


}
