package com.bq.login.mvp.login.presenter;

import android.text.TextUtils;

import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.msgService.MessageBody;
import com.bq.comm_config_lib.msgService.MessageEvent;
import com.bq.comm_config_lib.msgService.MessageInter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.request.AbstractReqeustCallback;
import com.bq.comm_config_lib.utils.CheckUtils;
import com.bq.login.R;
import com.bq.login.api.bean.LoginConfigBean;
import com.bq.login.mvp.login.ui.LoginBaseIView;
import com.bq.login.requset.LoginHttpReqeustImp;
import com.bq.login.requset.bean.LoginInfo;
import com.bq.utilslib.AccountValidatorUtil;
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
public class LoginPresenter implements BasePresenter {
    private LoginBaseIView mIView;
    private LoginConfigBean mLoginConfigBean;
    private LoginHttpReqeustImp mLoginHttpReqeustImp;
//    private LoginModel mLoginModel;

    public LoginPresenter(LoginBaseIView IView) {
        this(IView, false);
    }
    public LoginPresenter(LoginBaseIView IView, boolean hasConfig) {
        this.mIView = IView;
        mLoginHttpReqeustImp = new LoginHttpReqeustImp();
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

    /**
     * 登陆
     * @param phoneNumber
     * @param pwd
     */
    public void login(String phoneNumber, String pwd) {
        if (!AccountValidatorUtil.isMobile(phoneNumber)) {
            ToastUtils.showToast(mIView.getActivity(), "请输入正确的手机号码");
            return;
        }
        if(StringUtils.isEmpty(pwd)){
            ToastUtils.showToast(mIView.getActivity(), "密码不能为空");
            return;
        }

        mLoginHttpReqeustImp.login(phoneNumber, pwd, new AbstractReqeustCallback<LoginInfo>(mIView) {

            @Override
            public void onStart() {
                mIView.showLoading();
            }

            @Override
            public void onSuccess(LoginInfo loginInfo) {
                mIView.loginView(loginInfo);
            }
        });

    }

    /**
     * 获取验证码
     * @param phone
     */
    public void getVertificatCode(String phone) {
        mLoginHttpReqeustImp.getVertificatCode(phone, new AbstractReqeustCallback<Object>(mIView) {
            @Override
            public void onStart() {
                mIView.showLoading();
            }

            @Override
            public void onSuccess(Object obj) {
            }
        });
    }


    /**
     * 注册
     * @param phone
     * @param pwd
     * @param checkCode
     */
    public void register(String phone, String pwd, String checkCode) {
        mLoginHttpReqeustImp.register(phone, pwd, checkCode,new AbstractReqeustCallback<LoginInfo>(mIView) {
            @Override
            public void onStart() {
                mIView.showLoading();
            }

            @Override
            public void onSuccess(LoginInfo loginInfo) {
                mIView.registerView();
            }
        });

    }

    public void forgetPwd(String phone, String pwd, String checkCode) {
        mLoginHttpReqeustImp.forgetPwd(phone, pwd, checkCode,new AbstractReqeustCallback<Object>(mIView) {
            @Override
            public void onStart() {
                mIView.showLoading();
            }

            @Override
            public void onSuccess(Object loginInfo) {
                mIView.forgetPwdView();
            }
        });
    }

    public void modifypwd(String oldPwd,String newPwd){
        mLoginHttpReqeustImp.modify(oldPwd, newPwd, new AbstractReqeustCallback<Object>(mIView) {
            @Override
            public void onStart() {
                mIView.showLoading();
            }

            @Override
            public void onSuccess(Object o) {
                mIView.modifyPwdView();
            }
        });
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

    /**
     * 验证注册表单
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

}
