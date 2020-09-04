package com.bq.order.mvp.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.presenter.ProductPresenter;
import com.bq.order.requset.bean.ContractInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import skin.support.content.res.SkinCompatResources;

/**
 * 文件名：
 * 描述： 合同列表
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
@Route(path = AppArouter.ORDER_CONTRACT_LIST_ACTIVITY)
public class ContractListActivity extends BaseActivity implements ProductIview {
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_right)
    TextView mTvRight;

    @BindView(R2.id.rcv_list)
    RecyclerView mRcvList;
    @BindView(R2.id.nsv)
    NestedScrollView mNsv;

    private ProductPresenter mProductPresenter;
    private List<ContractInfo> mContractList = new ArrayList<>();
    BaseQuickAdapter<ContractInfo, BaseViewHolder> adapter;

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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRcvList.setLayoutManager(gridLayoutManager);
        adapter = new
                BaseQuickAdapter<ContractInfo, BaseViewHolder>(R.layout.order_item_contract, mContractList) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper, ContractInfo bean) {
                        helper.setText(R.id.tv_pdf, bean.getContract_name());
                        helper.setText(R.id.tv_time, bean.getCreate_time());
                        TextView tvType = helper.getView(R.id.tv_type);
                        tvType.setText(bean.getStatus_name());
                        if("已签署".equals(bean.getStatus_name())){
                            tvType.setTextColor(getResources().getColor(R.color.ui_txt_hint_color));
                            tvType.setBackground(getResources().getDrawable(R.drawable.shap_gray_radius_5));
                        }else{
                            tvType.setTextColor(SkinCompatResources.getColor(ContractListActivity.this, com.fan.baseuilibrary.R.color.ui_primary_color));
                            tvType.setBackground(getResources().getDrawable(R.drawable.shap_green_radius_5));
                        }
                        ImageView ivcontract = helper.getView(R.id.iv_contract);
                        if (bean.getUrl().size() > 0)
                            Utils.showImage(bean.getImg_url().get(0), ivcontract);
                    }
                };
        mRcvList.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ContractInfo info = (ContractInfo) adapter.getData().get(position);
                if("已签署".equals(info.getStatus_name())){
                    ARouter.getInstance().build(AppArouter.ORDER_SIGN_CONTRACT_ACTIVITY)
                            .withSerializable("mContractInfo",info)
                            .navigation();
                }else{
                    ARouter.getInstance().build(AppArouter.ORDER_SIGN_CONTRACT_ACTIVITY)
                            .withInt("sign",1)
                            .withSerializable("mContractInfo",info)
                            .navigation();
                }
            }
        });
        mProductPresenter.getContractList();
    }


    @Override
    public void getContractListView(List<ContractInfo> list) {
        if(list.size() == 0){
            mNsv.setVisibility(View.VISIBLE);
            mRcvList.setVisibility(View.GONE);
        }else{
            mNsv.setVisibility(View.GONE);
            mRcvList.setVisibility(View.VISIBLE);
        }
        adapter.setNewData(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getContractListVErrorView() {
//        mRefreshLayout.addData(null);
        mNsv.setVisibility(View.VISIBLE);
        mRcvList.setVisibility(View.GONE);
    }

}
