package com.clkj.wallet.mvp.wallet.presenter;

import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.request.AbstractReqeustCallback;
import com.clkj.wallet.api.WalletEventKey;
import com.clkj.wallet.mvp.wallet.ui.MyWalletActivity;
import com.clkj.wallet.mvp.wallet.ui.WalletIView;
import com.clkj.wallet.mvp.wallet.ui.WalletListFragment;
import com.clkj.wallet.requset.WalletHttpReqeustImp;
import com.clkj.wallet.requset.bean.BalanceBean;
import com.clkj.wallet.requset.bean.TransactionInfoBean;
import com.clkj.wallet.requset.bean.TransactionListBean;
import com.clkj.wallet.requset.bean.TransactionMonthListBean;
import com.clkj.wallet.requset.bean.WxBean;

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
                mWalletIView.transactionListView(currentPage,bean.getData_list());
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
        mWalletHttpReqeustImp.transactionDetail(id, new AbstractReqeustCallback<TransactionInfoBean>(mWalletIView) {
            @Override
            public void onSuccess(TransactionInfoBean bean) {
                mWalletIView.transactionDetailView(bean.getTransaction_info());
            }
        });
    }

    //充值
    public void recharge(String money,String payType) {
        mWalletHttpReqeustImp.recharge(money,"充值",payType,new AbstractReqeustCallback<WxBean>(mWalletIView) {
            @Override
            public void onStart() {
                mWalletIView.showLoading();
            }

            @Override
            public void onSuccess(WxBean bean) {
                mWalletIView.rechargeView(bean.getPay_info());
                EventBus.getDefault().post(WalletEventKey.UPDATE_BALANCE);
            }
        });
    }

    //提现
    public void tackCash(String money, String id) {
        mWalletHttpReqeustImp.tackCash(money,id,"提现","bank",new AbstractReqeustCallback<Object>(mWalletIView) {
            @Override
            public void onStart() {
                mWalletIView.showLoading();
            }

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
            if( mWalletIView != null && mWalletIView instanceof MyWalletActivity){
                getBalance();
            }else if(mWalletIView != null && mWalletIView instanceof WalletListFragment){
//                transactionList(1,"{}");
                getTransactionMonthist();
            }
        }
    }

}
