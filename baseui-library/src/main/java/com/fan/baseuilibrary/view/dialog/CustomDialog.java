package com.fan.baseuilibrary.view.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fan.baseuilibrary.R;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/19/019
 * 版权：
 */
public class CustomDialog {
    private Dialog mDialog;
    private TextView sureBt, cancelBt, oneSureBt;
    private TextView titleTv, msgTv;
    private LinearLayout contentLlt, llt_bottom, llt_dialog, llt_bottom1;
    private ClickCallBack mClickCallBack;


    /**
     * 显示对话框形式的自定义view
     *
     * @param context 上下文
     * @param view    自定义的view
     * @param size    距离左右的间距  0.9f
     * @param type    选择哪一种类型  Gravity.CENTER  TOP Bottom
     */
    public void showCustonView(Context context, View view, float size, int type) {
        initLayoutView(context, null, size, type);
        titleTv.setVisibility(View.GONE);
        if (contentLlt != null) {
            contentLlt.removeAllViews();
            contentLlt.addView(view);
        }
        if (llt_bottom != null) {
            llt_bottom.setVisibility(View.GONE);
        }
        if (llt_dialog != null) {
            llt_dialog.setBackground(null);
        }
    }


    /**
     * @param context
     * @param title
     * @param msg
     * @param mClickCallBack
     */
    public void showDialogDialog(Context context, String title, String msg, ClickCallBack mClickCallBack) {
        initLayoutView(context, mClickCallBack, 0.7f, Gravity.CENTER);
        titleTv.setText(title);
        cancelBt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        msgTv.setText(msg);
    }


    @SuppressLint("ResourceType")
    public void showDialogOneButton(Context context, String title, String msg, String txt, ClickCallBack mClickCallBack) {
        initLayoutView(context, mClickCallBack, 0.7f, Gravity.CENTER);
        titleTv.setText(title);
        msgTv.setText(msg);
        msgTv.setTextSize(15);
        llt_bottom.setVisibility(View.GONE);
        llt_bottom1.setVisibility(View.VISIBLE);
        oneSureBt.setText(txt);
    }

    @SuppressLint("ResourceType")
    public Dialog showDialog(Context context, String title, String msg, String leftBtStr, String rightBtStr,
                             ClickCallBack mClickCallBack) {
        initLayoutView(context, mClickCallBack, 0.7f, Gravity.CENTER);
        titleTv.setText(title);
        msgTv.setText(msg);
        cancelBt.setText(leftBtStr);
        cancelBt.setTextColor(Color.parseColor("#999999"));
        cancelBt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        sureBt.setText(rightBtStr);
        return mDialog;
    }


    /**
     * @param context 上下文
     * @param view    自定义的view
     * @param size    宽度的
     */
    private void initDialog(Context context, View view, float size, int type) {
        mDialog = new Dialog(context, R.style.custom_dialogTheme);
        mDialog.setContentView(view);
        Window window = mDialog.getWindow();
        window.setGravity(type);
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        //设置这个dialog的宽高
        view.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * size), FrameLayout.LayoutParams.WRAP_CONTENT));
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }


    private void initLayoutView(Context context, ClickCallBack clickCallBack, float size, int type) {
        View view = LinearLayout.inflate(context, R.layout.public_layout_dialog, null);
        this.mClickCallBack = clickCallBack;
        sureBt = view.findViewById(R.id.tv_ok);
        oneSureBt = view.findViewById(R.id.tv_bottom);
        cancelBt = view.findViewById(R.id.tv_cancel);
        contentLlt = view.findViewById(R.id.llt_content);
        llt_bottom = view.findViewById(R.id.llt_bottom);
        llt_bottom1 = view.findViewById(R.id.llt_bottom1);
        llt_dialog = view.findViewById(R.id.llt_dialog);
        titleTv = view.findViewById(R.id.tv_msg_title);
        msgTv = view.findViewById(R.id.tv_msg);

        if (mClickCallBack != null) {
            sureBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dissMissLoginDialog();
                    mClickCallBack.ok();
                }
            });
            oneSureBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dissMissLoginDialog();
                    mClickCallBack.ok();
                }
            });
            cancelBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dissMissLoginDialog();
                    mClickCallBack.cacel();
                }
            });

        } else {
            sureBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dissMissLoginDialog();
                }
            });
        }
        initDialog(context, view, size, type);
    }

    public void dissMissLoginDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public void dissMissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }


    public interface ClickCallBack {
        void ok();

        void cacel();
    }
}
