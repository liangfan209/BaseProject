package com.bq.walletapp.wallet.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.mvp.ui.BaseStickTimerFragment;
import com.bq.walletapp.R;
import com.bq.walletapp.api.bean.EarningsListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/21/021
 * 版权：
 */
public class WalletListFragment extends BaseStickTimerFragment<EarningsListBean.EarningBean> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
//                EarningsListBean.EarningBean client = (EarningsListBean.EarningBean) adapter.getData().get(position);
//                Intent intent = new Intent(WalletListFragment.this.getContext(), WalletListFragment.class);
//                intent.putExtra("earning", client);
//                WalletListFragment.this.getActivity().startActivity(intent);
            }
        });
        return view;

    }

    @Override
    protected Object getMonthDataDto(EarningsListBean.EarningBean item) {
        return item.getMonthDataDto();
    }

    @Override
    protected void updateHeadView(int firstVisibleItemPosition) {
        for (int i = firstVisibleItemPosition; i >= 0; i--) {
            if (adapter.getData().get(i).getMonthDataDto() != null) {
                EarningsListBean.MonthDateDto nextItem = adapter.getData().get(i).getMonthDataDto();
//                mTvIn.setVisibility(View.GONE);
//                mTvOut.setVisibility(View.GONE);
//                mIvJiantou.setVisibility(View.GONE);
                if(StringUtils.isEmpty(selectTime)){
                    mTvChangeTime.setText(nextItem.getMonthStr());
                }
                break;
            }
        }
    }

    @Override
    protected void loadData(String info) {
        EarningsListBean.MonthDateDto dateDto = new EarningsListBean.MonthDateDto(10.0,10.0,"一月");
        EarningsListBean.EarningBean eb1 = new EarningsListBean.EarningBean();
        eb1.setIncome_type("-1");
        eb1.setMonthDataDto(dateDto);
        EarningsListBean.EarningBean eb2 = new EarningsListBean.EarningBean();
        eb2.setIncome_type("1");
        List<EarningsListBean.EarningBean> list = new ArrayList<>();
        list.add(eb1);
        for (int i = 0; i < 15; i++) {
            list.add(eb2);
        }
        addData(list);
    }

    @Override
    protected int getItemRes() {
        return R.layout.wallet_item_wallet_detail;
    }

    @Override
    protected void bindItem(BaseViewHolder helper, EarningsListBean.EarningBean item) {

        EarningsListBean.MonthDateDto monthDataDto = item.getMonthDataDto();
        RelativeLayout rltMonth = helper.getView(R.id.rlt_month);
        if (monthDataDto != null) {
            rltMonth.setVisibility(View.VISIBLE);
//            helper.getView(R.id.tv_out).setVisibility(View.GONE);
//            helper.getView(R.id.iv_jiantou).setVisibility(View.GONE);
            if(!StringUtils.isEmpty(selectTime)){
                helper.setText(R.id.tv_change_time, selectTime);
            }else{
                helper.setText(R.id.tv_change_time, monthDataDto.getMonthStr());
            }
        } else {
            rltMonth.setVisibility(View.GONE);
        }
        TextView tvMoney = helper.getView(R.id.tv_momey);
        if(item.getIncome_type().equals("1")){
            tvMoney.setTextColor(getResources().getColor(R.color.ui_primary_color));
            tvMoney.setText("+10,000.00");
        }else{
            tvMoney.setTextColor(getResources().getColor(R.color.ui_txt_normal_color));
            tvMoney.setText("-10,000.00");
        }
    }

}
