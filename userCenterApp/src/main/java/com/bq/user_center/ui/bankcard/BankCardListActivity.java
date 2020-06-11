package com.bq.user_center.ui.bankcard;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.ui.BaseAcitivty;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.bean.BankCard;
import com.bq.user_center.mvp.bankcard.BankCardPersenter;
import com.bq.user_center.mvp.bankcard.BankCardIView;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.MyRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
@Route(path = AppArouter.USER_CENTER_BANK_LIST)
public class BankCardListActivity extends BaseAcitivty implements BankCardIView, MyRefreshLayout.LayoutInterface<BankCard> {

    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;
    MyRefreshLayout<BankCard> myRefreshLayout;
    BankCardPersenter mBankCardPersenter;

    @BindView(R2.id.tv_title)
    TextView mTvTitle;

    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_activity_banklist;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("银行卡列表");
        mBankCardPersenter = new BankCardPersenter(this);
        myRefreshLayout = new MyRefreshLayout<>(this, this);
        myRefreshLayout.setRefresh(true,false);
        mFltContent.addView(myRefreshLayout);
    }

    @Override
    public View addFooter() {
        View view =  LinearLayout.inflate(this,R.layout.user_center_layout_add_card,null);
        return view;
    }

    @Override
    public void getBankListView(List<BankCard> list) {
        myRefreshLayout.addData(list);
    }

    @Override
    public int getItemRes() {
        return R.layout.user_center_item_banklist;
    }

    @Override
    public void bindItem(BaseViewHolder helper, BankCard item) {
        helper.setText(R.id.tv_card_number, item.getCardNo());
        helper.setText(R.id.tv_card_name, item.getBankName());
        helper.setText(R.id.tv_card_type, item.getCardType());
        helper.setText(R.id.tv_card_user, "持卡人：" + item.getPayeeName());
        ImageView ivCard = helper.getView(R.id.iv_bank);
    }

    @Override
    public void loadData(int page, int pageSize) {
        mBankCardPersenter.getBankList(myRefreshLayout);
    }

}
