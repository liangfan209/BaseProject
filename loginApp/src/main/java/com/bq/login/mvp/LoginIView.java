package com.bq.login.mvp;

import com.bq.comm_config_lib.mvp.IView;
import com.bq.login.bean.LoginInfo;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/28/028
 * 版权：
 */
public interface LoginIView extends IView {
    default void loginView(LoginInfo info){};
    default void forgetPwdView(){};
    default void getVerticalCodeView(){};
}
