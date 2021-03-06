package com.clkj.order.mvp.ui;

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
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.utilslib.AccountValidatorUtil;
import com.bq.utilslib.EditFormatUtils;
import com.clkj.order.R;
import com.clkj.order.R2;
import com.clkj.order.api.OrderEventKey;
import com.clkj.order.mvp.presenter.OrderPresenter;
import com.clkj.order.requset.bean.ContractInfo;
import com.clkj.order.requset.bean.ContractRequsetBean;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.fan.baseuilibrary.view.dialog.CustomDialog;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

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
//    @BindView(R2.id.iv_contract)
//    ImageView mIvContract;
    @BindView(R2.id.llt_imgs)
    LinearLayout mLltImgs;

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
    private String contactId="";


    @Autowired
    String mOrderDetailId;

    @Autowired
    int sign;

    @Autowired
    ContractInfo mContractInfo;

    private int flag = -1;


    String imgBaseStr = "";

    private String emailStr = "";

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

        if(mContractInfo != null){
//            mEtName.setText(mContractInfo.getName());
//            mEtPhone.setText(mContractInfo.getPhone());
//            mEtIdCard.setText(mContractInfo.getIdentification());
//            mEtName.setEnabled(false);
//            mEtPhone.setEnabled(false);
//            mEtIdCard.setEnabled(false);
//            mEtName.setClearDrawableVisible(false);
//            mEtPhone.setClearDrawableVisible(false);
//            mEtIdCard.setClearDrawableVisible(false);
            ArrayList<String> list = mContractInfo.getImg_url();
            if(list == null || list.size() == 0)return;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < list.size(); i++) {
                ImageView iv = new ImageView(this);
                Utils.showImage(list.get(i),iv);
                mLltImgs.addView(iv,params);
            }
            contactId = mContractInfo.getId();
        }

        if(sign == 1){
            mTvTitle.setText("签合同");

        }else{
            mTvTitle.setText("查看合同");
            mRltBottom.setVisibility(View.GONE);
            mLltTopContent.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mScrollview.getLayoutParams();
            layoutParams.bottomMargin = 0;
            mScrollview.setLayoutParams(layoutParams);
        }



        if(!StringUtils.isEmpty(mOrderDetailId)){
            mOrderPresenter.getContactImg(mOrderDetailId);
        }
    }


    @Override
    public void getContactInfo(ContractInfo info) {

        if(info != null){
            mEtName.setText(info.getName());
            mEtPhone.setText(info.getPhone());
            mEtIdCard.setText(info.getIdentification());
            mEtName.setEnabled(false);
            mEtPhone.setEnabled(false);
            mEtIdCard.setEnabled(false);
            mEtName.setClearDrawableVisible(false);
            mEtPhone.setClearDrawableVisible(false);
            mEtIdCard.setClearDrawableVisible(false);
        }else{
            return;
        }

        if(info.getStatus_name().equals("待签署")){
            mTvTitle.setText("签合同");
        }else{
            mTvTitle.setText("查看合同");
            mRltBottom.setVisibility(View.GONE);
            mLltTopContent.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mScrollview.getLayoutParams();
            layoutParams.bottomMargin = 0;
            mScrollview.setLayoutParams(layoutParams);
        }


        contactId = info.getId();
        ArrayList<String> list = info.getImg_url();
        if(list == null || list.size() == 0)return;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < list.size(); i++) {
            ImageView iv = new ImageView(this);
            Utils.showImage(list.get(i),iv);
            mLltImgs.addView(iv,params);
        }
    }

    @OnClick({R2.id.tv_sign, R2.id.tv_commit,R2.id.tv_emails})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.tv_sign){
            ARouter.getInstance().build(AppArouter.ORDER_AUTOGRAPH_ACTIVITY).navigation(this,11);
        }else if(view.getId() == R.id.tv_emails){
            View view1 = LinearLayout.inflate(this, R.layout.order_input_view, null);
            final DeletableEditText etValue = view1.findViewById(R.id.et_value);
            etValue.setHint("请输入邮箱（用户接收电子合同）");
            if(!StringUtils.isEmpty(emailStr)){
                etValue.setText(emailStr);
            }
            CustomDialog mCustomDialog = new CustomDialog();
            mCustomDialog.showCustonViewDialog(this, view1, "填写邮箱",false, new CustomDialog.ClickCallBack() {
                @Override
                public void ok() {
                    emailStr = etValue.getText().toString().trim();
                    if(StringUtils.isEmpty(emailStr)){
                        ToastUtils.showToast(SignContractActivity.this,"请输入邮箱");
                        return;
                    }
                    if(!AccountValidatorUtil.isEmail(emailStr)){
                        ToastUtils.showToast(SignContractActivity.this,"请输入正确的邮箱");
                        return;
                    }
                    mCustomDialog.dissMissDialog();
                }

                @Override
                public void cacel() {
                    mCustomDialog.dissMissDialog();
                }
            });
        }
        else if(view.getId() == R.id.tv_commit){

            if(Utils.isFastDoubleClick(mTvCommit,4000)) {
                return;
            }
//            String name = mEtName.getText().toString();
//            String phone = mEtPhone.getText().toString().replaceAll(" ","");
//            String identication = mEtIdCard.getText().toString().replaceAll(" ","");
//            String email = mEtEmail.getText().toString();
//            if(StringUtils.isEmpty(name)){
//                ToastUtils.showToast(this,"请输入姓名");
//                return;
//            }
//            if(StringUtils.isEmpty(phone)){
//                ToastUtils.showToast(this,"请输入手机号");
//                return;
//            }
//            if(!AccountValidatorUtil.isIdCard(identication)){
//                ToastUtils.showToast(this,"请输入正确的身份证号");
//                return;
//            }

            if(StringUtils.isEmpty(emailStr)){
                ToastUtils.showToast(this,"请输入邮箱");
                return;
            }
            if(!AccountValidatorUtil.isEmail(emailStr)){
                ToastUtils.showToast(this,"请输入正确的邮箱");
                return;
            }
            if(flag == -1){
                ToastUtils.showToast(this,"请先签名");
                return;
            }

            if(StringUtils.isEmpty(imgBaseStr)){
                ToastUtils.showToast(this,"请重新签名");
                return;
            }

//            imgBaseStr = "iVBORw0KGgoAAAANSUhEUgAAB38AAAOnCAYAAAAgA4xPAAAABHNCSVQICAgIfAhkiAAAIABJREFUeJzs3e1x3Mp2BdD9XA5gQoAzmAweMhAz4GQgOQJNBmIGpCMgHYHGEVCOYJgBmQH9A28sXurjipwGGmisVXX+TjVQJRHdGzgnAQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA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AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKqjbG0AAAJsSURBVAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPD/24NDAgAAAABB/197wwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANwGo9yNINNX8FAAAAABJRU5ErkJggg==";

//            imgBaseStr = imgBaseStr.replaceAll("/","_");
//            imgBaseStr = imgBaseStr.replaceAll("\\+","-");
//            imgBaseStr = imgBaseStr.replaceAll("-","+");


//            imgBaseStr = "+123+abc";
            imgBaseStr = imgBaseStr.replaceAll("\\+","%2B");
            //将图片转换为base64
            imgBaseStr = "data:image/png;base64,"+imgBaseStr;
            ContractRequsetBean bean = new ContractRequsetBean(emailStr,imgBaseStr);
            //上传信息
            if(!StringUtils.isEmpty(contactId)){
                String info = new Gson().toJson(bean);
                mOrderPresenter.addContract(info,contactId);
            }else{
                ToastUtils.showToast(this,"请退出该页面，再来一次");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 11 && resultCode == 20){
            //将图片展示出来
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            Bitmap bm = BitmapFactory.decodeFile("/sdcard/sign.png", options);
            mIvSign.setImageBitmap(bm);
            mScrollview.fullScroll(ScrollView.FOCUS_DOWN);
            flag = 1;
            imgBaseStr = bitmapToString("/sdcard/sign.png");
        }
    }

    private String bitmapToString(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(filePath, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 0, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.NO_WRAP);
    }

    @Override
    public void addContactView() {
        EventBus.getDefault().post(OrderEventKey.UPDATE_ORDER_STATUS);
        ToastUtils.showToast(this,"签名成功");
        finish();
    }

}
