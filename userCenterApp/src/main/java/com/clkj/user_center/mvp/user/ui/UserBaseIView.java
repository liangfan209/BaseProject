package com.clkj.user_center.mvp.user.ui;

import com.bq.comm_config_lib.mvp.BaseIView;
import com.clkj.user_center.requset.bean.CertificationInfo;
import com.clkj.user_center.requset.bean.MessageInfo;
import com.clkj.user_center.requset.bean.UserInfo;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public interface UserBaseIView extends BaseIView {
    default void showUser(UserInfo info){
        if(info == null)return;
    };
    default void logout(){};
    default void certificationView(){};
    default void getCertificationView(CertificationInfo info){ }

    default void messageListView(List<MessageInfo> list){}
    default void unReadCountView(int count){}

    default void changeMsgIView(){};
}
