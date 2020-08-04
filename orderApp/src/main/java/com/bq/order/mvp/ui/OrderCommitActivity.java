package com.bq.order.mvp.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.PayView;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.presenter.OrderPresenter;
import com.bq.order.requset.bean.OrderRequsetBean;
import com.bq.order.requset.bean.ProductInfo;
import com.bq.order.requset.bean.SpecificationList;
import com.bq.utilslib.AppUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述： 提交订单
 * 作者：梁帆
 * 时间：2020/7/17
 * 版权：
 */
@Route(path = AppArouter.ORDER_ORDER_COMMIT_ACTIVITY)
public class OrderCommitActivity extends BaseActivity implements OrderIview{


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
    @BindView(R2.id.iv_item)
    ImageView mIvItem;
    @BindView(R2.id.tv_product_title)
    TextView mTvProductTitle;
    @BindView(R2.id.tv_school)
    TextView mTvSchool;
    @BindView(R2.id.tv_profession)
    TextView mTvProfession;
    @BindView(R2.id.tv_duration)
    TextView mTvDuration;
    @BindView(R2.id.tv_address)
    TextView mTvAddress;
    @BindView(R2.id.tv_brand)
    TextView mTvBrand;
    @BindView(R2.id.tv_product)
    TextView mTvProduct;
    @BindView(R2.id.tv_sale_price)
    TextView mTvSalePrice;
    @BindView(R2.id.tv_stock)
    TextView mTvStock;
    @BindView(R2.id.tv_category_value)
    TextView mTvCategoryValue;
    @BindView(R2.id.tv_logister)
    TextView mTvLogister;
    @BindView(R2.id.tv_real_price)
    TextView mTvRealPrice;
    @BindView(R2.id.tv_money)
    TextView mTvMoney;
    @BindView(R2.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R2.id.tv_buy)
    TextView mTvBuy;

    @Autowired
    ProductInfo mProductInfo;

    private OrderPresenter mOrderPresenter;

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_commit_order;
    }

    @Override
    protected BasePresenter createPersenter() {
        return mOrderPresenter = new OrderPresenter(this);
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("确认订单");
//        Glide.with(mIvItem).load(mProductInfo.getThumbnail())
//                .apply(Utils.getRequestOptionRadus(mIvItem.getContext(),0)).into(mIvItem);
        Utils.showImage(mProductInfo.getImgPath(),mIvItem);
        mTvProductTitle.setText(mProductInfo.getTitle());
        mTvSchool.setText(mProductInfo.getSchool_name());
        mTvProfession.setText(mProductInfo.getMajor_name());
        mTvDuration.setText(mProductInfo.getDuration());
        mTvAddress.setText(mProductInfo.getSchool_city());
        mTvBrand.setText(mProductInfo.getBrand_name());
        mTvBrand.setText(mProductInfo.getBrand_name());
        mTvProduct.setText(mProductInfo.getProduction_name());
        mTvSalePrice.setText(AppUtils.getDouble2(mProductInfo.getRealPrice()));

        mTvCategoryValue.setText(mProductInfo.getAttrubute());
        mTvLogister.setText(mProductInfo.getDespatch_type());
        mTvRealPrice.setText("¥"+AppUtils.getDouble2(mProductInfo.getRealPrice() * mProductInfo.getCount()));
        mTvMoney.setText("¥"+AppUtils.getDouble2(mProductInfo.getRealPrice() * mProductInfo.getCount()));
        mTvTotalPrice.setText("¥"+AppUtils.getDouble2(mProductInfo.getRealPrice() * mProductInfo.getCount()));
        mTvStock.setText("x "+ mProductInfo.getCount());
    }


    @OnClick({R2.id.tv_buy})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_buy) {
            if(!Utils.isFastDoubleClick(mTvBuy,1000)){
                List<SpecificationList> specification_list = mProductInfo.getSpecification_list();
                List<OrderRequsetBean.GoodsList> goodsList = new ArrayList<>();
                OrderRequsetBean.GoodsList goods = new OrderRequsetBean.GoodsList(mProductInfo.getCount(),specification_list.get(mProductInfo.getSelectPosition()).getId()+"");
                goodsList.add(goods);
                OrderRequsetBean bean = new OrderRequsetBean(mProductInfo.getRealPrice(),1,goodsList);
                String orderInfo = new Gson().toJson(bean);
                mOrderPresenter.addOrder(orderInfo);
            }
        }
    }

    @Override
    public void orderAddView(final String orderId) {
        new PayView().showBottomView(OrderCommitActivity.this, orderId,
                Utils.getDouble2(mProductInfo.getRealPrice()*mProductInfo.getCount()), "0.00");

//        EventBus.getDefault().post(new MessageEvent(AppArouter.WALLET_BALANCE_SERVICE, new MessageInter() {
//            @Override
//            public void callBack(MessageBody data) {
//                String content = data.getContent();
//                BanlanceBean banlance = new Gson().fromJson(content, BanlanceBean.class);
//                double balance = banlance.getBalance();
//                //弹出支付框
//                new PayView().showBottomView(OrderCommitActivity.this, orderId,
//                        Utils.getDouble2(mProductInfo.getRealPrice()*mProductInfo.getCount()), Utils.getDouble2(balance));
//            }
//        }));


    }
}
