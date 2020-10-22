package com.clkj.order.mvp.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.PayViewHelper;
import com.bq.comm_config_lib.utils.Utils;
import com.clkj.order.R;
import com.clkj.order.R2;
import com.clkj.order.mvp.presenter.OrderPresenter;
import com.clkj.order.requset.bean.InvoiceInfo;
import com.clkj.order.requset.bean.OrderInfo;
import com.bq.utilslib.AppUtils;
import com.fan.baseuilibrary.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：订单详情
 * 作者：梁帆
 * 时间：2020/7/17
 * 版权：
 */
@Route(path = AppArouter.ORDER_ORDER_DETAIL_ACTIVITY)
public class OrderDetaiActivity extends BaseActivity implements OrderIview {

    @Autowired
    String mOrderId;
    OrderInfo mOrderInfoBean;
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
    @BindView(R2.id.tv_price)
    TextView mTvPrice;
    @BindView(R2.id.tv_bottom_right)
    TextView mTvBottomRight;
//    @BindView(R2.id.tv_after_sale)
//    TextView mTvAfterSale;
    @BindView(R2.id.tv_order_num)
    TextView mTvOrderNum;




    @BindView(R2.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R2.id.tv_real_price)
    TextView mTvRealPrice;
    @BindView(R2.id.tv_order_bottom_right)
    TextView mTvOrderBottomRight;
    @BindView(R2.id.tv_order_sign_contract)
    TextView mTvOrderSignContract;


    @BindView(R2.id.tv_product_type)
    TextView mTvProductType;
    @BindView(R2.id.tv_order_type)
    TextView mTvOrderType;
    @BindView(R2.id.tv_copy)
    TextView mTvCopy;

    @BindView(R2.id.llt_address)
    LinearLayout mLltAddress;
    @BindView(R2.id.tv_address_name)
    TextView mTvAddressName;
    @BindView(R2.id.tv_address_phone)
    TextView mTvAddressPhone;
    @BindView(R2.id.tv_address_txt)
    TextView mTvAddressTxt;

    @BindView(R2.id.rlt_education)
    RelativeLayout mRltEducation;
    @BindView(R2.id.tv_education_name)
    TextView mTvEducationName;
    @BindView(R2.id.tv_education_phone)
    TextView mTvEducationPhone;
    @BindView(R2.id.tv_education_id)
    TextView mTvEducationId;
    @BindView(R2.id.tv_agent)
    TextView mTvAgent;
    @BindView(R2.id.tv_refund)
    TextView mTvRefund;


    @BindView(R2.id.tv_order_time)
    TextView mTvOrderTime; //下单时间

    @BindView(R2.id.tv_pay_type)
    TextView mTvPayType; //付款方式

    @BindView(R2.id.tv_pay_num)
    TextView mTvPayNum;//付款单号

    @BindView(R2.id.tv_pay_time)
    TextView mTvPayTime;//付款时间

    //下单时间 tv_order_time  #
    //付款方式 tv_pay_type #
    //付款单号 tv_pay_num #
    //付款时间 tv_pay_time #
    //助学原价 tv_sale_price
    //优惠金额 tv_discount
    //成交价 tv_strike_price
    //欠缴 tv_qianjiao
    //实付款 tv_actual_amount
    //支付方式分期 tv_fenqi
    //付款金额 tv_shifu_jine

    @BindView(R2.id.tv_sale_price)
    TextView mTvSalePrice;
    @BindView(R2.id.tv_discount)
    TextView mTvDiscount;
    @BindView(R2.id.tv_strike_price)
    TextView mTvStrikePrice;
    @BindView(R2.id.tv_qianjiao)
    TextView mTvQianjiao;
    @BindView(R2.id.tv_actual_amount)
    TextView mTvActualAmount;
    @BindView(R2.id.tv_fenqi)
    TextView mTvFenqi;
    @BindView(R2.id.tv_shifu_jine)
    TextView mTvShiFuJe;

    @BindView(R2.id.tv_pay_detail)
    TextView mTvPayDetail;


    @BindView(R2.id.llt_pay_detail)
    LinearLayout mLltPayhDetail;








//    tv_address_name tv_address_phone  tv_address_txt

    private OrderPresenter mOrderPresenter;
    private InvoiceInfo mInvoiceInfo;

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_order_detail;
    }

    @Override
    protected BasePresenter createPersenter() {
        return mOrderPresenter = new OrderPresenter(this);
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("订单详情");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mOrderPresenter.getOrderDetail(mOrderId);
    }


    @Override
    public void getOrderDetail(OrderInfo info) {
        mOrderInfoBean = info;
        mInvoiceInfo = info.getInvoice_info();
        initView();
    }


    private void initView() {
        final OrderInfo.OrderItemListBean mProductInfo = mOrderInfoBean.getOrder_item_list().get(0);
        mTvProductTitle.setText(mProductInfo.getTitle());
        mTvSchool.setText(mProductInfo.getSchool_name());
        mTvProfession.setText(mProductInfo.getMajor_name());
        mTvDuration.setText(mProductInfo.getDuration());
        mTvAddress.setText(mProductInfo.getSchool_city());
        mTvBrand.setText(mProductInfo.getBrand_name());
        mTvBrand.setText(mProductInfo.getBrand_name());
        mTvProduct.setText(mProductInfo.getCategory());
        mTvPrice.setText(AppUtils.getDouble2(mProductInfo.getSale_price()));
        mTvBottomRight.setText("x"+mProductInfo.getQuantity());
        mTvAgent.setText(mProductInfo.getAgent_name()+"为您服务");

//        mTvAfterSale.setVisibility(View.GONE);
        mTvOrderSignContract.setVisibility(View.GONE);
        mTvOrderBottomRight.setVisibility(View.GONE);

        mTvOrderNum.setText(mOrderInfoBean.getNumber()+"");




        mTvTotalPrice.setText("¥"+Utils.getDouble2(mProductInfo.getTotal_price()));
        mTvRealPrice.setText("¥"+Utils.getDouble2(mProductInfo.getTotal_price()));

        Utils.showImage(mProductInfo.getShow_image(),mIvItem);
        mTvProductType.setText("属性："+mProductInfo.getRemark() + "     发货："+mProductInfo.getDespatch_type());



        //下单时间 tv_order_time  #
        mTvOrderTime.setText(mOrderInfoBean.getCreate_time());
        //付款方式 tv_pay_type #
        mTvPayType.setText(mOrderInfoBean.getLast_payment_type());
        //付款单号 tv_pay_num #
        mTvPayNum.setText(mOrderInfoBean.getLast_payment_number());
        //付款时间 tv_pay_time #
        mTvPayTime.setText(mOrderInfoBean.getLast_payment_time());

        //助学原价 tv_sale_price
        mTvSalePrice.setText("¥"+Utils.getDouble2(mOrderInfoBean.getSale_price()));
        //优惠金额 tv_discount
        mTvDiscount.setText("-¥"+Utils.getDouble2(mOrderInfoBean.getDiscount()));
        //成交价 tv_strike_price
        mTvStrikePrice.setText("¥"+Utils.getDouble2(mOrderInfoBean.getStrike_price()));
        //欠缴 tv_qianjiao  如果需付款金额 小于
        mTvQianjiao.setText("欠缴：¥"+Utils.getDouble2(mOrderInfoBean.getArrears()));
        mTvQianjiao.setVisibility(mOrderInfoBean.getArrears()>0?View.VISIBLE:View.GONE);
        //实付款 tv_actual_amount  //如果是待支付就是需付款
        if("order_launched".equals(mOrderInfoBean.getStatus())){
            mTvActualAmount.setText("需付款：¥"+Utils.getDouble2(mOrderInfoBean.getDeposit()));
        }else{
            mTvActualAmount.setText("实付款：¥"+Utils.getDouble2(mOrderInfoBean.getActual_amount()));
        }

        if("order_launched".equals(mOrderInfoBean.getStatus()) || "order_closed".equals(mOrderInfoBean.getStatus())){
            mLltPayhDetail.setVisibility(View.GONE);
        }else{
            mLltPayhDetail.setVisibility(View.VISIBLE);
        }

        //支付方式分期 tv_fenqi
        mTvFenqi.setText(mOrderInfoBean.getPay_services());
        //付款金额 tv_shifu_jine
        mTvShiFuJe.setText("¥"+Utils.getDouble2(mOrderInfoBean.getDeposit()));
//        if("order_launched".equals(mOrderInfoBean.getStatus())){
//            mTvShiFuJe.setText("¥"+Utils.getDouble2(mOrderInfoBean.getDeposit()));
//        }else{
//            mTvShiFuJe.setText("¥"+Utils.getDouble2(mOrderInfoBean.getActual_amount()));
//        }

        mTvPayDetail.setOnClickListener(v->{
            ARouter.getInstance().build(AppArouter.ORDER_PAY_ORDER_DETAIL_ACTIVITY)
                    .withSerializable("mOrderInfo",mOrderInfoBean).navigation();
        });

        //屏蔽申请退款
//        mTvRefund.setVisibility(View.VISIBLE);

        List<OrderInfo.OrderItemListBean> order_item_list = mOrderInfoBean.getOrder_item_list();
        if(order_item_list.size() <=0) return;

        if(order_item_list.get(0).getDespatch_type().contains("教育")){
            mRltEducation.setVisibility(View.VISIBLE);
            mLltAddress.setVisibility(View.GONE);
            updateInVoiceInfo();

            if(mOrderInfoBean.getStatus().equals("order_launched")){
                mTvOrderType.setText("等待支付");
                mTvOrderBottomRight.setVisibility(View.VISIBLE);
                mTvOrderBottomRight.setText("立即支付");
                mTvRefund.setVisibility(View.GONE);
            }else if(mOrderInfoBean.getStatus().equals("payment_finished")){
                mTvOrderType.setText("已付款");
//                mTvOrderSignContract.setVisibility(View.VISIBLE);
            }else if(mOrderInfoBean.getStatus().equals("delivery_finished")){
                mTvOrderType.setText("已付款");
                mTvOrderSignContract.setVisibility(View.VISIBLE);
            }else if(mOrderInfoBean.getStatus().equals("order_closed")){
                mTvOrderType.setText("订单已取消");
                mTvOrderBottomRight.setVisibility(View.GONE);
            }else if(mOrderInfoBean.getStatus().equals("order_finished")){
                mTvOrderType.setText("订单已完成");
                mTvOrderSignContract.setText("查看合同");
                mTvOrderSignContract.setVisibility(View.VISIBLE);
            }else{
//                mTvAfterSale.setVisibility(View.VISIBLE);
            }

        }else{
            if(order_item_list.get(0).getDespatch_type().contains("物流")){
                mLltAddress.setVisibility(View.VISIBLE);
                mRltEducation.setVisibility(View.GONE);
                InvoiceInfo invoice_info = mOrderInfoBean.getInvoice_info();
                if(invoice_info == null) return;
                mTvAddressName.setText(invoice_info.getName());
                mTvAddressPhone.setText(invoice_info.getPhone());
                mTvAddressTxt.setText(invoice_info.getAddress());
            }

            if(mOrderInfoBean.getStatus().equals("order_launched")){
                mTvOrderType.setText("等待支付");
                mTvOrderBottomRight.setVisibility(View.VISIBLE);
                mTvOrderBottomRight.setText("立即支付");
                mTvRefund.setVisibility(View.GONE);
            }else if(mOrderInfoBean.getStatus().equals("payment_finished")){
                mTvOrderType.setText("等待卖家发货");
            }else if(mOrderInfoBean.getStatus().equals("delivery_finished")){
                mTvOrderType.setText("卖家已发货");
                mTvOrderBottomRight.setVisibility(View.VISIBLE);
                mTvOrderBottomRight.setText("确认收货");
            }else if(mOrderInfoBean.getStatus().equals("order_closed")){
                mTvOrderType.setText("订单已取消");
            }else if(mOrderInfoBean.getStatus().equals("order_finish")){
                mTvOrderType.setText("订单已完成");
            }else{
//                mTvAfterSale.setVisibility(View.VISIBLE);
                mTvOrderType.setText("订单已完成");
            }
        }

        mTvOrderType.setText(mOrderInfoBean.getStatus_name());

        //签约合同
        mTvOrderSignContract.setOnClickListener(v->{
            //后期还要判断是否已经签约了合同
            if(mOrderInfoBean.getStatus().equals("delivery_finished")){
                ARouter.getInstance().build(AppArouter.ORDER_SIGN_CONTRACT_ACTIVITY)
                        .withInt("sign",1)
                        .withString("mOrderDetailId",mProductInfo.getContract_id()+"").navigation();
//                        .withSerializable("imgPathList",mOrderInfoBean.getContract_background()).navigation();

            }else if(mOrderInfoBean.getStatus().equals("order_finished")){
                ARouter.getInstance().build(AppArouter.ORDER_SIGN_CONTRACT_ACTIVITY)
                        .withString("mOrderDetailId",mProductInfo.getContract_id()+"").navigation();
            }
        });
        mTvOrderBottomRight.setOnClickListener(v->{
            if(mOrderInfoBean.getStatus().equals("order_launched")){
                PayViewHelper.getBanenceAndShow(OrderDetaiActivity.this, mOrderId,
                        Utils.getDouble2(mOrderInfoBean.getDeposit()),2);
            }else if(mOrderInfoBean.getStatus().equals("payment_finished") || mOrderInfoBean.getStatus().equals("delivery_finished")){
//                ARouter.getInstance().build(AppArouter.WALLET_REFUND_ACTIVITY).navigation();
            }
        });

        mTvRefund.setOnClickListener(v->{
            ARouter.getInstance().build(AppArouter.WALLET_REFUND_ACTIVITY).navigation();
        });

//        mTvOrderTime.setText(mOrderInfoBean.getCreate_time());
//        mTvPayType.setText(mOrderInfoBean.getLast_payment_type());
    }


    @OnClick({R2.id.tv_copy,R2.id.tv_order_bottom_right,R2.id.tv_server})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.tv_copy){
            String s = mTvOrderNum.getText().toString();
            if(!StringUtils.isEmpty(s)){
                Utils.clipBoard(this,s);
            }
        }else if(view.getId() == R.id.tv_server){
            ToastUtils.showToast(this,"正在开发中");
        }
    }

    private void updateInVoiceInfo(){
        mTvEducationName.setVisibility(View.VISIBLE);
        mTvEducationPhone.setVisibility(View.VISIBLE);
        mTvEducationId.setVisibility(View.VISIBLE);
        if(mInvoiceInfo != null){
            mTvEducationName.setText(mInvoiceInfo.getName());
            mTvEducationPhone.setText(mInvoiceInfo.getPhone());
            mTvEducationId.setText(mInvoiceInfo.getIdentification());
        }
    }

}
