package com.bq.login.requset;

import com.bq.comm_config_lib.request.RequestCallBackInter;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/17
 * 版权：
 */
public interface LoginHttpReqeustInter {
    //登录
    void login(String name, String pwd, RequestCallBackInter callBack);

    //退出
    void logout(RequestCallBackInter callBack);

    //获取验证码
    void getVertificatCode(String type, String phone, RequestCallBackInter callBack);

    //忘记密码
   void forgetPwd(String type, String phone, RequestCallBackInter callBack);

   //注册
    void register(String code,String password,String phone, RequestCallBackInter callBack);
}
