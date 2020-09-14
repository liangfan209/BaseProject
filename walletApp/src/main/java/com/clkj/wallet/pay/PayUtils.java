package com.clkj.wallet.pay;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.request.RequestCallBackInter;
import com.bq.comm_config_lib.request.SignJsonCallBack;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.comm_config_lib.utils.PwdAndNumberKeyboardDialog;
import com.bq.netlibrary.NetManager;
import com.bq.netlibrary.http.BaseResponse;
import com.bq.utilslib.AppUtils;
import com.bq.utilslib.Md5Utils;
import com.clkj.wallet.R;
import com.clkj.wallet.pay.callback.IPayCallback;
import com.clkj.wallet.pay.weixin.WXPay;
import com.clkj.wallet.pay.weixin.WXPayInfoImpli;
import com.clkj.wallet.pay.zhifubao.AliPay;
import com.clkj.wallet.pay.zhifubao.AlipayInfoImpli;
import com.clkj.wallet.requset.bean.WxBean;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.dialog.CustomDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/4/26
 * 版权：
 */
public class PayUtils {


//    private CustomPopWindow mCustomPopWindow;
    private Dialog mDialog;
    private Dialog mDialog1;
    private Dialog mDialog2;

    private String phoneNumber = "";


    /**
     * @param activity 上下文
     * @param orderId  订单ID
     * @param price    支付总价格
     */
    public void getBancelAndshowBottomView(BaseActivity activity, String orderId, String price) {
//        String token = new UserUtils(activity).getToken();
//        activity.exeHttp(ApiServiceManager.getApi().partnerAccountBalance("partner.account.userbalance", "customer", "1", token
//                , "sha"))
//                .subscribe(new BaseSubscriber<BaseBean<BalanceBean>>(activity) {
//                    @Override
//                    public void result(BaseBean<BalanceBean> bean) {
//                        double balence = bean.getResult().getUser_balance();
//                        showBottomView(activity
//                                , orderId, price, balence + "");
//                    }
//                });
    }


    /**
     * @param activity 上下文
     * @param orderId  订单ID
     * @param price    支付总价格
     * @param balence  付款个人总余额
     */
    public void showBottomView(BaseActivity activity, String orderId, String price, String balence) {
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
            attributes.height = AppUtils.dp2px(activity,440);
            dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
        }



        iv_close.setOnClickListener(v -> {
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

        });
        TextView tvPrice = view.findViewById(R.id.tv_price);
        TextView tvBuy = view.findViewById(R.id.tv_buy);
        rbBlance.setEnabled(true);
        if (Double.valueOf(balence) < Double.valueOf(price)) {
            rbBlance.setEnabled(false);
            rbZhifubao.setChecked(true);
        }

        tvBuy.setOnClickListener(v -> {
//            mDialog.dismiss();
            boolean blanceCheck = rbBlance.isChecked();
            boolean weiXinCheck = rbWeixin.isChecked();
            boolean zhifubaoCheck = rbZhifubao.isChecked();
            String token = CommSpUtils.getToken();
            if (blanceCheck) {
                //先检查是否设置了支付密码
//                checkUserPay(activity,orderId);


            } else if (weiXinCheck) {
                mDialog.dismiss();
                Map<String,String> map = new HashMap<>();
                map.put("api", "customer.order.pay");
                map.put("order_id", orderId);
                map.put("pay_type", "wechat");
                map.put("auth", CommSpUtils.getToken());
                NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<WxBean>>(new RequestCallBackInter<WxBean>() {
                    @Override
                    public void onSuccess(WxBean bean) {
                        wechatPay(activity,bean.getPay_info());
                    }

                    @Override
                    public void onError(String msg) {
                    }
                }));


//                String weixinInfo = new Gson().toJson(new OrderInfoServiceBean(orderId, "3"));
//                activity.exeHttpWithDialog(ApiServiceManager.getApi().partnerGetWxOrder("order.pay", "customer", "1",
//                        token, "sha", weixinInfo))
//                        .subscribe(new BaseSubscriber<BaseBean<WxOrderInfo>>(activity) {
//                            @Override
//                            public void result(BaseBean<WxOrderInfo> bean) {
//                                WxOrderInfo result = bean.getResult();
//                                if (result == null)
//                                    return;
//                                wexinPay(activity, result.getWc(), new IPayCallback() {
//                                    @Override
//                                    public void success() {
//                                        payOk(activity, orderId);
//                                    }
//
//                                    @Override
//                                    public void failed() {
//                                        payFail(activity, orderId);
//                                    }
//
//                                    @Override
//                                    public void cancel() {
//                                        payCancel(activity,orderId);
//                                    }
//                                });
//                            }
//                        });

            } else if (zhifubaoCheck) {
                mDialog.dismiss();
//                String zhifubaoInfo = new Gson().toJson(new OrderInfoServiceBean(orderId, "8"));
//                activity.exeHttpWithDialog(ApiServiceManager.getApi().partnerGetAliOrder("order.pay", "customer", "1",
//                        token, "sha", zhifubaoInfo))
//                        .subscribe(new BaseSubscriber<BaseBean<String>>(activity) {
//                            @Override
//                            public void result(BaseBean<String> bean) {
//                                zhifubaoPay(activity, bean.getResult(), new IPayCallback() {
//                                    @Override
//                                    public void success() {
//                                        payOk(activity, orderId);
//                                    }
//
//                                    @Override
//                                    public void failed() {
//                                        payFail(activity, orderId);
//                                    }
//
//                                    @Override
//                                    public void cancel() {
//                                        payCancel(activity,orderId);
//                                    }
//                                });
//                            }
//                        });
            }
        });
        tvPrice.setText(price);
        rbBlance.setText("余额（可使用：¥" + balence + "）");
        mDialog.show();
//        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(activity)
//                .size(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//                .setView(view)
//                .enableBackgroundDark(true)
////                .setOutsideTouchable(false)
////                .setFocusable(false)
////                .setTouchable(true)
//                .setAnimationStyle(R.style.public_dialog_inout_anim)
//                .create()
//                .showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 是否设置支付密码
     * @param activity
     */
    private void checkUserPay(BaseActivity activity,String orderId) {
//        String token = new UserUtils(activity).getToken();
//        activity.exeHttpWithDialog(ApiServiceManager.getApi().partnerUserInfo("partner.account.userinfo", "customer", "1", token, "sha"))
//                .subscribe(new BaseSubscriber<BaseBean<UserInfoBean>>(activity) {
//                    @Override
//                    public void result(BaseBean<UserInfoBean> bean) {
//                        UserInfoBean result = bean.getResult();
//                        phoneNumber = result.getUser_phone();
//                        int is_pay_password = result.getIs_pay_password();
//                        if(is_pay_password == 0){
//                            //弹出对话框，告知还没有设置支付密码
//                            notSetPayPwdDialog(activity, orderId);
//
//
//                        }else{
//                            //弹出对话框
//                            showPayPwdDialog(activity,orderId);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                    }
//                });
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
                            //去下一个页面设置密码
                            //去设置设置密码
//                            Intent intent = new Intent(activity, PartnerVerificationCodeActivity.class);
//                            intent.putExtra("type",1);
//                            intent.putExtra("phone",phoneNumber);
//                            activity.startActivity(intent);
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
        new Handler().postDelayed(() -> {
            toOrderDetail(activity, orderId);
        }, 1000);
    }

    private void payFail(Activity activity, String orderId) {
        activity.setResult(101);
        ToastUtils.showToastOk(activity, "支付失败");
        new Handler().postDelayed(() -> {
            toOrderDetail(activity, orderId);

        }, 1000);
    }
    private void payCancel(Activity activity, String orderId) {
        toOrderDetail(activity, orderId);
    }


    public void toOrderDetail(Activity activity, String orderId) {
//        Intent intent = new Intent(activity, H5V2Activity.class);
//        //如果是确认订单、 h5v2中的订单需要关闭
//        if (activity instanceof ConfirmOrderActivity) {
//            intent.putExtra("h5url", Configuration.HHR_H5_API_NET + "/#/?id=" + orderId);
//            activity.startActivity(intent);
//            activity.finish();
//        } else if (activity instanceof H5V2Activity) {
//            ((H5V2Activity) activity).reload(orderId);
//        } else {
//            intent.putExtra("h5url", Configuration.HHR_H5_API_NET + "/#/?id=" + orderId);
//            activity.startActivity(intent);
//        }
    }

    private void wechatPay(Activity activiy, WxBean.PayInfo info){
        wexinPay(activiy, info, new IPayCallback() {
            @Override
            public void success() {
                activiy.finish();
            }
            @Override
            public void failed() {
            }
            @Override
            public void cancel() {
            }
        });
    }


    public static void wexinPay(Activity activiy, WxBean.PayInfo info, IPayCallback callBack) {
        //实例化微信支付策略
        String appid = "wx8aa0090b4ffd643f";
        WXPay wxPay = WXPay.getInstance(activiy, appid);
        String partnerid = "1602241032";
        //构造微信订单实体。一般都是由服务端直接返回。

        String timestamp = info.getTimestamp();
        String prepayid = info.getPrepayid();
        String noncestr = info.getNoncestr();
        String sign = info.getSign();

        WXPayInfoImpli wxPayInfoImpli = new WXPayInfoImpli();
        wxPayInfoImpli.setTimestamp(timestamp);
        wxPayInfoImpli.setSign(sign);
        wxPayInfoImpli.setPrepayId(prepayid);
        wxPayInfoImpli.setPartnerid(partnerid);
        wxPayInfoImpli.setAppid(appid);
        wxPayInfoImpli.setNonceStr(noncestr);
        wxPayInfoImpli.setPackageValue("Sign=WXPay");
        //策略场景类调起支付方法开始支付，以及接收回调。
        EasyPay.pay(wxPay, activiy, wxPayInfoImpli, callBack);
    }

    public static void zhifubaoPay(Activity activiy, String info, IPayCallback callBack) {
        //实例化支付宝支付策略
        AliPay aliPay = new AliPay();
        //构造支付宝订单实体。一般都是由服务端直接返回。
        AlipayInfoImpli alipayInfoImpli = new AlipayInfoImpli();
        alipayInfoImpli.setOrderInfo(info);
        //策略场景类调起支付方法开始支付，以及接收回调。
        EasyPay.pay(aliPay, activiy, alipayInfoImpli, callBack);
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
                        content = Md5Utils.md5(content);
                        String token = CommSpUtils.getToken();
//                        String blanceInfo = new Gson().toJson(new OrderInfoServiceBean(orderId, "2"));
//                        activity.exeHttpWithDialog(ApiServiceManager.getApi().partnerPayBalence("order.pay", "customer", "1",
//                                token, "sha", blanceInfo, content))
//                                .subscribe(new BaseSubscriber<BaseBean>(activity) {
//                                    @Override
//                                    public void result(BaseBean bean) {
//                                        if(mDialog != null){
//                                            mDialog.dismiss();
//                                        }
//                                        if(customDialog != null){
//                                            customDialog.dismiss();
//                                        }
//                                        payOk(activity, orderId);
//                                    }
//                                });
                    }

                    @Override
                    public void forgetPwd() {
                        customDialog.dismiss();
                        //忘记密码
//                        Intent intent = new Intent(activity,PartnerVerificationCodeActivity.class);
//                        intent.putExtra("type",2);
//                        intent.putExtra("phone",phoneNumber);
//                        activity.startActivity(intent);
                    }
                });
        customDialog = numberKeyboardView.showBottomView();
        customDialog.show();
    }


}
