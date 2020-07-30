package com.bq.order.pay.zhifubao;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import java.util.Map;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2019/7/14/014
 * 版权：
 */
public class ZhiFuBaoPay {
    private Activity mActivity;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private PayInter inter;

    private Handler mHandler;

    public ZhiFuBaoPay(Activity activity) {
        mActivity = activity;
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    inter.payFial();
                }
            };
        };
    }

    public void  pay(PayInter inter){
        this.inter = inter;
//        CcPermissions.with(mActivity).permission(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .request(new Consumer() {
//                    @Override
//                    public void accept(List<String> granted, boolean isAll) {
//
//                        final String orderInfo = "123";   // 订单信息
//
//                        Runnable payRunnable = new Runnable() {
//
//                            @Override
//                            public void run() {
//                                PayTask alipay = new PayTask(mActivity);
//                                Map<String,String> result = alipay.payV2(orderInfo,true);
//                                Message msg = new Message();
//                                msg.what = SDK_PAY_FLAG;
//                                msg.obj = result;
//                                mHandler.sendMessage(msg);
//                            }
//                        };
//                        // 必须异步调用
//                        Thread payThread = new Thread(payRunnable);
//                        payThread.start();
//                    }
//                });
    }

    public interface PayInter{
        void payOk();
        void payFial();
    }
}
