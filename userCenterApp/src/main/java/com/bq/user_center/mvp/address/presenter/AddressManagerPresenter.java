package com.bq.user_center.mvp.address.presenter;

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
    public static  ArrayList<AddressBean> list;

    public AddressManagerPresenter(AddressBaseIView addressBaseIView) {
        mAddressBaseIView = addressBaseIView;
    }

    /**
     * 获取地址列表
     */
    public void getAddressList(){
        if(list == null){
            list = new ArrayList<>();
            for (int i = 0; i <3 ; i++) {
               AddressBean ab = new AddressBean(i,1,"phonenumber"+i,
                        "name"+i,1,"provinces"+i,"Address"+i);
                if(i== 0){
                    ab.setType(1);
                }else{
                    ab.setType(2);
                }
                list.add(ab);
            }
        }
        mAddressBaseIView.getAddressList(list);
    }

    public void addAddress(AddressBean address){
        mAddressBaseIView.addAdress();
        EventBus.getDefault().post(AddressManagerActivity.UPDATE_ADDRESS);
    }

    public void deleteAddress(AddressBean addressBean){
        list.remove(addressBean);
        mAddressBaseIView.deleteAddress();
        EventBus.getDefault().post(AddressManagerActivity.UPDATE_ADDRESS);
    }

    public void updateAddress(AddressBean address){
        if(address.getType() == 1){
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getId() == address.getId()){
                    list.remove(i);
                    list.add(i,address);
                }else{
                    list.get(i).setType(2);
                }
            }
        }
        mAddressBaseIView.editeAddress();
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
    public void updateAddress(String event) {
        if(AddressManagerActivity.UPDATE_ADDRESS.equals(event)){
            getAddressList();
        }
    }

}
