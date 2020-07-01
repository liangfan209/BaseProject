package com.bq.user_center.mvp.user.ui;

import com.bq.comm_config_lib.mvp.BaseIView;
import com.bq.user_center.requset.bean.UserInfo;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public interface UserBaseIView extends BaseIView {
    void showUser(UserInfo info);
    default void logout(){};
}