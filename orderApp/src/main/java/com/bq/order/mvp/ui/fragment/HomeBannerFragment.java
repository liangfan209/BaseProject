package com.bq.order.mvp.ui.fragment;

import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.ui.adapter.BannerAdapter;
import com.bq.order.mvp.ui.hodler.NewTypeViewHolder;
import com.bq.order.requset.bean.BannerData;
import com.bq.order.requset.bean.ProductSearchBean;
import com.bq.order.requset.bean.ProductTypeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
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
public class HomeBannerFragment extends BaseFragment {
    @BindView(R2.id.banner_view)
    BannerViewPager mBannerView;
    @BindView(R2.id.rv_product_type)
    RecyclerView mRvProductType;
    @BindView(R2.id.banner_advertising)
    BannerViewPager mBannerAdvertising;

    private String[] productTypeStr = {"专升本","高起专","考研","资格证"};
    private int[] productTypeInts = {R.mipmap.icon_benke,R.mipmap.icon_zhuanke,R.mipmap.icon_kaoyan,R.mipmap.icon_zigezheng};

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_fragment_home_banner;
    }



    @Override
    protected void attach() {
        initHomeBanner();
        initRecycleView();
        //初始化广告banner
        initAdvertisingBeanner();
    }

    private void initAdvertisingBeanner() {
        mBannerAdvertising
                .setScrollDuration(600)
                .setLifecycleRegistry(getLifecycle())
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSlideMode(IndicatorSlideMode.WORM)
                .setInterval(3000)
                .setIndicatorGravity(IndicatorGravity.END)
                .setCanLoop(true)
                .setAdapter(new BannerAdapter())
                .setOnPageClickListener(position -> {
                }).create();

        ArrayList<BannerData> dataList = new ArrayList<BannerData>();
        BannerData b1 = new BannerData(R.mipmap.icon_advertising, 1);
        BannerData b2 = new BannerData(R.mipmap.icon_advertising, 1);
        dataList.add(b1);
        dataList.add(b2);
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
                bean.setCategory(productTypeStr[position]);
                String serachInfo = new Gson().toJson(bean);
                ARouter.getInstance().build(AppArouter.ORDER_PRODUCT_LIST_ACTIVITY)
                        .withString("mSearchInfo",serachInfo).navigation();
//                parentFragment.updateTest();
            }
        });
    }

    private void initHomeBanner() {
        BannerAdapter homeAdapter = new BannerAdapter(new NewTypeViewHolder.HolderInter() {
            @Override
            public void createVideo(StandardGSYVideoPlayer palyer) {
//                createVideoView(palyer);
            }
        });

        mBannerView
                .setScrollDuration(600)
                .setLifecycleRegistry(getLifecycle())
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSlideMode(IndicatorSlideMode.WORM)
                .setInterval(5000)
                .setIndicatorGravity(IndicatorGravity.END)
                .setCanLoop(true)
                .setAdapter(homeAdapter)
                .setOnPageClickListener(position -> {
                    String serachInfo = new Gson().toJson(new ProductSearchBean(CommSpUtils.getLocation()));
                    ARouter.getInstance().build(AppArouter.ORDER_PRODUCT_LIST_ACTIVITY)
                            .withString("mSearchInfo",serachInfo).navigation();
                }).create();

        ArrayList<BannerData> dataList = new ArrayList<BannerData>();
//        BannerData b1 = new BannerData("https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png",
//                BannerData.TYPE_NEW);
        BannerData b2 = new BannerData("http://img6.16fan.com/201510/11/005258wdngg6rv0tpn8z9z.jpg", 1);
        BannerData b3 = new BannerData("http://img6.16fan.com/201510/11/013553aj3kp9u6iuz6k9uj.jpg", 1);
        BannerData b4 = new BannerData("http://img6.16fan.com/201510/11/011753fnanichdca0wbhxc.jpg", 1);
        BannerData b5 = new BannerData("http://img6.16fan.com/201510/11/011819zbzbciir9ctn295o.jpg", 1);
//        dataList.add(b1);
        dataList.add(b2);
        dataList.add(b3);
        dataList.add(b4);
        dataList.add(b5);
        mBannerView.refreshData(dataList);
    }
}
