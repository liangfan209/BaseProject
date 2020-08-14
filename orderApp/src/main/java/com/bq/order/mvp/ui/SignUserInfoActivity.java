package com.bq.order.mvp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.requset.bean.InvoiceInfo;
import com.bq.utilslib.AccountValidatorUtil;
import com.bq.utilslib.EditFormatUtils;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;

import butterknife.BindView;

/**
 * 文件名：
 * 描述：签署人信息页面
 * 作者：梁帆
 * 时间：2020/8/6
 * 版权：
 */
@Route(path = AppArouter.ORDER_SIGN_USER_ACTIVITY)
public class SignUserInfoActivity extends BaseActivity implements OrderIview{
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

    @BindView(R2.id.tv_commit)
    TextView mTvCommit;

    @Autowired
    InvoiceInfo mInvoiceInfo;

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_sign_user_activity;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("签署人信息");
        ARouter.getInstance().inject(this);
        EditFormatUtils.idCardAddSpace(mEtIdCard);
        EditFormatUtils.phoneNumAddSpace(mEtPhone);

        if(mInvoiceInfo != null){
            mEtName.setText(mInvoiceInfo.getName());
            mEtPhone.setText(mInvoiceInfo.getPhone());
            mEtIdCard.setText(mInvoiceInfo.getIdentification());
            mEtName.setClearDrawableVisible(false);
            mEtPhone.setClearDrawableVisible(false);
            mEtIdCard.setClearDrawableVisible(false);
        }

        mTvCommit.setOnClickListener(v->{
            String name = mEtName.getText().toString();
            String phone = mEtPhone.getText().toString().replaceAll("","");
            String identication = mEtIdCard.getText().toString().replaceAll(" ","");
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
            mInvoiceInfo = new InvoiceInfo(name,phone,identication);
            Bundle bundle =new Bundle();
            bundle.putSerializable("mInvoiceInfo",mInvoiceInfo);
            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(0,intent);
            finish();
        });
    }

}
