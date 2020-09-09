package com.bq.order.mvp.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
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
import com.bq.order.mvp.ui.hodler.ProductType;
import com.bq.order.requset.bean.ProductInfo;
import com.bq.utilslib.AppUtils;
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
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/30
 * 版权：
 */
public class ProductListFragment extends BaseFragment implements MyRefreshLayout.LayoutInterface<ProductInfo>
        , ProductIview {

    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;
    private List<ProductInfo> mlist = new ArrayList<>();
//    private int mtype = 0;
    private ProductType mProductType;
    private ProductPresenter mProductPresenter;
    private String mSearchInfo;


    public static ProductListFragment getInstance(ProductType productType) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("productType", productType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.laoyut_frame_layout;
    }

    @Override
    protected BasePresenter createPersenter() {
//        ProductSearchBean productSearchBean = new ProductSearchBean("湖北","武汉");
//        mSearchInfo = new Gson().toJson(productSearchBean);
        mProductPresenter = new ProductPresenter(this);
        return mProductPresenter;
    }

    @Override
    protected void attach() {
        mProductType = (ProductType) getArguments().get("productType");
        mSearchInfo = mProductType.searchInfo;
        mRefreshLayout = new MyRefreshLayout<String>(this.getContext(), this);
        mRefreshLayout.setRefresh(true, true);
        mFltContent.addView(mRefreshLayout);

        mRefreshLayout.adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ProductInfo info = (ProductInfo) adapter.getData().get(position);
                ARouter.getInstance().build(AppArouter.ORDER_PRODUCT_DETAIL_ACTIVITY)
                        .withString("mProductId",info.getId()).navigation();
            }
        });
    }

    @Override
    public BaseQuickAdapter<ProductInfo, ? extends BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<ProductInfo, BaseViewHolder>(R.layout.order_item_product,mlist) {
            @Override
            protected void convert(@NotNull BaseViewHolder helper, ProductInfo bean) {
                ImageView iv = helper.getView(R.id.iv_item);
//                Glide.with(iv).load(bean.getThumbnail())
//                        .apply(Utils.getRequestOptionRadus(iv.getContext(),0)).into(iv);
                Utils.showImage(bean.getThumbnail(),iv,3);
                helper.setText(R.id.tv_bottom_right,"起");

                helper.setText(R.id.tv_product_title,bean.getTitle());
                helper.setText(R.id.tv_school,bean.getSchool_name());
                helper.setText(R.id.tv_profession,bean.getMajor_name());
                helper.setText(R.id.tv_duration,bean.getDuration());
                helper.setText(R.id.tv_address,bean.getSchool_city());
                helper.setText(R.id.tv_brand,bean.getBrand_name());
                helper.setText(R.id.tv_product,bean.getCategory());
                helper.setText(R.id.tv_price, AppUtils.getDouble2(bean.getSale_price()));
                helper.setText(R.id.tv_orgamnization, bean.getAgent_name()+"为您服务");
            }
        };
    }

    @Override
    public void getProductListView(List<ProductInfo> list) {
        mRefreshLayout.addData(list);
    }

    @Override
    public void getProductListErrorView() {
        mRefreshLayout.addData(null);
    }

    @Override
    public void loadData(int page, int pageSize) {
        mProductPresenter.getSearchProductList(page,mSearchInfo);
    }


    public void updateFragment(String searchInfo){
        this.mSearchInfo = searchInfo;
        mRefreshLayout.autoRefresh();
    }
}
