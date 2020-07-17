package com.bq.order.mvp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseStickTimerFragment;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.mvp.presenter.WalletPresenter;
import com.bq.order.requset.bean.TransactionInfo;
import com.bq.order.requset.bean.TransactionMonthInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import skin.support.content.res.SkinCompatResources;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/21/021
 * 版权：
 */
public class WalletListFragment extends BaseStickTimerFragment<TransactionInfo> implements WalletIView{

    private WalletPresenter mWalletPresenter;
    private List<TransactionMonthInfo> mTransactionMonthInfos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                TransactionInfo client = (TransactionInfo) adapter.getData().get(position);
                if(client.getAmount() > 0){
                    //充值详情
                    ARouter.getInstance().build(AppArouter.WALLET_TRANSACTION_DETAIL_ACTIVITY)
                            .withSerializable("mTransactionInfo",client).navigation();
                }else{
                    ARouter.getInstance().build(AppArouter.WALLET_BILL_DETAIL_ACTIVITY)
                            .withSerializable("mTransactionInfo",client).navigation();
                }
            }
        });
        return view;
    }

    @Override
    protected BasePresenter createPersenter() {
        mWalletPresenter = new WalletPresenter(this);

        return  mWalletPresenter;
    }


    @Override
    protected Object getMonthDataDto(TransactionInfo item) {
        return item.getMonthInfo();
    }

    @Override
    protected void updateHeadView(int firstVisibleItemPosition) {
        for (int i = firstVisibleItemPosition; i >= 0; i--) {
            if (adapter.getData().get(i).getMonthInfo() != null) {
                TransactionMonthInfo nextItem = adapter.getData().get(i).getMonthInfo();
                if(StringUtils.isEmpty(selectTime)){
                    mTvChangeTime.setText(nextItem.getYear()+"-"+nextItem.getMonth());
                }
                mTvOut.setText("收入¥"+ Utils.getDouble2(nextItem.getIncome())+"   转出¥"+ Utils.getDouble2(Integer.valueOf(nextItem.getExpense())));
                break;
            }
        }
    }


    @Override
    public void transactionMonthListView(List<TransactionMonthInfo> list) {
        mTransactionMonthInfos = list;
        mWalletPresenter.transactionList(1,"{}");
    }

    @Override
    public void loadComplete() {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
    }

    @Override
    public void transactionListView(int page,List<TransactionInfo> list) {
        //开始组装数据
        //1.拿之前的数据最后一条，如果之前的列表是空，那么第一条加入头，
        //2 如果之前的列表最后一条的月份与当前列表该月不一致，那么加入头
        //3遍历当前的列表的月份，如果下一个与上一条不一致，加入头

        TransactionInfo currentTransactionInfo = null; //当前比较数据源
        List<TransactionInfo> oldDataList = adapter.getData();
        if(oldDataList.size() > 0 && page != 1){
            //获取老数据的最后一条
            TransactionInfo oldLastInfo = oldDataList.get(oldDataList.size() - 1);

            for (int i = 0; i < list.size(); i++) {
                if(i == 0){
                    if(!(Utils.getYearMonthByTime(oldLastInfo.getCreate_time()).equals(Utils.getYearMonthByTime(list.get(i).getCreate_time())))){
                        list.get(i).setMonthInfo(getMonthDataDtoByMonth(list.get(i).getCreate_time()));
                    }
                }else{
                    if(currentTransactionInfo != null){
                        if(!(Utils.getYearMonthByTime(currentTransactionInfo.getCreate_time()).equals(Utils.getYearMonthByTime(list.get(i).getCreate_time())))){
                            list.get(i).setMonthInfo(getMonthDataDtoByMonth(list.get(i).getCreate_time()));
                        }
                    }
                }
                currentTransactionInfo = list.get(i);
            }
        }else{
            //第一条数据必须加入
            for (int i = 0; i < list.size(); i++) {
                if(i == 0){
                    list.get(i).setMonthInfo(getMonthDataDtoByMonth(list.get(i).getCreate_time()));
                }else{
                    if(currentTransactionInfo != null){
                        if(!(Utils.getYearMonthByTime(currentTransactionInfo.getCreate_time()).equals(Utils.getYearMonthByTime(list.get(i).getCreate_time())))){
                            list.get(i).setMonthInfo(getMonthDataDtoByMonth(list.get(i).getCreate_time()));
                        }
                    }
                }
                currentTransactionInfo = list.get(i);
            }
        }
        if(page == 1){
            adapter.setNewData(list);
            adapter.notifyDataSetChanged();
        }else{
            addData(list);
        }
    }

    /**
     * 匹配到相关的月份
     * @param time
     * @return
     */
    private TransactionMonthInfo getMonthDataDtoByMonth(String time) {
        time = Utils.getYearMonthByTime(time);
        for (TransactionMonthInfo transactionMonthInfo : mTransactionMonthInfos) {
            if(time.equals(transactionMonthInfo.getYear()+"-"+transactionMonthInfo.getMonth())){
                return transactionMonthInfo;
            }
        }
        return null;
    }

    @Override
    protected void loadData(int page,String info) {
        if(page == 1){
            mWalletPresenter.getTransactionMonthist();
        }else{
            mWalletPresenter.transactionList(page,"{}");
        }
        mPage++;
    }


    @Override
    protected int getItemRes() {
        return R.layout.wallet_item_wallet_detail;
    }

    @Override
    protected void bindItem(BaseViewHolder helper, TransactionInfo item) {

        TransactionMonthInfo monthDataDto = item.getMonthInfo();
        RelativeLayout rltMonth = helper.getView(R.id.rlt_month);
        if (monthDataDto != null) {
            rltMonth.setVisibility(View.VISIBLE);
            if(!StringUtils.isEmpty(selectTime)){
                helper.setText(R.id.tv_change_time, selectTime);
            }else{
                helper.setText(R.id.tv_change_time, monthDataDto.getYear()+"-"+monthDataDto.getMonth());
            }
            TextView tvOut = rltMonth.findViewById(R.id.tv_out);
            TextView tvIn = rltMonth.findViewById(R.id.tv_in);
            tvOut.setText("收入¥"+ Utils.getDouble2(item.getMonthInfo().getIncome())+"   转出¥"+ Utils.getDouble2(Integer.valueOf(item.getMonthInfo().getExpense())));
        } else {
            rltMonth.setVisibility(View.GONE);
        }
        TextView tvMoney = helper.getView(R.id.tv_momey);
        helper.setText(R.id.tv_type,item.getRemark());
        helper.setText(R.id.tv_time,item.getCreate_time());
        tvMoney.setText(Utils.getDouble2(item.getAmount()));
        if(item.getAmount() > 0){
            tvMoney.setTextColor(SkinCompatResources.getColor(this.getContext(),R.color.ui_primary_color));
        }else{
            tvMoney.setTextColor(getResources().getColor(R.color.ui_txt_normal_color));
        }
//        if(item.getIncome_type().equals("out")){
//            tvMoney.setTextColor(SkinCompatResources.getColor(this.getContext(),R.color.ui_primary_color));
//            tvMoney.setText("+10,000.00");
//        }else{
//            tvMoney.setTextColor(getResources().getColor(R.color.ui_txt_normal_color));
//            tvMoney.setText("-10,000.00");
//        }
    }

}
