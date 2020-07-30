package com.bq.user_center.mvp.address.presenter;

import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.request.AbstractReqeustCallback;
import com.bq.user_center.api.UserCenterEventKey;
import com.bq.user_center.mvp.address.ui.AddressBaseIView;
import com.bq.user_center.requset.UserCenterHttpReqeustImp;
import com.bq.user_center.requset.bean.AddressInfo;
import com.bq.user_center.requset.bean.AddressInfoBean;
import com.bq.user_center.requset.bean.AddressListBean;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/21/021
 * 版权：
 */
public class AddressManagerPresenter implements BasePresenter {
    private AddressBaseIView mAddressBaseIView;
    private UserCenterHttpReqeustImp mUserCenterHttpReqeustImp;

    public AddressManagerPresenter(AddressBaseIView addressBaseIView) {
        mAddressBaseIView = addressBaseIView;
        mUserCenterHttpReqeustImp = new UserCenterHttpReqeustImp();
    }

    /**
     * 获取地址列表
     */
    public void getAddressList() {
        mUserCenterHttpReqeustImp.getAddressList(new AbstractReqeustCallback<AddressListBean>(mAddressBaseIView) {
            @Override
            public void onSuccess(AddressListBean bean) {
                List<AddressInfo> address_list = bean.getAddress_list();
//               Collections.sort(address_list);
                mAddressBaseIView.getAddressList(address_list);
            }
        });
    }

    /**
     * 添加地址
     *
     * @param address
     */
    public void addAddress(AddressInfo address) {
        String info = new Gson().toJson(address);
        mUserCenterHttpReqeustImp.addAddress(info,address.getIs_default(), new AbstractReqeustCallback<String>(mAddressBaseIView) {
            @Override
            public void onSuccess(String str) {
                mAddressBaseIView.addAdress();
                EventBus.getDefault().post(UserCenterEventKey.UPDATE_ADDRESS);
            }
        });

    }

    /**
     * 删除地址
     *
     * @param id
     */
    public void deleteAddress(String id) {
        mUserCenterHttpReqeustImp.deleteAddress(id, new AbstractReqeustCallback<String>(mAddressBaseIView) {
            @Override
            public void onSuccess(String str) {
                mAddressBaseIView.deleteAddress();
                EventBus.getDefault().post(UserCenterEventKey.UPDATE_ADDRESS);
            }
        });

    }

    /**
     * 修改地址
     *
     * @param id
     * @param address
     */
    public void updateAddress(String id, AddressInfo address) {
        String info = new Gson().toJson(address);
        int isDefault = address.getIs_default();
        mUserCenterHttpReqeustImp.updateAddress(id, info,isDefault, new AbstractReqeustCallback<Object>(mAddressBaseIView) {
            @Override
            public void onStart() {
                mAddressBaseIView.showLoading();
            }

            @Override
            public void onSuccess(Object obj) {
                mAddressBaseIView.updateAddress();
                EventBus.getDefault().post(UserCenterEventKey.UPDATE_ADDRESS);
            }
        });
    }


    public void getAddressById(String id) {
        mUserCenterHttpReqeustImp.getAddressById(id, new AbstractReqeustCallback<AddressInfoBean>(mAddressBaseIView) {
            @Override
            public void onSuccess(AddressInfoBean info) {
                mAddressBaseIView.getAddressById(info.getAddress_info());
                EventBus.getDefault().post(UserCenterEventKey.UPDATE_ADDRESS);
            }
        });
    }


    public void unRegister() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateAddressList(String event) {
        if (UserCenterEventKey.UPDATE_ADDRESS.equals(event)) {
            getAddressList();
        }
    }

}
