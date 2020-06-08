package com.fan.baseuilibrary.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/22/022
 * 版权：
 */
public class MyRefreshLayout<T> extends SmartRefreshLayout {

    public interface LayoutInter<T>{
        int getItemRes();
        void bindItem(BaseViewHolder helper, T item);
        void loadData(int page, int pageSize);
    }

    //下拉刷新组件
    protected RecyclerView mRecyclerView;
    protected StatusView mStatusView;
    protected int page = 1;
    protected int pageSize = 10;
    protected BaseQuickAdapter<T, ? extends BaseViewHolder> adapter;
    private List<T> data = new ArrayList<>();

    protected boolean refreshBoo = false;
    protected boolean loadmoreBoo = false;
    private LayoutInter<T> mInterface;


    public MyRefreshLayout(Context context, LayoutInter inter) {
        super(context);
        this.mInterface =inter;
        View view = LinearLayout.inflate(context, R.layout.layout_base_refresh,null);
        mRecyclerView = view.findViewById(R.id.base_recycler_view);
        mStatusView = view.findViewById(R.id.base_status_view);
        addView(view);
        initView();
    }


    private void initView() {
        this.setOnLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshBoo = false;
                loadmoreBoo = true;
                loadData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
            }
        });
        this.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initRefreshBoo();
                loadData();
            }
        });
        adapter = new BaseQuickAdapter<T, BaseViewHolder>(mInterface.getItemRes(), data) {
            @Override
            protected void convert(BaseViewHolder helper, T item) {
                mInterface.bindItem(helper, item);
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(adapter);
        initRefreshBoo();

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        loadData();
    }

    /**
     * 重新开始刷新，adapter能够添加新数据
     */
    protected void initRefreshBoo() {
        page = 1;
        refreshBoo = true;
        loadmoreBoo = false;
    }
    //填充数据
    public void addData(List<T> list) {
        if (adapter == null) {
            mStatusView.showEmpty();
            return;
        }
        if (refreshBoo) {
            if (list == null || list.size() == 0) {
                mStatusView.showContent(mRecyclerView);
                mStatusView.showEmpty();
                return;
            }

            adapter.removeAllFooterView();
            this.setEnableLoadmore(true);
            adapter.setNewData(list);
            mStatusView.showContent(mRecyclerView);


        } else if (loadmoreBoo) {
            if(list != null && list.size() == 0){
                View noDataView = LinearLayout.inflate(mRecyclerView.getContext(),
                        R.layout.layout_base_nodata, null);
                adapter.addFooterView(noDataView);
                this.setEnableLoadmore(false);
            }

            adapter.addData(list);
            mStatusView.showContent(mRecyclerView);
        }
    }

    protected void loadData() {
        mInterface.loadData(1, 10);
        page++;
    }


}
