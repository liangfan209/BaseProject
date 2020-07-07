package com.bq.login.mvp.login.ui;

import com.bq.comm_config_lib.mvp.BaseIView;
import com.bq.login.api.bean.LoginConfigBean;
import com.bq.login.requset.bean.LoginInfo;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28/028
 * 版权：
 */
public interface LoginBaseIView extends BaseIView {
    default void updateView(LoginConfigBean type){}
    default void loginView(LoginInfo info){};
    default void forgetPwdView(){};
    default void registerView(){}
    default void settingPwdView(){}
    default void modifyPwdView(){}
    default void getVerticalCodeView(){};

}
