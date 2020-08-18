package com.bq.order.mvp.ui.fragment;

import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.presenter.ProductPresenter;
import com.bq.order.mvp.ui.ProductIview;
import com.bq.order.mvp.ui.adapter.BannerAdapter;
import com.bq.order.mvp.ui.hodler.NewTypeViewHolder;
import com.bq.order.requset.bean.BannerData;
import com.bq.order.requset.bean.BannerInfo;
import com.bq.order.requset.bean.ProductSearchBean;
import com.bq.order.requset.bean.ProductTypeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
public class HomeBannerFragment extends BaseFragment implements ProductIview {
    @BindView(R2.id.banner_view)
    BannerViewPager mBannerView;
    @BindView(R2.id.rv_product_type)
    RecyclerView mRvProductType;
    @BindView(R2.id.banner_advertising)
    BannerViewPager mBannerAdvertising;
    //undergraduate -> 专升本 、 specialty -> 高起专 、 graduate -> 考研 、 qualification ->
    private String[] professionStr ={"undergraduate","specialty","graduate","qualification"};
    private String[] productTypeStr = {"专升本","高起专","考研","资格证"};
    private int[] productTypeInts = {R.mipmap.icon_benke,R.mipmap.icon_zhuanke,R.mipmap.icon_kaoyan,R.mipmap.icon_zigezheng};

    private ProductPresenter mProductPresenter;
    @Override
    protected BasePresenter createPersenter() {
        return mProductPresenter = new ProductPresenter(this);
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_fragment_home_banner;
    }



    @Override
    protected void attach() {
        initRecycleView();
        updateView();
    }

    public void updateView(){
        mProductPresenter.getHomeBanner("index_banner");
        mProductPresenter.getHomeBanner("index_mid");
    }


    @Override
    public void getBannerList(String type, List<BannerInfo> list) {
        if("index_banner".equals(type)){
            initHomeBanner(list);
        }else if("index_mid".equals(type)){
            initAdvertisingBeanner(list);
        }
    }



    private void initAdvertisingBeanner(List<BannerInfo> list) {
        mBannerAdvertising
                .setScrollDuration(600)
                .setLifecycleRegistry(getLifecycle())
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setInterval(3000)
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setCanLoop(true)
                .setAdapter(new BannerAdapter())
                .setOnPageClickListener(position -> {
                    BannerData info = (BannerData) mBannerView.getData().get(position);
//                    ARouter.getInstance().build(AppArouter.H5_ACTIVITY)
//                            .withString("h5url",info.getUrl()).navigation();
                    Utils.goCustomActivity(this.getActivity(),info.getUrl());
                }).create();

        ArrayList<BannerData> dataList = new ArrayList<BannerData>();
        for (BannerInfo bannerInfo : list) {
            dataList.add(new BannerData(bannerInfo.getId(),bannerInfo.getName(),bannerInfo.getThumbnail(),
                    bannerInfo.getUrl(),1));
        }
        mBannerAdvertising.refreshData(dataList);
    }

    private void initRecycleView() {
        List<ProductTypeBean.ProductTypeInfo> list = new ArrayList<>();
        for (int i = 0; i < productTypeStr.length; i++) {
            list.add(new ProductTypeBean.ProductTypeInfo(productTypeInts[i],productTypeStr[i]));
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 4);
        mRvProductType.setLayoutManager(gridLayoutManager);
        BaseQuickAdapter adapter = new
                BaseQuickAdapter<ProductTypeBean.ProductTypeInfo, BaseViewHolder>(R.layout.order_item_product_type, list) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper,
                                           ProductTypeBean.ProductTypeInfo bean) {
                        ImageView iv = helper.getView(R.id.iv_img);
                        iv.setBackground(getResources().getDrawable(bean.getId()));
                        helper.setText(R.id.tv_name,bean.getName());
                    }
                };
        mRvProductType.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ProductSearchBean bean = new ProductSearchBean();
                ARouter.getInstance().build(AppArouter.ORDER_PRODUCT_LIST_ACTIVITY)
                        .withString("forType",professionStr[position]).navigation();
//                parentFragment.updateTest();
            }
        });
    }

    private void initHomeBanner(List<BannerInfo> list) {
        BannerAdapter homeAdapter = new BannerAdapter(new NewTypeViewHolder.HolderInter() {
            @Override
            public void createVideo(StandardGSYVideoPlayer palyer) {
            }
        });

        mBannerView
                .setScrollDuration(600)
                .setLifecycleRegistry(getLifecycle())
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setInterval(5000)
                .setIndicatorGravity(IndicatorGravity.CENTER)
//                .setIndicatorView(10,10)
                .setIndicatorVisibility(View.VISIBLE)
                .setCanLoop(true)
                .setAdapter(homeAdapter)
                .setOnPageClickListener(position -> {
//                    String serachInfo = new Gson().toJson(new ProductSearchBean(CommSpUtils.getLocation()));
//                    ARouter.getInstance().build(AppArouter.ORDER_PRODUCT_LIST_ACTIVITY)
//                            .withString("mSearchInfo",serachInfo).navigation();
                    BannerData info = (BannerData) mBannerView.getData().get(position);
//                    ARouter.getInstance().build(AppArouter.H5_ACTIVITY)
//                            .withString("h5url",info.getUrl()).navigation();
                    Utils.goCustomActivity(this.getActivity(),info.getUrl());
                }).create();

        ArrayList<BannerData> dataList = new ArrayList<BannerData>();
        for (BannerInfo bannerInfo : list) {
            dataList.add(new BannerData(bannerInfo.getId(),bannerInfo.getName(),bannerInfo.getThumbnail(),
                    bannerInfo.getUrl(),1));
        }
        mBannerView.refreshData(dataList);
    }
}
