package com.bq.comm_config_lib.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.R;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.msgService.MessageBody;
import com.bq.comm_config_lib.msgService.MessageEvent;
import com.bq.comm_config_lib.msgService.MessageInter;
import com.bq.comm_config_lib.msgService.bean.BanlanceBean;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.utilslib.AppUtils;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.dialog.CustomDialog;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/4/26
 * 版权：
 */
public class PayViewHelper {

//    private CustomPopWindow mCustomPopWindow;
    private Dialog mDialog;
    private Dialog mDialog1;
    private Dialog mDialog2;

    private String phoneNumber = "";

    /**
     *
     * @param activity 上下文
     * @param orderId  订单ID
     * @param price    支付总价格
     * @param type  哪种页面过来的支付。  1：订单创建页 2：订单详情页 3：订单列表页
     */
    public static void getBanenceAndShow(final BaseActivity activity,String orderId,String price,int type){
        EventBus.getDefault().post(new MessageEvent(AppArouter.WALLET_BALANCE_SERVICE, new MessageInter() {
            @Override
            public void callBack(MessageBody data) {
                String content = data.getContent();
                BanlanceBean banlance = new Gson().fromJson(content, BanlanceBean.class);
                double balance = banlance.getBalance();
                //弹出支付框
                new PayViewHelper().showBottomView(activity, orderId,
                        price, Utils.getDouble2(balance),type);
            }
        }));
    }

    /**
     * @param activity 上下文
     * @param orderId  订单ID
     * @param price    支付总价格
     * @param balence  付款个人总余额
     * @param type  哪种页面过来的支付。  1：订单创建页 2：订单详情页 3：订单列表页
     */
    public void showBottomView(BaseActivity activity, String orderId, String price, String balence, int type) {
        View view = LinearLayout.inflate(activity, R.layout.layout_view_pay, null);
        final RadioButton rbBlance = view.findViewById(R.id.rbt_balence);
        final RadioButton rbWeixin = view.findViewById(R.id.rbt_wexin);
        final RadioButton rbZhifubao = view.findViewById(R.id.rbt_zhifubao);
        ImageView iv_close = view.findViewById(R.id.iv_close);


        mDialog = new Dialog(activity, R.style.custom_dialog2);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        Window dialogWindow = mDialog.getWindow();
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.BOTTOM);//可以改成Bottom
            WindowManager.LayoutParams attributes = dialogWindow.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = AppUtils.dp2px(activity,400);
            dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
        }

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == 1){
                    mDialog1 = new CustomDialog().showDialog(activity,
                            "取消支付", "确定取消支付", "取消", "确定", new CustomDialog.ClickCallBack() {

                                @Override
                                public void ok() {
                                    if (mDialog1 != null) {
                                        mDialog1.dismiss();
                                    }
                                    mDialog.dismiss();
                                    payCancel(activity,orderId);
                                }
                                @Override
                                public void cacel() {
                                }
                            });
                }else{
                    mDialog.dismiss();
                }

            }
        });
        TextView tvPrice = view.findViewById(R.id.tv_price);
        TextView tvBuy = view.findViewById(R.id.tv_buy);
        rbBlance.setEnabled(true);
        if (Double.valueOf(balence) < Double.valueOf(price)) {
            rbBlance.setEnabled(false);
            rbZhifubao.setChecked(true);
        }

        tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                boolean blanceCheck = rbBlance.isChecked();
                boolean weiXinCheck = rbWeixin.isChecked();
                boolean zhifubaoCheck = rbZhifubao.isChecked();
                if (blanceCheck) {
                    //先检查是否设置了支付密码
                    checkUserPay(activity,orderId);
                } else if (weiXinCheck) {
                    mDialog.dismiss();
                    EventBus.getDefault().post(new MessageEvent(AppArouter.WALLET_PAY_SERVICE,orderId,activity,price,"wechat",type));
                } else if (zhifubaoCheck) {
                    mDialog.dismiss();
                    EventBus.getDefault().post(new MessageEvent(AppArouter.WALLET_PAY_SERVICE,orderId,activity,price,"alipay",type));
                }
            }
        });
        tvPrice.setText(price);
        rbBlance.setText("余额（可使用：¥" + balence + "）");
        mDialog.show();
    }

    /**
     * 是否设置支付密码
     * @param activity
     */
    private void checkUserPay(BaseActivity activity,String orderId) {
        showPayPwdDialog(activity,orderId);
    }

    /**
     * 没有设置支付密码弹出框
     * @param activity
     * @param orderId
     */
    private void notSetPayPwdDialog(BaseActivity activity, String orderId) {
        mDialog2 = new CustomDialog().showDialog(activity,
                "提示", "您还未设置密码哦~", "取消", "去设置", new CustomDialog.ClickCallBack() {
                    @Override
                    public void ok() {
                        if (mDialog2 != null) {
                            mDialog2.dismiss();
                        }
                    }

                    @Override
                    public void cacel() {
                        mDialog2.dismiss();
                    }
                });
    }


    private void payOk(Activity activity, String orderId) {
        EventBus.getDefault().post("update_userinfo");
        activity.setResult(101);
        ToastUtils.showToastOk(activity, "支付成功");
    }

    private void payFail(Activity activity, String orderId) {
        activity.setResult(101);
        ToastUtils.showToastOk(activity, "支付失败");
    }
    private void payCancel(Activity activity, String orderId) {
//        toOrderDetail(activity, orderId);
        //往订单详情页面跳转
        ARouter.getInstance().build(AppArouter.ORDER_ORDER_DETAIL_ACTIVITY)
                .withString("mOrderId",orderId).navigation();
    }


    Dialog customDialog;
    /**
     * 弹出输入密码的对话框
     */
    public void showPayPwdDialog(BaseActivity activity,String orderId){
        PwdAndNumberKeyboardDialog numberKeyboardView = new PwdAndNumberKeyboardDialog(activity,
                new PwdAndNumberKeyboardDialog.NumberPwdAndKeyBoradCallBack() {
                    @Override
                    public void complete(String content) {
                    }

                    @Override
                    public void forgetPwd() {
                        customDialog.dismiss();
                    }
                });
        customDialog = numberKeyboardView.showBottomView();
        customDialog.show();
    }
}
