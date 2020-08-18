package com.bq.order.mvp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.msgService.bean.UserInfo;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.api.OrderEventKey;
import com.bq.order.mvp.presenter.OrderPresenter;
import com.bq.order.requset.bean.ContractRequsetBean;
import com.bq.order.requset.bean.InvoiceInfo;
import com.bq.utilslib.AccountValidatorUtil;
import com.bq.utilslib.EditFormatUtils;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/8/6
 * 版权：
 */
@Route(path = AppArouter.ORDER_SIGN_CONTRACT_ACTIVITY)
public class SignContractActivity extends BaseActivity implements OrderIview{
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
    @BindView(R2.id.et_name)
    DeletableEditText mEtName;
    @BindView(R2.id.et_phone)
    DeletableEditText mEtPhone;
    @BindView(R2.id.et_id_card)
    DeletableEditText mEtIdCard;
    @BindView(R2.id.iv_scan1)
    ImageView mIvScan1;
    @BindView(R2.id.et_email)
    DeletableEditText mEtEmail;
    @BindView(R2.id.iv_contract)
    ImageView mIvContract;

    @BindView(R2.id.iv_sign)
    ImageView mIvSign;

    @BindView(R2.id.scrollview)
    ScrollView mScrollview;

    @BindView(R2.id.tv_sign)
    TextView mTvSign;
    @BindView(R2.id.tv_commit)
    TextView mTvCommit;



    @BindView(R2.id.rlt_bottom)
    RelativeLayout mRltBottom;
    @BindView(R2.id.llt_top_content)
    LinearLayout mLltTopContent;

    private OrderPresenter mOrderPresenter;
    private UserInfo userInfo;

    @Autowired
    InvoiceInfo mInvoiceInfo;
    @Autowired
    String productId;
    @Autowired
    String imgPath;
    @Autowired
    int sign;

    private int flag = -1;

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_sign_contract_activity;
    }

    @Override
    protected BasePresenter createPersenter() {
        return mOrderPresenter = new OrderPresenter(this);
    }

    @Override
    protected void attach() {

        ARouter.getInstance().inject(this);

        EditFormatUtils.idCardAddSpace(mEtIdCard);
        EditFormatUtils.phoneNumAddSpace(mEtPhone);

//        mEtIdCard.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mEtIdCard.setTypeface(s.length()>0? Typeface.DEFAULT_BOLD:Typeface.DEFAULT);
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });


        //签合同
        if(sign == 1){
            Utils.showImage(imgPath,mIvContract);
            mTvTitle.setText("签合同");
            if(mInvoiceInfo != null){
                mEtName.setText(mInvoiceInfo.getName());
                mEtPhone.setText(mInvoiceInfo.getPhone());
                mEtIdCard.setText(mInvoiceInfo.getIdentification());
                mEtName.setEnabled(false);
                mEtPhone.setEnabled(false);
                mEtIdCard.setEnabled(false);
                mEtName.setClearDrawableVisible(false);
                mEtPhone.setClearDrawableVisible(false);
                mEtIdCard.setClearDrawableVisible(false);
            }

        }else{
            mTvTitle.setText("查看合同");
            if(!StringUtils.isEmpty(imgPath)){
                Utils.showImage(imgPath,mIvContract);
            }else{
                mOrderPresenter.getContactImg(productId);
            }
            mRltBottom.setVisibility(View.GONE);
            mLltTopContent.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mScrollview.getLayoutParams();
            layoutParams.bottomMargin = 0;
            mScrollview.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void getContactImgs(List<String> list) {
        if(list == null || list.size() == 0)return;
        Utils.showImage(list.get(0),mIvContract);

    }

    @OnClick({R2.id.tv_sign, R2.id.tv_commit})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.tv_sign){
            ARouter.getInstance().build(AppArouter.ORDER_AUTOGRAPH_ACTIVITY).navigation(this,11);
        }else if(view.getId() == R.id.tv_commit){
            if(flag == -1){
                ToastUtils.showToast(this,"请先签名");
                return;
            }

            if(Utils.isFastDoubleClick(mTvCommit,4000)) {
                return;
            }
            String name = mEtName.getText().toString();
            String phone = mEtPhone.getText().toString().replaceAll("","");
            String identication = mEtIdCard.getText().toString().replaceAll(" ","");
            String email = mEtEmail.getText().toString();
            String imgBaseStr = bitmapToString("/sdcard/sign.png");

            if(StringUtils.isEmpty(name)){
                ToastUtils.showToast(this,"请输入姓名");
                return;
            }
            if(StringUtils.isEmpty(phone)){
                ToastUtils.showToast(this,"请输入手机号");
                return;
            }
            if(!AccountValidatorUtil.isIdCard(identication)){
                ToastUtils.showToast(this,"请输入正确的身份证号");
                return;
            }
            if(!AccountValidatorUtil.isEmail(email)){
                ToastUtils.showToast(this,"请输入正确的邮箱");
                return;
            }
            //将图片转换为base64
            ContractRequsetBean bean = new ContractRequsetBean(name,phone,identication,email,imgBaseStr);
            //上传信息
            mOrderPresenter.addContract(new Gson().toJson(bean),productId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 11 && resultCode == 0){
            //将图片展示出来
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            Bitmap bm = BitmapFactory.decodeFile("/sdcard/sign.png", options);
            mIvSign.setImageBitmap(bm);
            mScrollview.fullScroll(ScrollView.FOCUS_DOWN);
            flag = 1;
        }
    }

    private String bitmapToString(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(filePath, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 0, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    @Override
    public void addContactView() {
        EventBus.getDefault().post(OrderEventKey.UPDATE_ORDER_STATUS);
        ToastUtils.showToast(this,"签名成功");
        finish();
    }
}
