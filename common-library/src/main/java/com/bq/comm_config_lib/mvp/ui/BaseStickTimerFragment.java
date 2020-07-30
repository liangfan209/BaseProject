package com.bq.comm_config_lib.mvp.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.R;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.utilslib.AppUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.StatusView;
import com.fan.baseuilibrary.view.picker.PickerUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/21
 * 版权：
 */
public abstract class BaseStickTimerFragment<T> extends BaseFragment {
    //下拉刷新组件
    public RecyclerView mRecyclerView;
    public StatusView mStatusView;
    public SmartRefreshLayout mRefreshLayout;
    public TextView mTvChangeTime;
    public TextView mTvIn;
    public TextView mTvOut;
    public RelativeLayout floadView;
    public ImageView mIvJiantou;

    protected BaseQuickAdapter<T, ? extends BaseViewHolder> adapter;
    public int mPage = 1;
    protected int pageSize = 10;
    protected boolean refreshBoo = false;
    protected boolean loadmoreBoo = false;
    protected LinearLayoutManager lm;
    protected int floatViewHeight;
    Unbinder unbinder;
    //进来默认是当前月份
    protected List<T> data = new ArrayList<>();
    protected String selectTime = "";
    public LinearLayout lltNoDataView;
    TextView selectTimeTv;
    public String mFloadColor = "#dddddd";

    public int mBottom = -20;


    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_stick_timer;
    }

    @Override
    protected void attach() {
        mRecyclerView = contentView.findViewById(R.id.base_recycler_view);
        mStatusView = contentView.findViewById(R.id.base_status_view);
        mRefreshLayout= contentView.findViewById(R.id.base_refresh_layout);
        mTvChangeTime= contentView.findViewById(R.id.tv_change_time);
        mTvIn= contentView.findViewById(R.id.tv_in);
        mTvOut= contentView.findViewById(R.id.tv_out);
        floadView= contentView.findViewById(R.id.rlt_month);
        mIvJiantou= contentView.findViewById(R.id.iv_jiantou);
        initView();
    }



    private void initView() {
        mTvChangeTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                selectTimer();
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshBoo = false;
                loadmoreBoo = true;
                loadData(mPage,packageData(getFilterTime(selectTime)));
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initRefreshBoo();
                loadData(mPage,packageData(getFilterTime(selectTime)));
            }
        });
        adapter = new BaseQuickAdapter<T, BaseViewHolder>(getItemRes(), data) {
            @Override
            protected void convert(BaseViewHolder helper, T item) {
                helper.getView(R.id.tv_change_time).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        selectTimer();
                    }
                });
                bindItem(helper, item);
            }
        };

        lm = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(lm);
        mRefreshLayout.setRefreshHeader(Utils.createFreshHeader(this.getContext()));
        mRefreshLayout.setRefreshFooter(Utils.createFreshFooter(this.getContext()));
        mRefreshLayout.setEnableLoadmoreWhenContentNotFull(true);
        mRecyclerView.setAdapter(adapter);
        mRefreshLayout.setEnableRefresh(false);
        initRefreshBoo();
        loadData(mPage,packageData(getFilterTime(selectTime)));
        initRecycleView();
    }


    private void initRecycleView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                floatViewHeight = AppUtils.dp2px(recyclerView.getContext(), 50);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (adapter.getData().size() == 0)
                    return;
                int firstVisibleItemPosition = lm.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                floadView.setVisibility(dy == 0 ? View.GONE : View.VISIBLE);
                int nextVisibleItemPosition = firstVisibleItemPosition;
                while (nextVisibleItemPosition < lastVisibleItemPosition) {
                    T item = adapter.getData().get(nextVisibleItemPosition);
                    if (getMonthDataDto(item) != null) {
                        break;
                    }
                    nextVisibleItemPosition++;
                }
                View nextView = lm.findViewByPosition(nextVisibleItemPosition);
                if (nextView.getTop() < floatViewHeight && nextView.getTop() >= 0) {
                    floadView.setY(-(floatViewHeight - nextView.getTop()));
                    updateHeadView(firstVisibleItemPosition);
                } else {
                    floadView.setY(0);
                    //更新当前的数据
                    updateHeadView(firstVisibleItemPosition);

                }
            }
        });
    }

    public void setFloadViewColor(String color){
        this.mFloadColor = color;
    }

    //填充数据
    protected void addData(List<T> list) {
        if (adapter == null) {
            mStatusView.showEmpty();
            return;
        }
        if (refreshBoo) {
            if (list == null || list.size() == 0) {
                if (lltNoDataView == null) {
                    adapter.setNewData(list);
                    lltNoDataView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_base_nodata_stick,
                            null);
                    selectTimeTv = lltNoDataView.findViewById(R.id.tv_change_time);
                    selectTimeTv.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            selectTimer();
                        }
                    });
                    WindowManager windowManager = BaseStickTimerFragment.this.getActivity().getWindowManager();
                    int height = windowManager.getDefaultDisplay().getHeight();
                    int[] position = new int[2];
                    mRecyclerView.getLocationInWindow(position);
                    lltNoDataView.findViewById(R.id.rlt_month).setBackgroundColor(Color.parseColor(mFloadColor));
                    lltNoDataView.findViewById(R.id.rlt_month).setVisibility(View.GONE);
                    lltNoDataView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            height - position[1]- AppUtils.dp2px(lltNoDataView.getContext(),mBottom)));
                    adapter.addHeaderView(lltNoDataView);
                    mStatusView.showContent(mRecyclerView);
                }
                if (selectTimeTv != null)
                    selectTimeTv.setText(StringUtils.isEmpty(selectTime) ? "本月" : selectTime);
                return;
            }
            adapter.removeAllHeaderView();
            lltNoDataView = null;
            adapter.setNewData(list);
            adapter.removeAllFooterView();
            mRefreshLayout.setEnableLoadmore(true);
            mStatusView.showContent(mRecyclerView);
        } else if (loadmoreBoo) {

            if (list != null && list.size() == 0) {
                View noDataView = LinearLayout.inflate(mRecyclerView.getContext(),
                        R.layout.layout_base_nodata, null);
                if(adapter.getData().size() != 0){
                    adapter.addFooterView(noDataView);
                }
                mRefreshLayout.setEnableLoadmore(false);
            }

            adapter.addData(list);
            mStatusView.showContent(mRecyclerView);
        }

    }

    protected abstract Object getMonthDataDto(T item);

    protected abstract void updateHeadView(int firstVisibleItemPosition);

    protected abstract void loadData(int page,String info);

    protected abstract int getItemRes();

    protected abstract void bindItem(BaseViewHolder helper, T item);

    private void selectTimer() {
        new PickerUtils().showTimePick(this.getContext(), new PickerUtils.TimeCallBack() {
            @Override
            public void timeCallBack(String date) {
                selectTime = date;
                mTvChangeTime.setText(selectTime);
                initRefreshBoo();
                loadData(1,packageData(getFilterTime(date)));
            }
        }, mTvChangeTime.getText().toString());
    }

    public String packageData(String[] strs) {
//        String info;
//        if (strs == null) {
//            info = new Gson().toJson(new AssetServerBean());
//        } else {
//            info = new Gson().toJson(new AssetServerBean(strs[0], strs[1]));
//        }
//        return info;
        return null;
    }


    /**
     * 重新开始刷新，adapter能够添加新数据
     */
    protected void initRefreshBoo() {
        mPage = 1;
        refreshBoo = true;
        loadmoreBoo = false;
    }


    public static String[] getFilterTime(String selectDate){
        if(StringUtils.isEmpty(selectDate)){
            return null;
        }
        String[] strs = new String[2];
        if(selectDate.contains("至")){
            strs[0] = selectDate.split("至")[0].trim()+" 00:00:00";
            strs[1] = selectDate.split("至")[1].trim()+" 23:59:59";
        }else{
            Calendar calendar = Calendar.getInstance();
            int date1 =  calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            if(selectDate.length() == 7){
                strs[0] =  selectDate+"-01 00:00:00";
                strs[1] =  selectDate+"-"+date1+" 23:59:59";
            }else if(selectDate.length() == 10){
                strs[0] =  selectDate+" 00:00:00";
                strs[1] =  selectDate+" 23:59:59";
            }
        }
        return strs;
    }
}
