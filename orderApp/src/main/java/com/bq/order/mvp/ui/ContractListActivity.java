package com.bq.order.mvp.ui;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.presenter.ProductPresenter;
import com.bq.order.requset.bean.ContractInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.MyRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;

/**
 * 文件名：
 * 描述： 合同列表
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
@Route(path = AppArouter.ORDER_CONTRACT_LIST_ACTIVITY)
public class ContractListActivity extends BaseActivity implements MyRefreshLayout.LayoutInterface<ContractInfo>, ProductIview{
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_right)
    TextView mTvRight;


    @BindView(R2.id.iv_advertising)
    ImageView mIvAdvertising;
    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;
    private String mSearchStr = "";

    private ProductPresenter mProductPresenter;
    private List<ContractInfo> mContractList = new ArrayList<>();

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_contract_list;
    }

    @Override
    protected BasePresenter createPersenter() {
        return mProductPresenter = new ProductPresenter(this);
    }

    @Override
    protected void attach() {
        mTvTitle.setText("我的合同");
        mRefreshLayout = new MyRefreshLayout<String>(this, this);
        mRefreshLayout.setRefresh(true, true);
        mFltContent.addView(mRefreshLayout);
        mRefreshLayout.adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ContractInfo info = (ContractInfo) adapter.getData().get(position);
                ARouter.getInstance().build(AppArouter.ORDER_SIGN_CONTRACT_ACTIVITY)
                        .withString("imgPath",info.getImg_url().get(0)).navigation();
            }
        });
    }





    @Override
    public BaseQuickAdapter<ContractInfo, ? extends BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<ContractInfo, BaseViewHolder>(R.layout.order_item_contract, mContractList) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper, ContractInfo bean) {
                        helper.setText(R.id.tv_pdf,bean.getName());
                        helper.setText(R.id.tv_time,bean.getCreate_time());
                    }
                };
    }


    @Override
    public void getContractListView(List<ContractInfo> list) {
        mRefreshLayout.addData(list);
    }

    @Override
    public void getContractListVErrorView() {
        mRefreshLayout.addData(null);
    }

    @Override
    public void loadData(int page, int pageSize) {
        String mSearchStr = "{}";
        mProductPresenter.getContractList();
    }
}
