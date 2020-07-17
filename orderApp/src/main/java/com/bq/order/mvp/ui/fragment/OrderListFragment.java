package com.bq.order.mvp.ui.fragment;

import android.widget.FrameLayout;

import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;
import com.bq.order.R;
import com.bq.order.R2;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.MyRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/17
 * 版权：
 */

public class OrderListFragment extends BaseFragment implements MyRefreshLayout.LayoutInterface<String> {

    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;
    private List<String> mlist = new ArrayList<>();

    @Override
    protected int getContentViewLayout() {
        return R.layout.laoyut_frame_layout;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        mRefreshLayout = new MyRefreshLayout<String>(this.getContext(), this);
        mRefreshLayout.setRefresh(false, true);
        mFltContent.addView(mRefreshLayout);
    }

    @Override
    public BaseQuickAdapter<String, ? extends BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<String, BaseViewHolder>(R.layout.order_item_order,mlist) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {

            }
        };
    }

    @Override
    public void loadData(int page, int pageSize) {
        mlist.add("1");
        mlist.add("1");
        mlist.add("1");
        mlist.add("1");
        mRefreshLayout.adapter.setNewData(mlist);
        mRefreshLayout.adapter.notifyDataSetChanged();
    }
}
