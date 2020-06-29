package com.bq.user_center.mvp.bankcard.presenter;

import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.user_center.mvp.bankcard.ui.BankCardBaseIView;
import com.bq.user_center.mvp.bankcard.ui.BankCardListActivity;
import com.bq.user_center.requset.bean.BankCard;

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
 * 时间：2020/6/11
 * 版权：
 */
public class BankCardPresenter implements BasePersenter {
    private BankCardBaseIView mBankCardView;


    public BankCardPresenter() {

    }
    public BankCardPresenter(BankCardBaseIView bankCardView) {
        mBankCardView = bankCardView;
    }

    public static List<BankCard> bankCardList ;


    public List<BankCard> getBankList(){
        if(bankCardList == null){
            bankCardList = new ArrayList<>();
            bankCardList.add(new BankCard("name1","**** **** **** 1234"));
        }
        return bankCardList;
    }

    public void getBankList(int page) {
//        new UserCenterHttpReqeustImp().getBankList(new AbstractReqeustCallback<List<BankCard>>(mBankCardView) {
//            @Override
//            public void onSuccess(List<BankCard> list) {
//                mBankCardView.getBankListView(list);
//            }
//        });
        if(bankCardList == null){
            bankCardList = new ArrayList<>();
            bankCardList.add(new BankCard("name1","**** **** **** 1234"));
        }
        mBankCardView.getBankListView(bankCardList,page);
    }

    public void addBankCard(BankCard bankCard){
        bankCardList.add(bankCard);
        EventBus.getDefault().post(BankCardListActivity.UPDATE_BANK);
        mBankCardView.addBankSuccess();
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
        if(BankCardListActivity.UPDATE_BANK.equals(event)){
            getBankList(1);
        }
    }

}
