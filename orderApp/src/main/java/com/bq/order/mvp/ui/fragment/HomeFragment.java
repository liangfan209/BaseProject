package com.bq.order.mvp.ui.fragment;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;
import com.bq.comm_config_lib.request.Api;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.presenter.ProductPresenter;
import com.bq.order.mvp.ui.ProductIview;
import com.bq.order.requset.bean.ProductInfo;
import com.bq.order.requset.bean.ProductSearchBean;
import com.bq.order.requset.bean.SchoolInfo;
import com.bq.utilslib.AppUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.CircleImageView;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
@Route(path = AppArouter.ORDER_HOME_FRAGMENT)
public class HomeFragment extends BaseFragment implements  ProductIview {
    @BindView(R2.id.tv_address_location)
    TextView mTvAddressLocation;
    @BindView(R2.id.det_search)
    DeletableEditText mDetSearch;
    @BindView(R2.id.rlt_search)
    RelativeLayout mRltSearch;
    @BindView(R2.id.flt_home_banner)
    FrameLayout mFltHomeBanner;
    @BindView(R2.id.tv_hot_school)
    TextView mTvHotSchool;
    @BindView(R2.id.rv_school)
    RecyclerView mRvSchool;
    @BindView(R2.id.rv_news)
    RecyclerView mRvNews;
    @BindView(R2.id.tv_hot_profession)
    TextView mTvHotProfession;
    @BindView(R2.id.rv_profession)
    RecyclerView mRvProfession;

    private HomeBannerFragment mHomeBannerFragment;
    private ProductPresenter mProductPresenter;
    BaseQuickAdapter mSchoolAdapter;
    BaseQuickAdapter mProductAdapter;
    List<SchoolInfo> mHostSchoollist = new ArrayList<>();
    List<ProductInfo> mHostProductlist = new ArrayList<>();

    @Override
    protected BasePresenter createPersenter() {
        mProductPresenter = new ProductPresenter(this);
        return mProductPresenter;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_fragment_home;
    }

    @Override
    protected void attach() {
        mHomeBannerFragment = new HomeBannerFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flt_home_banner,mHomeBannerFragment);
        fragmentTransaction.commit();
        initHotSchoolView();
        intNewsView();
        intHotProfessionView();

        String searchStr = new Gson().toJson(new ProductSearchBean("湖北","武汉"));
        mProductPresenter.getHotSchool(searchStr);
        mProductPresenter.getHotProductList(searchStr);
    }

    private void intHotProfessionView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        mRvProfession.setLayoutManager(linearLayoutManager);

        mProductAdapter = new
                BaseQuickAdapter<ProductInfo, BaseViewHolder>(R.layout.order_item_product, mHostProductlist) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper,
                                           ProductInfo bean) {
                        ImageView iv = helper.getView(R.id.iv_item);
                        Glide.with(iv).load(Api.BASE_API+bean.getThumbnail())
                                .apply(Utils.getRequestOptionRadus(iv.getContext(),0)).into(iv);
                        helper.setText(R.id.tv_title,bean.getTitle());
                        helper.setText(R.id.tv_school,bean.getSchool_name());
                        helper.setText(R.id.tv_profession,bean.getMajor_name());
                        helper.setText(R.id.tv_duration,bean.getDuration());
                        helper.setText(R.id.tv_address,bean.getSchool_city());
                        helper.setText(R.id.tv_brand,bean.getBrand_name());
                        helper.setText(R.id.tv_product,bean.getProduction_name());
                        helper.setText(R.id.tv_price, AppUtils.getDouble2(bean.getSale_price()));

                    }
                };
        mRvProfession.setAdapter(mProductAdapter);

        mProductAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ARouter.getInstance().build(AppArouter.ORDER_PRODUCT_LIST_ACTIVITY).navigation();
            }
        });
    }

    @Override
    public void getProductListView(List<ProductInfo> list) {
        mHostProductlist = list;
        mProductAdapter.setNewData(mHostProductlist);
        mProductAdapter.notifyDataSetChanged();
    }

    private void initHotSchoolView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        mRvSchool.setLayoutManager(gridLayoutManager);
        mSchoolAdapter = new
                BaseQuickAdapter<SchoolInfo, BaseViewHolder>(R.layout.order_item_school, mHostSchoollist) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper,
                                           SchoolInfo bean) {
                        helper.setText(R.id.tv_name,bean.getName());
                        helper.setText(R.id.tv_remark,bean.getContent());
                        CircleImageView iv = helper.getView(R.id.iv_icon);
                        Glide.with(iv).load(Api.BASE_API+bean.getLogo_url())
                                .apply(Utils.getRequestOptionRadus(iv.getContext(),0)).into(iv);
                    }
                };
        mRvSchool.setAdapter(mSchoolAdapter);

        mSchoolAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ARouter.getInstance().build(AppArouter.ORDER_SCHOOL_DETAIL_ACTIVITY).navigation();
            }
        });
    }
    @Override
    public void getSchooListlView(List<SchoolInfo> list) {
        mHostSchoollist = list;
        mSchoolAdapter.setNewData(mHostSchoollist);
        mSchoolAdapter.notifyDataSetChanged();
    }

    private void intNewsView() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL,false);
        mRvNews.setLayoutManager(linearLayoutManager);

        BaseQuickAdapter adapter = new
                BaseQuickAdapter<String, BaseViewHolder>(R.layout.order_item_news, list) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper,
                                           String bean) {
                    }
                };
        mRvNews.setAdapter(adapter);
    }


    @OnClick({R2.id.rlt_search, R2.id.tv_hot_school, R2.id.tv_hot_profession})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.tv_hot_school){
            ARouter.getInstance().build(AppArouter.ORDER_SCHOOL_LIST_ACTIVITY).navigation();
        }else if(view.getId() == R.id.tv_hot_profession){
            ARouter.getInstance().build(AppArouter.ORDER_PRODUCT_LIST_ACTIVITY).navigation();
        }
    }

}
