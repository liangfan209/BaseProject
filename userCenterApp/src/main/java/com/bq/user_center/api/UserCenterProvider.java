package com.bq.user_center.api;

import com.bq.comm_config_lib.annotation.Register;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.msgService.MessageBody;
import com.bq.comm_config_lib.msgService.MessageEvent;
import com.bq.comm_config_lib.request.RequestCallBackInter;
import com.bq.user_center.requset.UserCenterHttpReqeustImp;
import com.bq.user_center.requset.bean.AddressInfo;
import com.bq.user_center.requset.bean.AddressListBean;
import com.bq.user_center.requset.bean.BankCardInfo;
import com.bq.user_center.requset.bean.BankListBean;
import com.bq.user_center.requset.bean.UserInfo;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/9
 * 版权：
 */
@Register
public class UserCenterProvider {

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void providerData(MessageEvent event) {
        if(AppArouter.USER_CENTER_SERVICE.equals(event.key)){
            new UserCenterHttpReqeustImp().showUserInfo(new RequestCallBackInter<UserInfo>() {
                @Override
                public void onSuccess(UserInfo userInfo) {
                    event.eventInterface.callBack(new MessageBody(MessageBody.SUCCESS_CODE,new Gson().toJson(userInfo)));
                }
                @Override
                public void onError(String msg) {
                    event.eventInterface.callBack(new MessageBody(MessageBody.FAIL_CODE,msg));
                }
            });
        } else if(AppArouter.USER_CENTER_BANK_LIST_SERVICE.equals(event.key)) {
            new UserCenterHttpReqeustImp().getBankList(new RequestCallBackInter<BankListBean>(){
                @Override
                public void onSuccess(BankListBean bean) {
                    List<BankCardInfo> list = bean.getBankcard_list();
                    event.eventInterface.callBack(new MessageBody(MessageBody.SUCCESS_CODE, new Gson().toJson(list)));
                }
                @Override
                public void onError(String msg) {
                }
            });


        } else if(AppArouter.USER_CENTER_ADDRESS_DEFAULT_SERVICE.equals(event.key)){
            new UserCenterHttpReqeustImp().getAddressList(new RequestCallBackInter<AddressListBean>() {
                @Override
                public void onSuccess(AddressListBean bean) {
                    List<AddressInfo> address_list = bean.getAddress_list();
                    if(address_list.size() == 0){
                        event.eventInterface.callBack(new MessageBody(MessageBody.SUCCESS_CODE, ""));
                        return;
                    }
                    for (int i = 0; i < address_list.size(); i++) {
                        if(address_list.get(i).getIs_default() == 1){
                            event.eventInterface.callBack(new MessageBody(MessageBody.SUCCESS_CODE, new Gson().toJson(address_list.get(i))));
                            return;
                        }
                    }
                    event.eventInterface.callBack(new MessageBody(MessageBody.SUCCESS_CODE, ""));
                }

                @Override
                public void onError(String msg) {

                }
            });
        }
    }
}
