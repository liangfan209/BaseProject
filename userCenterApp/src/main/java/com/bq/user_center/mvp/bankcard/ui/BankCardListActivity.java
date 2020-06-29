package com.bq.user_center.mvp.bankcard.ui;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.mvp.bankcard.presenter.BankCardPresenter;
import com.bq.user_center.requset.bean.BankCard;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.MyRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
@Route(path = AppArouter.USER_CENTER_BANK_LIST)
public class BankCardListActivity extends BaseAcitivty implements BankCardBaseIView, MyRefreshLayout.LayoutInterface<BankCard> {

    public static final String UPDATE_BANK = "update_bank";
    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;
    BankCardPresenter mBankCardPersenter;

    @BindView(R2.id.tv_title)
    TextView mTvTitle;


    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_activity_banklist;
    }


    @Override
    protected BasePersenter createPersenter() {
        mBankCardPersenter = new BankCardPresenter(this);
        return mBankCardPersenter;
    }


    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("银行卡列表");

        mRefreshLayout = new MyRefreshLayout<BankCard>(this, this);
        mRefreshLayout.setRefresh(false, false);
        mFltContent.addView(mRefreshLayout);
    }


    @Override
    public View addHeader() {
        View view = LinearLayout.inflate(this, R.layout.user_center_layout_add_card, null);
        view.setOnClickListener(v -> {
            ARouter.getInstance().build(AppArouter.USER_CENTER_BANK_ADD_ACTIVITY).navigation();
        });
        return view;
    }


    @Override
    public void getBankListView(List<BankCard> list,int page) {
        if(page == 1){
            mRefreshLayout.adapter.setNewData(list);
            mRefreshLayout.adapter.notifyDataSetChanged();
        }else{
            mRefreshLayout.addData(list);
        }
    }

    @Override
    public BaseQuickAdapter<BankCard, ? extends BaseViewHolder> createAdapter() {
        BaseQuickAdapter adapter =  new BaseQuickAdapter<BankCard, BaseViewHolder>(R.layout.user_center_item_banklist,new ArrayList<BankCard>()) {
            @Override
            protected void convert(@NotNull BaseViewHolder helper, BankCard item) {
                helper.setText(R.id.tv_card_number, item.getCardNo());
                helper.setText(R.id.tv_card_name, item.getBankName());
                helper.setText(R.id.tv_card_type, item.getCardType());
                ImageView ivCard = helper.getView(R.id.iv_bank);
            }
        };
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                new BankCardOption(BankCardListActivity.this)
                        .showBottomView(new BankCardOption.BankCardOptionInter() {
                            @Override
                            public void unBind() {

                            }
                        });
                return false;
            }
        });
        return adapter;
    }

    @Override
    public void loadData(int page, int pageSize) {
        mBankCardPersenter.getBankList(page);
    }

}
