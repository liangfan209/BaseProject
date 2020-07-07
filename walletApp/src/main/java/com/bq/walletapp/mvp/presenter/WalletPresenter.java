package com.bq.walletapp.mvp.presenter;

import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.request.AbstractReqeustCallback;
import com.bq.walletapp.api.WalletEventKey;
import com.bq.walletapp.api.bean.TransactionListBean;
import com.bq.walletapp.api.bean.TransactionMonthListBean;
import com.bq.walletapp.mvp.ui.WalletIView;
import com.bq.walletapp.requset.WalletHttpReqeustImp;
import com.bq.walletapp.requset.bean.BalanceBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/3
 * 版权：
 */
public class WalletPresenter implements BasePresenter {

    private WalletIView mWalletIView;
    private WalletHttpReqeustImp mWalletHttpReqeustImp;

    public WalletPresenter(WalletIView walletIView) {
        mWalletIView = walletIView;
        mWalletHttpReqeustImp = new WalletHttpReqeustImp();
    }
    //交易列表
    public void transactionList(int currentPage,String searchInfo) {
        mWalletHttpReqeustImp.transactionList(currentPage,searchInfo,new AbstractReqeustCallback<TransactionListBean>(mWalletIView) {
            @Override
            public void onSuccess(TransactionListBean bean) {
                mWalletIView.transactionListView(bean.getData_list());
            }

            @Override
            public void onComplete() {
                super.onComplete();
                mWalletIView.loadComplete();
            }
        });

    }

    /**
     * 获取交易列表月份
     */
    public void getTransactionMonthist(){
        mWalletHttpReqeustImp.transactionMonthList(new AbstractReqeustCallback<TransactionMonthListBean>(mWalletIView) {
            @Override
            public void onSuccess(TransactionMonthListBean bean) {
                mWalletIView.transactionMonthListView(bean.getStatistics_list());
            }
        });
    }

    //交易详情
    public void transactionDetail(String id) {
        mWalletHttpReqeustImp.transactionDetail(id, new AbstractReqeustCallback(mWalletIView) {
            @Override
            public void onSuccess(Object o) {
            }
        });
    }

    //充值
    public void recharge(String money) {
        mWalletHttpReqeustImp.recharge(money,"remark","bank",new AbstractReqeustCallback<Object>(mWalletIView) {
            @Override
            public void onSuccess(Object bean) {
                mWalletIView.rechargeView();
                EventBus.getDefault().post(WalletEventKey.UPDATE_BALANCE);
            }
        });
    }

    //提现
    public void tackCash(String money, String id) {
        mWalletHttpReqeustImp.tackCash(money,"","remark","bank",new AbstractReqeustCallback<Object>(mWalletIView) {
            @Override
            public void onSuccess(Object bean) {
                mWalletIView.tackCashView();
                EventBus.getDefault().post(WalletEventKey.UPDATE_BALANCE);
            }
        });
    }

    //查询余额
    public void getBalance() {
        mWalletHttpReqeustImp.getBalance(new AbstractReqeustCallback<BalanceBean>(mWalletIView) {
            @Override
            public void onSuccess(BalanceBean bean) {
                mWalletIView.getBalanceView(bean.getBalance());
            }
        });
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        EventBus.getDefault().register(this);
    }

    public void unRegister(){
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateBalance(String event){
        if(WalletEventKey.UPDATE_BALANCE.equals(event)){
            getBalance();
        }
    }

}
