package com.bq.comm_config_lib.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bq.comm_config_lib.R;
import com.bq.utilslib.AppUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 版权：
 */
public class PwdAndNumberKeyboardDialog {
    private Context mContext;
    private LinearLayout mLinearLayout;
    private NumberPwdAndKeyBoradCallBack mCallBack;

    private TextView mTvForgot;
    private PayPwdView mPayPwdView;
    private ImageView ivclose;

    private GridView gridView;
    private List<Integer> listNumber = new ArrayList<>();
    Integer[] ints = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, -1};
//    CustomPopWindow mCustomPopWindow;
    Dialog mDialog;

    public PwdAndNumberKeyboardDialog(Context context, NumberPwdAndKeyBoradCallBack callBack) {
        this.mContext = context;
        this.mCallBack = callBack;
    }

    public Dialog showBottomView() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_pwd_and_number_keyboard_, null);
        mTvForgot = contentView.findViewById(R.id.tv_forgot);
        mPayPwdView = contentView.findViewById(R.id.pay_pwd_view);
        ivclose = contentView.findViewById(R.id.iv_del);
        ivclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDialog != null){
                    mDialog.dismiss();
                }
            }
        });

        mPayPwdView.setCallBack(new PayPwdView.PayPwdViewInterface() {
            @Override
            public void updateText(String content) {
                if(content.length() == 6){
                    //发送请求验证
                    mCallBack.complete(content);
                }else{

                }
            }
        });

        mTvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.forgetPwd();
            }
        });

//        if (mCustomPopWindow == null) {
//            mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
//                    .size(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//                    .setView(contentView)
//                    .enableBackgroundDark(true)
//                    .setAnimationStyle(R.style.public_dialog_inout_anim)
//                    .create();
//        }
//        mCustomPopWindow.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        mDialog = new Dialog(mContext, R.style.custom_dialog2);
        mDialog.setContentView(contentView);
        mDialog.setCanceledOnTouchOutside(false);
        Window dialogWindow = mDialog.getWindow();
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.BOTTOM);//可以改成Bottom
            WindowManager.LayoutParams attributes = dialogWindow.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = AppUtils.dp2px(mContext,390);
            dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
        }

        initGridView(contentView);
//        return mCustomPopWindow;

        return mDialog;
    }

    void initGridView(View contentView) {
        gridView = contentView.findViewById(R.id.gv_pass);
        listNumber = Arrays.asList(ints);
        gridView.setAdapter(adapter);
    }


    /**
     * GridView的适配器
     */
    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return listNumber.size();
        }

        @Override
        public Object getItem(int position) {
            return listNumber.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_number_gridview, null);
                holder = new ViewHolder();
                holder.btnNumber = (TextView) convertView.findViewById(R.id.tvNumber);
                holder.ivDel = (ImageView) convertView.findViewById(R.id.ivDel);
                holder.rlt = convertView.findViewById(R.id.rlt);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //-------------设置数据----------------
            if(position != 11){
                holder.btnNumber.setText(listNumber.get(position) + "");
            }
            if (position == 9) {
                holder.rlt.setBackgroundResource(R.color.ui_txt_hint_color);
                holder.btnNumber.setText("");
            }
            if (position == 11) {
                holder.rlt.setBackgroundResource(R.color.ui_txt_hint_color);
//                holder.btnNumber.setBackground(mContext.getResources().getDrawable(listNumber.get(position)));
                holder.ivDel.setVisibility(View.VISIBLE);
                holder.btnNumber.setVisibility(View.GONE);
            }else{
                holder.ivDel.setVisibility(View.GONE);
                holder.btnNumber.setVisibility(View.VISIBLE);
            }
            holder.rlt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (position < 9) {
                        mPayPwdView.addNumber(position + 1);
                        return;
                    }
                    if (position == 10) {
                        mPayPwdView.addNumber(0);
                        return;
                    }
                    if (position == 11) {
                        mPayPwdView.delNumber();
                    }

                }
            });

            return convertView;
        }
    };

    public void clearAll(){
        mPayPwdView.clearAll();
    }

    static class ViewHolder {
        public TextView btnNumber;
        public RelativeLayout rlt;
        public ImageView ivDel;
    }

    public interface NumberPwdAndKeyBoradCallBack {
        void complete(String content);
        void forgetPwd();
    }
}
