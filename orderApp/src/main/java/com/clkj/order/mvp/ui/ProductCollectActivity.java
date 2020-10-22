package com.clkj.order.mvp.ui;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.request.LoginBean;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.utilslib.AppUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.clkj.order.R;
import com.clkj.order.R2;
import com.clkj.order.mvp.presenter.ProductPresenter;
import com.clkj.order.mvp.ui.hodler.ProductType;
import com.clkj.order.requset.bean.ProductInfo;
import com.fan.baseuilibrary.view.ChooseShare;
import com.fan.baseuilibrary.view.MyRefreshLayout;
import com.google.gson.Gson;
import com.umeng.socialize.bean.SHARE_MEDIA;

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

@Route(path = AppArouter.ORDER_COLLECT_ACTIVITY)
public class ProductCollectActivity extends BaseActivity implements MyRefreshLayout.LayoutInterface<ProductInfo>
        , ProductIview {

    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    private List<ProductInfo> mlist = new ArrayList<>();
    private ProductType mProductType;
    private ProductPresenter mProductPresenter;
    private String mSearchInfo = "{}";


    //要删除的id
    private int deleteId = -1;


    @Override
    protected int getContentViewLayout() {
        return R.layout.laoyut_collect_frame_layout;
    }

    @Override
    protected BasePresenter createPersenter() {
        mProductPresenter = new ProductPresenter(this);
        return mProductPresenter;
    }

    @Override
    protected void attach() {
        mRefreshLayout = new MyRefreshLayout<String>(this, this);
        mRefreshLayout.setRefresh(true, true);
        mFltContent.addView(mRefreshLayout);
        mTvTitle.setText("收藏列表");
        mRefreshLayout.adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ProductInfo info = (ProductInfo) adapter.getData().get(position);
                ARouter.getInstance().build(AppArouter.ORDER_PRODUCT_DETAIL_ACTIVITY)
                        .withString("mProductId", info.getId()).navigation();
            }
        });
    }

    @Override
    public BaseQuickAdapter<ProductInfo, ? extends BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<ProductInfo, BaseViewHolder>(R.layout.order_item_collect_product, mlist) {
            @Override
            protected void convert(@NotNull BaseViewHolder helper, ProductInfo bean) {
                ImageView iv = helper.getView(R.id.iv_item);
//                Glide.with(iv).load(bean.getThumbnail())
//                        .apply(Utils.getRequestOptionRadus(iv.getContext(),0)).into(iv);
                Utils.showImage(bean.getThumbnail(), iv, 3);
                helper.setText(R.id.tv_bottom_right, "起");

                helper.setText(R.id.tv_product_title, bean.getTitle());
                helper.setText(R.id.tv_school, bean.getSchool_name());
                helper.setText(R.id.tv_profession, bean.getMajor_name());
                helper.setText(R.id.tv_duration, bean.getDuration());
                helper.setText(R.id.tv_address, bean.getSchool_city());
                helper.setText(R.id.tv_brand, bean.getBrand_name());
                helper.setText(R.id.tv_product, bean.getCategory());
                helper.setText(R.id.tv_price, AppUtils.getDouble2(bean.getSale_price()));
                helper.setText(R.id.tv_orgamnization, bean.getAgent_name() + "为您服务");


                helper.getView(R.id.tv_share).setOnClickListener(v->{
                    new ChooseShare(ProductCollectActivity.this).showBottomView(new ChooseShare.ChooseShareImpl() {
                        @Override
                        public void choosePlat(int index) {
                            if (index == 1) {
                                ChooseShare.shareMsg(ProductCollectActivity.this, SHARE_MEDIA.WEIXIN, index);
                            } else if (index == 2) {
                            } else {
                            }
                        }
                    });
                });

                helper.getView(R.id.tv_del).setOnClickListener(v->{
                    mProductPresenter.hasCollectProduct(bean.getId());
                    deleteId = Integer.valueOf(bean.getId());
                    LoginBean loginBean = CommSpUtils.getLoginBean();
                    List<Integer> goods_ids = loginBean.getGoods_ids();
                    for (Integer goods_id : goods_ids) {
                        if(goods_id == deleteId){
                            goods_ids.remove(goods_id);
                            break;
                        }
                    }
                    loginBean.setGoods_ids(goods_ids);
                    CommSpUtils.saveLoginInfo(new Gson().toJson(loginBean));
                });
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(1,10);
    }

    @Override
    public void collectProductView() {
//        mRefreshLayout.autoRefresh();
        loadData(1,10);
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
        mProductPresenter.collectProductList(page, mSearchInfo);
    }


}
