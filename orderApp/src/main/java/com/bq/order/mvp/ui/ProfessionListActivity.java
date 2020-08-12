package com.bq.order.mvp.ui;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.presenter.ProductPresenter;
import com.bq.order.requset.bean.ProductSearchBean;
import com.bq.order.requset.bean.ProfessionInfo;
import com.bq.order.requset.bean.SelecterBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.utils.provinces.CityUtils;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.fan.baseuilibrary.view.MyRefreshLayout;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述： 学校列表
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
@Route(path = AppArouter.ORDER_PROFESSION_LIST_ACTIVITY)
public class ProfessionListActivity extends BaseActivity implements MyRefreshLayout.LayoutInterface<ProfessionInfo>, ProductIview{
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_right)
    TextView mTvRight;
    @BindView(R2.id.view_line)
    View mViewLine;
    @BindView(R2.id.rlt_title)
    RelativeLayout mRltTitle;
    @BindView(R2.id.tv_address_location)
    TextView mTvAddressLocation;
    @BindView(R2.id.det_search)
    DeletableEditText mDetSearch;
    @BindView(R2.id.rlt_search)
    RelativeLayout mRltSearch;
    @BindView(R2.id.iv_advertising)
    ImageView mIvAdvertising;
    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;
    private String mSearchStr = "";

    private ProductPresenter mProductPresenter;
    private List<ProfessionInfo> mSchoolInfoList = new ArrayList<>();

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_profession_list;
    }

    @Override
    protected BasePresenter createPersenter() {
        return mProductPresenter = new ProductPresenter(this);
    }

    @Override
    protected void attach() {
        ProductSearchBean bean = new ProductSearchBean(CommSpUtils.getLocation());
        mTvAddressLocation.setText(CommSpUtils.getLocation());
        mSearchStr = new Gson().toJson(bean);
        mTvTitle.setText("专业列表");
        mRefreshLayout = new MyRefreshLayout<String>(this, this);
        mRefreshLayout.setRefresh(true, true);
        mFltContent.addView(mRefreshLayout);
        initEditText();
        mRefreshLayout.adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ProfessionInfo info = (ProfessionInfo) adapter.getData().get(position);

                SelecterBean.SelectInfo selectInfo = new SelecterBean.SelectInfo(info.getId()+"",info.getName());
                ARouter.getInstance().build(AppArouter.ORDER_PRODUCT_LIST_ACTIVITY)
                        .withSerializable("mSelectInfo",selectInfo).navigation();
            }
        });
    }

    private void initEditText() {
        mDetSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    Utils.cancelFocus(mDetSearch);
                    updateSelf();
                }
                return true;
            }
        });
    }

    private void updateSelf() {
        String titleSearch = mDetSearch.getText().toString();
        ProductSearchBean bean = new ProductSearchBean(CommSpUtils.getLocation());
        bean.setName(titleSearch);
        mSearchStr = new Gson().toJson(bean);
        mRefreshLayout.autoRefresh();
    }


    @OnClick({R2.id.rlt_search, R2.id.iv_advertising,R2.id.tv_address_location,R2.id.iv_address_location})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.rlt_search){
            Utils.cancelFocus(mDetSearch);
            updateSelf();
        }else if(view.getId() == R.id.tv_address_location){
            CityUtils.getInstance(this).showPickerView(this, new CityUtils.CityCallBack() {
                @Override
                public void getCitys(String citys) {
                    CommSpUtils.saveLocaltion(citys);
                    mTvAddressLocation.setText(citys);
                    updateSelf();
                }
            });
        }else if(view.getId() == R.id.iv_address_location){
            mTvAddressLocation.setText("武汉市");
            CommSpUtils.saveLocaltion("武汉市");
            updateSelf();
        }
    }

    @Override
    public BaseQuickAdapter<ProfessionInfo, ? extends BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<ProfessionInfo, BaseViewHolder>(R.layout.order_item_profession, mSchoolInfoList) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper, ProfessionInfo bean) {
                        ImageView iv = helper.getView(R.id.iv_item);
                        Utils.showImage(bean.getIcons(),iv);
                        helper.setText(R.id.tv_profession_title,bean.getName());
                        helper.setText(R.id.tv_profession_remark,bean.getContent());
                    }
                };
    }

    @Override
    public void getProfessionListView(List<ProfessionInfo> list) {
        mRefreshLayout.addData(list);
    }

    @Override
    public void getProfessionListErrorView() {
        mRefreshLayout.addData(null);
    }

    @Override
    public void loadData(int page, int pageSize) {
        mProductPresenter.getprofessionList(page,mSearchStr);
    }
}
