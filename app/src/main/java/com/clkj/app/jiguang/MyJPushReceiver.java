package com.clkj.app.jiguang;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 *
 * <p>如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyJPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";
    private String mTitle;
    private String mMessage;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.d(
                        TAG,
                        "[MyReceiver] 接收到推送下来的自定义消息: "
                                + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                String str = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                String type = bundle.getString(JPushInterface.EXTRA_TITLE);
                EventBus.getDefault().post("un_message");
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知alert: " + alert);
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知extra: " + extra);
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
                receivingNotification(context, bundle);
                EventBus.getDefault().post("un_message");
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

                mMessage = bundle.getString(JPushInterface.EXTRA_EXTRA);
                DataBean dataBean = new Gson().fromJson(mMessage, DataBean.class);
                if("contract".equals(dataBean.getType())){
                    //跳转到合同界面
                    ARouter.getInstance().build(AppArouter.ORDER_SIGN_CONTRACT_ACTIVITY)
                            .withInt("sign",1)
                            .withString("mOrderDetailId",dataBean.getId()+"").navigation();

                }


//                if(url == null){
//                    Intent intent2 = new Intent(context, MainActivity.class);
//                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent2);
//                    return;
//                }
//
//                if (url.contains("http")) {
//                    Intent intent1 = new Intent(context, H5Activity.class);
//                    intent1.putExtra(H5Activity.H5_INTENT_ULR, Utils.getH5url(url));
//                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent1);
//                } else if (url.contains("app://")) {
//                    Intent intent2 = new Intent(context, MainActivity.class);
//                    intent2.putExtra("go_url", url);
////                    intent2.putExtra("go_url", "app://home/message?type=3/msgDetail?id=123");
//                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent2);
//                } else {
//                    Intent intent2 = new Intent(context, MainActivity.class);
//                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent2);
//                }


                // 打开自定义的Activity
//                openNotification(context, bundle);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.d(
                        TAG,
                        "[MyReceiver] 用户收到到RICH PUSH CALLBACK: "
                                + bundle.getString(JPushInterface.EXTRA_EXTRA));
                // 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected =
                        intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                //                LogUtils.warnInfo(TAG, "[MyReceiver]" + intent.getAction() + "
                // connected state change to " + connected);
            } else {
                Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
        mTitle = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.d(TAG, " title : " + mTitle);
        mMessage = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.d(TAG, "message : " + mMessage);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d(TAG, "extras : " + extras);
    }

    private void openNotification(Context context, Bundle bundle) {


        //        ARouter.getInstance().build(RouterHub.MINE_DETAIL_ACTIVITY)
        //                .withString("title",mTitle =
        // bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE))
        //                .withString("message",mTitle =
        // bundle.getString(JPushInterface.EXTRA_ALERT))
        //                .navigation(context);
    }

    /**
     * 设置通知提示方式 - 基础属性
     */
    private void setStyleBasic(Context context) {
//        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
//        builder.statusBarDrawable = R.mipmap.icon_app_logo;
//        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
//        builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification
//        // .DEFAULT_VIBRATE）
//        JPushInterface.setPushNotificationBuilder(1, builder);
    }

}
