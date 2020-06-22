package com.bq.user_center.mvp.address.presenter;

import com.bq.comm_config_lib.msgService.MessageEvent;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.user_center.mvp.address.ui.AddressBaseIView;
import com.bq.user_center.mvp.address.ui.AddressManagerActivity;
import com.bq.user_center.requset.bean.AddressBean;
import com.bq.utilslib.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/21/021
 * 版权：
 */
public class AddressManagerPresenter implements BasePersenter{
    private AddressBaseIView mAddressBaseIView;

    public AddressManagerPresenter(AddressBaseIView addressBaseIView) {
        mAddressBaseIView = addressBaseIView;
    }

    /**
     * 获取地址列表
     */
    public void getAddressList(){
        ArrayList<AddressBean> list = new ArrayList<AddressBean>();
        for (int i = 0; i <10 ; i++) {
            if(i== 0){
                list.add(new AddressBean(1));
            }
            list.add(new AddressBean(2));
        }
        mAddressBaseIView.getAddressList(list);
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
        mAddressBaseIView.addAdress();
        EventBus.getDefault().post(AddressManagerActivity.UPDATE_ADDRESS);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        LogUtils.d("=== >>>   presenter....oncreate");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateAddress(MessageEvent event) {
        if(AddressManagerActivity.UPDATE_ADDRESS.equals(event.key)){
            getAddressList();
        }
    }

}
