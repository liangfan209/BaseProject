package com.bq.user_center.mvp.bankcard.ui;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.bq.user_center.R;
import com.fan.baseuilibrary.view.CustomPopWindow;


/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2019/3/29/029
 * 版权：
 */
public class BankCardOption {
    private Context mContext;
    private CustomPopWindow mCustomPopWindow;
    PopupWindow.OnDismissListener mOnDismissListener;

    public interface BankCardOptionInter {
        void unBind();

        default void cancel() {

        }
    }

    public BankCardOption(Context context) {
        this.mContext = context;
    }
    public BankCardOption(Context context, PopupWindow.OnDismissListener listener) {
        this.mContext = context;
        this.mOnDismissListener = listener;
    }

    public void showBottomView(final BankCardOptionInter callBack) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.user_center_bankcard_option, null);
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .size(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .setView(contentView)
                .enableBackgroundDark(true)
                .setAnimationStyle(R.style.public_dialog_inout_anim)
                .setOnDissmissListener(mOnDismissListener)
                .create()

                .showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        //处理popWindow 显示内容
        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mCustomPopWindow.dissmiss();
                if(view.getId() == R.id.tv_unbind){
                    callBack.unBind();
                }
            }
        };
        contentView.findViewById(R.id.tv_unbind).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(listener);
    }
}
