package com.bq.user_center.mvp.bankcard.ui;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.mvp.bankcard.presenter.BankCardPresenter;
import com.bq.user_center.requset.bean.BankCardInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.utils.ToastUtils;
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
public class BankCardListActivity extends BaseActivity implements BankCardBaseIView, MyRefreshLayout.LayoutInterface<BankCardInfo> {


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
    protected BasePresenter createPersenter() {
        mBankCardPersenter = new BankCardPresenter(this);
        return mBankCardPersenter;
    }


    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("银行卡列表");

        mRefreshLayout = new MyRefreshLayout<BankCardInfo>(this, this);
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
    public void getBankListView(List<BankCardInfo> list) {
        mRefreshLayout.adapter.setNewData(list);
        mRefreshLayout.adapter.notifyDataSetChanged();
    }

    @Override
    public void removeSuccess() {
        ToastUtils.showToastOk(this,"解绑成功");
    }

    @Override
    public BaseQuickAdapter<BankCardInfo, ? extends BaseViewHolder> createAdapter() {
        BaseQuickAdapter adapter =  new BaseQuickAdapter<BankCardInfo, BaseViewHolder>(R.layout.user_center_item_banklist,new ArrayList<BankCardInfo>()) {
            @Override
            protected void convert(@NotNull BaseViewHolder helper, BankCardInfo item) {
                String bankNumber = item.getBank_number();
                if(bankNumber.length() > 4){
                    helper.setText(R.id.tv_card_number,String.format("**** **** **** %s",bankNumber.substring(bankNumber.length()-4)));
                }else{
                    helper.setText(R.id.tv_card_number,bankNumber);
                }
                helper.setText(R.id.tv_card_name, item.getBank_name());
                ImageView ivCard = helper.getView(R.id.iv_bank);
                if (!StringUtils.isEmpty(item.getBank_code())) {
                    String bankCode = item.getBank_code().toLowerCase();
                    int resId = getResources().getIdentifier("bank_icon_" + bankCode, "mipmap",
                            getPackageName());
                    try {
                        ivCard.setBackground(getResources().getDrawable(resId));
                    } catch (Exception e) {
                        ivCard.setBackground(getResources().getDrawable(R.mipmap.bank_icon_cgb));
                        e.printStackTrace();
                    }
                }

            }
        };
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                new BankCardOption(BankCardListActivity.this)
                        .showBottomView(new BankCardOption.BankCardOptionInter() {
                            @Override
                            public void unBind() {
                                BankCardInfo info = (BankCardInfo) adapter.getData().get(position);
                                mBankCardPersenter.deleteBank(info.getId());
                            }
                        });
                return false;
            }
        });
        return adapter;
    }

    @Override
    public void loadData(int page, int pageSize) {
        mBankCardPersenter.getBankList();
    }

}
