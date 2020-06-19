package com.bq.user_center.mvp.address.ui;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.mvp.bankcard.presenter.BankCardPresenter;
import com.bq.user_center.requset.bean.BankCard;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.MyRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/18
 * 版权：
 */
@Route(path = AppArouter.USER_CENTER_ADDRESS_LIST)
public class AddressListActivity extends BaseAcitivty implements MyRefreshLayout.LayoutInterface<String> {

    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;
    BankCardPresenter mBankCardPersenter;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.bt_add_address)
    Button mBtAddAddress;


    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_activity_banklist;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("地址管理");
        mRefreshLayout = new MyRefreshLayout<BankCard>(this, this);
        mRefreshLayout.setbackgroundColor(R.color.ui_recycleview_color);
        mFltContent.addView(mRefreshLayout);

        mRefreshLayout.adapter.setOnItemClickListener((adapter, view, position) -> {
            ARouter.getInstance().build(AppArouter.USER_CENTER_ADDRESS_OPTION)
                    .withInt("optionType", AddressOptionActivity.ADDRESS_EDIT).navigation();
        });
    }

    @Override
    public int getItemRes() {
        return R.layout.user_center_item_addresslist;
    }

    @Override
    public void bindItem(BaseViewHolder helper, String item) {
    }

    @Override
    public void loadData(int page, int pageSize) {
        ArrayList<String> as = new ArrayList<String>();
        for (int i = 0; i <10 ; i++) {
            as.add("");
        }
        mRefreshLayout.addData(as);
    }

    @OnClick(R2.id.bt_add_address)
    public void onViewClicked(View view) {
        if(view.getId() == R.id.bt_add_address){
            ARouter.getInstance().build(AppArouter.USER_CENTER_ADDRESS_OPTION)
                    .withInt("optionType", AddressOptionActivity.ADDRESS_ADD).navigation();
        }
    }
}
