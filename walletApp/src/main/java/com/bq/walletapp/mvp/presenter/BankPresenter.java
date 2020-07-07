package com.bq.walletapp.mvp.presenter;

import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.msgService.MessageBody;
import com.bq.comm_config_lib.msgService.MessageEvent;
import com.bq.comm_config_lib.msgService.MessageInter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.walletapp.api.bean.BankCard;
import com.bq.walletapp.mvp.ui.WalletIView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/29/029
 * 版权：
 */
public class BankPresenter implements BasePresenter {
    private WalletIView mTackCashIView;

    public BankPresenter(WalletIView tackCashIView) {
        mTackCashIView = tackCashIView;
    }





    /**
     * 获取银行卡列表
     */
    public void getBankCardList(){
        EventBus.getDefault().post(new MessageEvent(AppArouter.USER_CENTER_BANK_LIST_SERVICE, new MessageInter() {
            @Override
            public void callBack(MessageBody data) {
                ArrayList<BankCard> list = new Gson().fromJson(data.getContent(), new TypeToken<List<BankCard>>() {}.getType());
                mTackCashIView.getBankList(list);
            }
        }));
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
    public void updateAddress(String event) {
        if("update_bank".equals(event)){
            getBankCardList();
        }
    }
}
