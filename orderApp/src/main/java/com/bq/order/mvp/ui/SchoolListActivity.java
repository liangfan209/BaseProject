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
import com.bq.comm_config_lib.request.Api;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.presenter.ProductPresenter;
import com.bq.order.requset.bean.ProductSearchBean;
import com.bq.order.requset.bean.ProfessionList;
import com.bq.order.requset.bean.SchoolInfo;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
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
@Route(path = AppArouter.ORDER_SCHOOL_LIST_ACTIVITY)
public class SchoolListActivity extends BaseActivity implements MyRefreshLayout.LayoutInterface<SchoolInfo>, ProductIview{
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
    private List<SchoolInfo> mSchoolInfoList = new ArrayList<>();

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_school_list;
    }

    @Override
    protected BasePresenter createPersenter() {
        return mProductPresenter = new ProductPresenter(this);
    }

    @Override
    protected void attach() {
        ProductSearchBean bean = new ProductSearchBean("湖北","武汉");
        mSearchStr = new Gson().toJson(bean);
        mTvTitle.setText("院校列表");
        mRefreshLayout = new MyRefreshLayout<String>(this, this);
        mRefreshLayout.setRefresh(true, true);
        mFltContent.addView(mRefreshLayout);
        initEditText();
        mRefreshLayout.adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                SchoolInfo info = (SchoolInfo) adapter.getData().get(position);
                ARouter.getInstance().build(AppArouter.ORDER_SCHOOL_DETAIL_ACTIVITY)
                        .withSerializable("mSchoolInfo",info).navigation();
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
        ProductSearchBean bean = new ProductSearchBean("湖北","武汉");
        bean.setName(titleSearch);
        mSearchStr = new Gson().toJson(bean);
        mRefreshLayout.autoRefresh();
    }


    @OnClick({R2.id.rlt_search, R2.id.iv_advertising})
    public void onViewClicked(View view) {
    }

    @Override
    public BaseQuickAdapter<SchoolInfo, ? extends BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<SchoolInfo, BaseViewHolder>(R.layout.order_item_school_type2, mSchoolInfoList) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper, SchoolInfo bean) {
                        ImageView iv = helper.getView(R.id.iv_icon);
                        Glide.with(iv).load(Api.BASE_API+bean.getLogo_url())
                                .apply(Utils.getRequestOptionRadus(iv.getContext(),0)).into(iv);
                        helper.setText(R.id.tv_title,bean.getName());
                        List<ProfessionList> production_list = bean.getProduction_list();
                        TextView tvRow1 = helper.getView(R.id.tv_row1);
                        TextView tvRow2 = helper.getView(R.id.tv_row2);
                        if(production_list.size() > 2){
                            tvRow1.setVisibility(View.VISIBLE);
                            tvRow2.setVisibility(View.VISIBLE);
                            tvRow1.setText(production_list.get(0).getName()+" "+production_list.get(0).getQuantity()+"个   "+production_list.get(1).getName()+" "+production_list.get(2).getQuantity()+"个");
                            if(production_list.size()  == 3){
                                tvRow2.setText(production_list.get(2).getName()+" "+production_list.get(2).getQuantity()+"个 ");
                            }else{
                                tvRow2.setText(production_list.get(2).getName()+" "+production_list.get(2).getQuantity()+"个   "+production_list.get(3).getName()+" "+production_list.get(3).getQuantity()+"个");
                            }
                        }else if(production_list.size() > 0){
                            tvRow1.setVisibility(View.VISIBLE);
                            tvRow2.setVisibility(View.GONE);
                            if(production_list.size()  == 1){
                                tvRow1.setText(production_list.get(0).getName()+" "+production_list.get(0).getQuantity()+"个 ");
                            }else{
                                tvRow1.setText(production_list.get(0).getName()+" "+production_list.get(0).getQuantity()+"个   "+production_list.get(1).getName()+" "+production_list.get(1).getQuantity()+"个");
                            }
                        }else{
                            tvRow1.setVisibility(View.GONE);
                            tvRow2.setVisibility(View.GONE);
                        }
                    }
                };
    }

    @Override
    public void getSchooListlView(List<SchoolInfo> list) {
        mRefreshLayout.addData(list);
    }

    @Override
    public void loadData(int page, int pageSize) {
        mProductPresenter.getSearchSchoolList(page,mSearchStr);
    }
}
