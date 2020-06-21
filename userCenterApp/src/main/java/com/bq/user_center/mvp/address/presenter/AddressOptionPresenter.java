package com.bq.user_center.mvp.address.presenter;

import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.user_center.mvp.address.ui.AddressBaseIView;
import com.bq.user_center.mvp.address.ui.AddressManagerActivity;
import com.bq.user_center.requset.bean.AddressBean;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/21/021
 * 版权：
 */
public class AddressOptionPresenter implements BasePersenter{
    private AddressBaseIView mAddressBaseIView;

    public AddressOptionPresenter(AddressBaseIView addressBaseIView) {
        mAddressBaseIView = addressBaseIView;
    }


    public void addAddress(AddressBean address){
        mAddressBaseIView.addAdress();
        EventBus.getDefault().post(AddressManagerActivity.UPDATE_ADDRESS);
    }

    public void deleteAddress(int addressId){
        mAddressBaseIView.deleteAddress();
        EventBus.getDefault().post(AddressManagerActivity.UPDATE_ADDRESS);
    }

    public void editeAddress(){
        mAddressBaseIView.editeAddress();
        EventBus.getDefault().post(AddressManagerActivity.UPDATE_ADDRESS);
    }


    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
    }
    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
    }
}
