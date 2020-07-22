package com.bq.comm_config_lib.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.R;


public class PayPwdView extends RelativeLayout {


    /**
     * 1.点击该view的要弹出密码盘
     * 2.接收键盘来的数据
     * 3.数据满了之后，给出回调
     * 4 删除数据
     */

    PayPwdViewInterface mCallback;

    public interface PayPwdViewInterface{
        default void showKeyboard(){};
        default void complet(String content){};
        void updateText(String content);
    }


    private TextView[] mTvPass;//密码数字控件

    public PayPwdView(Context context) {
        this(context, null);
    }

    public PayPwdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    /**
     * 初始化
     */
    private void init(Context context) {
        View mPassLayout = LinearLayout.inflate(this.getContext(), R.layout.layout_pay_pwd_view, null);
        mTvPass = new TextView[6];                                  //密码控件
        mTvPass[0] = (TextView) mPassLayout.findViewById(R.id.tv_pass1);
        mTvPass[1] = (TextView) mPassLayout.findViewById(R.id.tv_pass2);
        mTvPass[2] = (TextView) mPassLayout.findViewById(R.id.tv_pass3);
        mTvPass[3] = (TextView) mPassLayout.findViewById(R.id.tv_pass4);
        mTvPass[4] = (TextView) mPassLayout.findViewById(R.id.tv_pass5);
        mTvPass[5] = (TextView) mPassLayout.findViewById(R.id.tv_pass6);

        clearAll();
        this.addView(mPassLayout);
        mPassLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCallback != null){
                    mCallback.showKeyboard();
                }
            }
        });
    }

    public void setCallBack(PayPwdViewInterface call){
        this.mCallback = call;
    }


    /**
     * 返回空数据是在第几个上面
     * @return 返回-1，表示内容已经满了
     */
    private int numIsEmpty(){
        int index = -1;
        for (int i = 0; i< mTvPass.length; i++){
            if(StringUtils.isEmpty(mTvPass[i].getText().toString().trim())){
                index = i;
                return index;
            }
        }
        return index;
    }

    public void addNumber(int num){
        int index = numIsEmpty();
        if(index == -1){
            return;
        }else{
            mTvPass[index].setText(num+"");
            mCallback.updateText(getText());
        }
        //如果满了之后给出回调
        if(numIsEmpty() == -1 && mCallback != null){
            //将内容取出来
            mCallback.complet(getText());
        }
    }

    public void delNumber(){
        int index = numIsEmpty();
        if(index == 0){
            return;
        }
        if(index == -1){
            index = 5;
        }else{
            index = index -1;
        }
        mTvPass[index].setText("");
        mCallback.updateText(getText());

    }

    public void clearAll(){
        for (int i = 0; i< mTvPass.length; i++){
            mTvPass[i].setText("");
            mTvPass[i].setClickable(false);
            mTvPass[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mCallback != null){
                        mCallback.showKeyboard();
                    }
                }
            });
        }
    }

    private String getText(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i< mTvPass.length; i++){
            sb.append( mTvPass[i].getText().toString().trim());
        }
        return sb.toString();
    }
}
