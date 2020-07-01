package com.bq.user_center.mvp.address.ui;

import com.bq.comm_config_lib.mvp.BaseIView;
import com.bq.user_center.requset.bean.AddressInfo;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/21/021
 * 版权：
 */
public interface AddressBaseIView extends BaseIView {
    default void getAddressList(List<AddressInfo> list){};
    default void addAdress(){};
    default void deleteAddress(){}
    default void updateAddress(){}
    default void getAddressById(AddressInfo info){}
}
