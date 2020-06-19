package com.bq.login.mvp.login.presenter;

import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.request.AbstractReqeustCallback;
import com.bq.login.mvp.login.ui.LoginIView;
import com.bq.login.requset.LoginHttpReqeustImp;
import com.bq.login.requset.bean.LoginInfo;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/2
 * 版权：
 */
public class LoginPresenter implements BasePersenter {
    private LoginIView mIView;
//    private LoginModel mLoginModel;

    public LoginPresenter(LoginIView IView) {
        mIView = IView;
    }
    public LoginPresenter() {
    }

    public void login(String name, String pwd) {
        new LoginHttpReqeustImp().login(name, pwd, new AbstractReqeustCallback<LoginInfo>(mIView) {
            @Override
            public void onSuccess(LoginInfo loginInfo) {
                mIView.loginView(loginInfo);
            }
        });
    }

    public void getVertificatCode(String type, String phone) {
    }

    public void forgetPwd(String phone, String pwd, String checkCode) {
    }

}
