package com.bq.login.requset;

import com.bq.comm_config_lib.request.RequestCallBackInter;
import com.bq.comm_config_lib.request.SignJsonCallBack;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.login.requset.bean.LoginInfo;
import com.bq.netlibrary.NetManager;
import com.bq.netlibrary.http.BaseResponse;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/16
 * 版权：
 */
public class LoginHttpReqeustImp implements LoginHttpReqeustInter{

    //登录请求
    @Override
    public void login(String name, String pwd, RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiLogin.API_LOGIN_LOGIN);
        map.put("username",name);
        map.put("password",pwd);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<LoginInfo>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<LoginInfo>> response) {
                super.onSuccess(response);
            }
        });
    }

    //退出登录请求
    @Override
    public void logout(RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiLogin.API_LOGOUT);
        map.put("token", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<String>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<String>> response) {
                super.onSuccess(response);
            }
        });
    }


    //获取验证码
    @Override
    public void getVertificatCode(String type, String phone, RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiLogin.API_GET_VERTIFICAT_CODE);
        map.put("type",type);
        map.put("phone",phone);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<String>>(callBack));
    }

    //忘记密码
    @Override
    public void forgetPwd(String type, String phone, RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiLogin.API_FORGET_PWD);
        map.put("type",type);
        map.put("phone",phone);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<String>>(callBack));
    }

    @Override
    public void register(String phone, String password, String code, RequestCallBackInter callBack) {
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiLogin.API_LOGIN_REGISTER);
        map.put("phone", phone);
        map.put("password", password);
        map.put("code", code);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<String>>(callBack){
            @Override
            public void onSuccess(Response<BaseResponse<String>> response) {
                super.onSuccess(response);
                callBack.onSuccess(response.body().result);
            }
        });
    }
}
