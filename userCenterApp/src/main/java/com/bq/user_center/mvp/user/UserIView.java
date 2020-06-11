package com.bq.user_center.mvp.user;

import com.bq.comm_config_lib.mvp.IView;
import com.bq.user_center.bean.UserInfo;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public interface UserIView extends IView {
    void showUser(UserInfo info);
}
