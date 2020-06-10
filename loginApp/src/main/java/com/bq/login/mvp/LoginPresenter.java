package com.bq.login.mvp;

import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.http.SignJsonCallBack;
import com.bq.login.bean.LoginInfo;
import com.bq.netlibrary.NetManager;
import com.bq.netlibrary.http.BaseResponse;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28
 * 版权：
 */
public class LoginPresenter implements BasePersenter {
    private LoginIView mIView;
//    private LoginModel mLoginModel;

    public LoginPresenter(LoginIView IView) {
        mIView = IView;
//        this.mLoginModel = loginModel;
    }

    public void login(String api, String name, String pwd) {
        Map<String,String> map = new HashMap<>();
        map.put("api",api);
        map.put("username",name);
        map.put("password",pwd);

        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<LoginInfo>>() {
            @Override
            public void onSuccess(Response<BaseResponse<LoginInfo>> response) {
                mIView.loginView(response.body().data);
            }
        });


    }

    public void getVertificatCode(String api, String type, String phone) {
//        mLoginModel.getVertificatCode(api, type, phone, mIView);
    }

    public void forgetPwd(String api, String phone, String pwd, String checkCode) {
//        mLoginModel.forgetPwd(api, phone, pwd, checkCode, mIView);
    }

}
