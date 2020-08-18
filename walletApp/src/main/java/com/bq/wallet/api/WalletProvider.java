package com.bq.wallet.api;

import android.app.Activity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.annotation.Register;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.msgService.MessageBody;
import com.bq.comm_config_lib.msgService.MessageEvent;
import com.bq.comm_config_lib.request.RequestCallBackInter;
import com.bq.wallet.pay.EasyPay;
import com.bq.wallet.pay.PayUtils;
import com.bq.wallet.pay.callback.IPayCallback;
import com.bq.wallet.pay.zhifubao.AliPay;
import com.bq.wallet.pay.zhifubao.AlipayInfoImpli;
import com.bq.wallet.requset.WalletHttpReqeustImp;
import com.bq.wallet.requset.bean.WxBean;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/9
 * 版权：
 */
@Register
public class WalletProvider {
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void providerData(MessageEvent event) {
        if (AppArouter.WALLET_BALANCE_SERVICE.equals(event.key)) {
//            event.eventInterface.callBack(new MessageBody(MessageBody.SUCCESS_CODE,"登录模块提供服务"));
            new WalletHttpReqeustImp().getBalance(new RequestCallBackInter() {
                @Override
                public void onSuccess(Object o) {
                    event.eventInterface.callBack(new MessageBody(MessageBody.SUCCESS_CODE,new Gson().toJson(o)));
                }
                @Override
                public void onError(String msg) {
                    event.eventInterface.callBack(new MessageBody(MessageBody.FAIL_CODE,msg));
                }
            });
        } else if(AppArouter.LOGOUT_SERVER.equals(event.key)){

        } else if(AppArouter.WALLET_PAY_SERVICE.equals(event.key)){
            new WalletHttpReqeustImp().pay(event.orderId,event.payType, new RequestCallBackInter<WxBean>() {
                @Override
                public void onSuccess(WxBean bean) {
                    if(event.payType.equals("wechat")){
                        wechatPay(event.activity,bean.getPay_info(),event.orderId,event.price);
                    }else if(event.payType.equals("alipay")){
                        zhifubaoPay(event.activity,bean.getPay_info().getPrepayid(),event.orderId,event.price);
                    }
                }
                @Override
                public void onError(String msg) {
//                    event.eventInterface.callBack(new MessageBody(MessageBody.FAIL_CODE,msg));
                }
            } );
        }
    }

    static void wechatPay(Activity activity, WxBean.PayInfo info,final String orderId,String price){
        PayUtils.wexinPay(activity,info,new IPayCallback(){
            @Override
            public void success() {
                ARouter.getInstance().build(AppArouter.ORDER_PAY_SUCCESS_ACTIVITY)
                        .withString("mOrderId",orderId)
                        .withString("mPrice",price).navigation();
                activity.finish();
            }
            @Override
            public void failed() {
                ARouter.getInstance().build(AppArouter.ORDER_ORDER_DETAIL_ACTIVITY)
                        .withString("mOrderId",orderId).navigation();
                activity.finish();
            }
            @Override
            public void cancel() {
                ARouter.getInstance().build(AppArouter.ORDER_ORDER_DETAIL_ACTIVITY)
                        .withString("mOrderId",orderId).navigation();
                activity.finish();
            }
        });
    }

    public static void zhifubaoPay(final Activity activity, String info,final String orderId,final String price) {
        //实例化支付宝支付策略
        AliPay aliPay = new AliPay();
        //构造支付宝订单实体。一般都是由服务端直接返回。
        AlipayInfoImpli alipayInfoImpli = new AlipayInfoImpli();
        alipayInfoImpli.setOrderInfo(info);
        //策略场景类调起支付方法开始支付，以及接收回调。
        EasyPay.pay(aliPay, activity, alipayInfoImpli,new IPayCallback(){
            @Override
            public void success() {
                ARouter.getInstance().build(AppArouter.ORDER_PAY_SUCCESS_ACTIVITY)
                        .withString("mOrderId",orderId)
                        .withString("mPrice",price).navigation();
                activity.finish();
            }
            @Override
            public void failed() {
                ARouter.getInstance().build(AppArouter.ORDER_ORDER_DETAIL_ACTIVITY)
                        .withString("mOrderId",orderId).navigation();
                activity.finish();
            }
            @Override
            public void cancel() {
                ARouter.getInstance().build(AppArouter.ORDER_ORDER_DETAIL_ACTIVITY)
                        .withString("mOrderId",orderId).navigation();
                activity.finish();
            }
        });
    }
}
