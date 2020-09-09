package com.bq.order.mvp.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.msgService.MessageBody;
import com.bq.comm_config_lib.msgService.MessageEvent;
import com.bq.comm_config_lib.msgService.MessageInter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.PayViewHelper;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.api.bean.AddressInfo;
import com.bq.order.mvp.presenter.OrderPresenter;
import com.bq.order.requset.bean.InvoiceInfo;
import com.bq.order.requset.bean.OrderRequsetBean;
import com.bq.order.requset.bean.ProductInfo;
import com.bq.order.requset.bean.SpecificationList;
import com.bq.utilslib.AppUtils;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
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

    @BindView(R2.id.rlt_address)
    RelativeLayout mRltAddress;
    @BindView(R2.id.tv_address_contact)
    TextView mTvAddressContact;
    @BindView(R2.id.tv_address_detail)
    TextView mTvAddressDetail;
    @BindView(R2.id.tv_address_phone)
    TextView mTvAddressPhone;

    @BindView(R2.id.tv_min_price)
    TextView mtvMinPrice;

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

    @BindView(R2.id.tv_payType)
    TextView mTvPayType;
    @BindView(R2.id.tv_pay_money)
    TextView mTvPayMoney;
    @BindView(R2.id.tv_qian_money)
    TextView mTvQian;
    @BindView(R2.id.rlt_qian)
    RelativeLayout mRltQian;
    @BindView(R2.id.tv_arrears)
    TextView mTvArrears;


    @Autowired
    String mPosterId;

    private int realPrice;



    @Autowired
    ProductInfo mProductInfo;
    private String addressId = "-1";
    private OrderPresenter mOrderPresenter;
    private InvoiceInfo mInvoiceInfo;

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
        mTvProduct.setText(mProductInfo.getCategory());
        if(mProductInfo.getSpecification_list().size() >0){
            mTvSalePrice.setText(AppUtils.getDouble2(mProductInfo.getSpecification_list().get(0).getOriginal_price()));
        }
        mTvAgent.setText(mProductInfo.getAgent_name()+"为您服务");

        mTvCategoryValue.setText(mProductInfo.getAttrubute());
        mTvLogister.setText(mProductInfo.getDespatch_type());



//        mTvTotalPrice.setText("¥"+AppUtils.getDouble2(mProductInfo.getRealPrice() * mProductInfo.getCount()));
        mTvStock.setText("x "+ mProductInfo.getCount());

        if(mProductInfo.getSpecification_list().size()>0){
            int originalPrice = mProductInfo.getSpecification_list().get(0).getOriginal_price();
            realPrice = mProductInfo.getSpecification_list().get(0).getSale_price();

            //原价
            mTvRealPrice.setText("¥"+AppUtils.getDouble2(originalPrice*mProductInfo.getCount()));
            //优惠
            mtvMinPrice.setText("- ¥"+AppUtils.getDouble2(originalPrice*mProductInfo.getCount() - realPrice * mProductInfo.getCount()));
            //成交价
            mTvMoney.setText("¥"+AppUtils.getDouble2( realPrice * mProductInfo.getCount()));

            if(mProductInfo.getDeposit() >0 && mProductInfo.getPay_services().equals("installment")){
                mTvArrears.setText("欠缴：¥"+AppUtils.getDouble2(realPrice)+"  ");
                mTvTotalPrice.setText(AppUtils.getDouble2(mProductInfo.getDeposit()));
            }else{
                mTvTotalPrice.setText(AppUtils.getDouble2(realPrice * mProductInfo.getCount()));
            }




            if("full_payment".equals(mProductInfo.getPay_services())){
                mTvPayType.setText("全款");
            }else if("installment".equals(mProductInfo.getPay_services())){
                mRltQian.setVisibility(View.VISIBLE);
                mTvQian.setText("¥"+AppUtils.getDouble2(realPrice));
                mTvPayType.setText("分期");
            }else{
                mRltQian.setVisibility(View.GONE);
            }
            //支付金额
            if(mProductInfo.getDeposit() > 0){
                mTvPayMoney.setText("¥"+AppUtils.getDouble2(mProductInfo.getDeposit()));
            }else{
                mTvPayMoney.setText("¥"+AppUtils.getDouble2(realPrice));
            }

        }

        if(mProductInfo.getDespatch_type().contains("教育")){
            mRltAddress.setVisibility(View.GONE);
            mRltEducation.setVisibility(View.VISIBLE);
            if(mInvoiceInfo == null){
                mTvEducationName.setText("请填写合同人信息");
                mTvEducationPhone.setVisibility(View.GONE);
                mTvEducationId.setVisibility(View.GONE);
            }else{
                updateInVoiceInfo();
            }
        }else{
            mRltAddress.setVisibility(View.VISIBLE);
            mRltEducation.setVisibility(View.GONE);
            EventBus.getDefault().post(new MessageEvent(AppArouter.USER_CENTER_ADDRESS_DEFAULT_SERVICE, new MessageInter() {
                @Override
                public void callBack(MessageBody data) {
                    if(data.getCode() == MessageBody.SUCCESS_CODE){
                        parseAddressInfoStr(data.getContent());
                    }
                }
            }));
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


    @OnClick({R2.id.tv_buy,R2.id.rlt_address,R2.id.rlt_education})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_buy) {
            if(!Utils.isFastDoubleClick(mTvBuy,1000)){
                List<SpecificationList> specification_list = mProductInfo.getSpecification_list();
                List<OrderRequsetBean.GoodsList> goodsList = new ArrayList<>();
                OrderRequsetBean.GoodsList goods = new OrderRequsetBean.GoodsList(mProductInfo.getCount(),specification_list.get(mProductInfo.getSelectPosition()).getId()+"");
                goodsList.add(goods);
                OrderRequsetBean bean = new OrderRequsetBean(realPrice,goodsList);
                if(mProductInfo.getDespatch_type().contains("物流")){
                    if(addressId.equals("-1")){
                        ToastUtils.showToast(this,"请选择地址");
                        return;
                    }
                    bean.setAddress_id(Integer.valueOf(addressId));
                }else if(mProductInfo.getDespatch_type().contains("教育")){
                    String name = mTvEducationName.getText().toString();
                    String phone = mTvEducationPhone.getText().toString();
                    String identification = mTvEducationId.getText().toString();
                    if(StringUtils.isEmpty(phone)){
                        ToastUtils.showToast(this,"请填写合同人信息");
                        return;
                    }
                    InvoiceInfo info = new InvoiceInfo(name,phone,identification);
                    bean.setInvoice_info(info);
                }
                if(!StringUtils.isEmpty(mPosterId)){
                    bean.setDeposit(mProductInfo.getDeposit());
                    String orderInfo = new Gson().toJson(bean);
                    mOrderPresenter.addOrderByPoster(orderInfo,mPosterId);
                }else{
                    String info = new Gson().toJson(bean);
                    mOrderPresenter.addOrder(info);
                    mTvBuy.setEnabled(false);
                }
            }
        }else if(view.getId() == R.id.rlt_address){
            ARouter.getInstance().build(AppArouter.USER_CENTER_ADDRESS_SELECT)
                    .withInt("addressId",Integer.valueOf(addressId))
                    .navigation(this,11);
        }else if(view.getId() == R.id.rlt_education){
            ARouter.getInstance().build(AppArouter.ORDER_SIGN_USER_ACTIVITY)
                    .withSerializable("mInvoiceInfo",mInvoiceInfo)
                    .navigation(this,12);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null) return;
        if(requestCode == 11 && resultCode == 0){
            try{
                String addressInfo = data.getStringExtra("address_info");
                parseAddressInfoStr(addressInfo);
            }catch(Exception e){

            }
        }else if(requestCode == 12 && resultCode == 0){
            mInvoiceInfo = (InvoiceInfo) data.getExtras().getSerializable("mInvoiceInfo");
            updateInVoiceInfo();
        }
    }

    private void parseAddressInfoStr(String addressInfo) {
        if(StringUtils.isEmpty(addressInfo)){
            mTvAddressDetail.setVisibility(View.GONE);
            mTvAddressContact.setText("请选择收货地址");
            return;
        }
        mTvAddressDetail.setVisibility(View.VISIBLE);
        AddressInfo info = new Gson().fromJson(addressInfo, AddressInfo.class);
        mTvAddressDetail.setText(info.getCity()+info.getAddress());
        mTvAddressContact.setText(info.getContacts());
        mTvAddressPhone.setText(info.getPhone());
        addressId = info.getId();
    }

    @Override
    public void orderAddView(final String orderId) {
        int price = realPrice;
        if(mProductInfo.getDeposit() >0){
            price = mProductInfo.getDeposit();
        }
        PayViewHelper.getBanenceAndShow(OrderCommitActivity.this, orderId,
                Utils.getDouble2(price),1);
    }
}
