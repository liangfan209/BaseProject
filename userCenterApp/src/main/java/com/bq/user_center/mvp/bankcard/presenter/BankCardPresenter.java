package com.bq.user_center.mvp.bankcard.presenter;

import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.request.AbstractReqeustCallback;
import com.bq.user_center.api.UserCenterEventKey;
import com.bq.user_center.mvp.bankcard.ui.BankCardBaseIView;
import com.bq.user_center.requset.UserCenterHttpReqeustImp;
import com.bq.user_center.requset.bean.BankCardInfo;
import com.bq.user_center.requset.bean.BankListBean;
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
 * 时间：2020/6/11
 * 版权：
 */
public class BankCardPresenter implements BasePresenter {
    private BankCardBaseIView mBankCardView;
    private UserCenterHttpReqeustImp mUserCenterHttpReqeustImp;


    public BankCardPresenter() {

    }
    public BankCardPresenter(BankCardBaseIView bankCardView) {
        mBankCardView = bankCardView;
        mUserCenterHttpReqeustImp = new UserCenterHttpReqeustImp();
    }

    public static List<BankCardInfo> bankCardList ;


//    public List<BankCardInfo> getBankList(){
//        return bankCardList;
//    }


    /**
     * 获取银行卡列表
     */
    public void getBankList() {
        mUserCenterHttpReqeustImp.getBankList(new AbstractReqeustCallback<BankListBean>(mBankCardView) {
            @Override
            public void onSuccess(BankListBean bean) {
                mBankCardView.getBankListView(bean.getBankcard_list());
            }
        });
    }

    /**
     * 添加银行卡
     * @param bankCard
     */
    public void addBankCard(BankCardInfo bankCard){
        String info = new Gson().toJson(bankCard);
        mUserCenterHttpReqeustImp.addBank(info, new AbstractReqeustCallback(mBankCardView) {
            @Override
            public void onStart() {
                mBankCardView.showLoading();
            }

            @Override
            public void onSuccess(Object o) {
                mBankCardView.addBankSuccess();
                EventBus.getDefault().post(UserCenterEventKey.UPDATE_BANK);
            }
        });
    }

    /**
     * 删除银行卡
     * @param id
     */
    public void deleteBank(String id){
        mUserCenterHttpReqeustImp.deleteBank(id, new AbstractReqeustCallback(mBankCardView) {
            @Override
            public void onStart() {
                mBankCardView.showLoading();
            }

            @Override
            public void onSuccess(Object o) {
                mBankCardView.removeSuccess();
                EventBus.getDefault().post(UserCenterEventKey.UPDATE_BANK);
            }
        });

    }


    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        EventBus.getDefault().unregister(this);
    }

    public void unRegister(){
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateAddress(String event) {
        if(UserCenterEventKey.UPDATE_BANK.equals(event)){
            getBankList();
        }
    }

}
