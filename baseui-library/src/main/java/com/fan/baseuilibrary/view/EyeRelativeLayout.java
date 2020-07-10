package com.fan.baseuilibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.bq.utilslib.AppUtils;
import com.fan.baseuilibrary.R;

import skin.support.widget.SkinCompatCheckBox;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/10
 * 版权：
 */
public class EyeRelativeLayout extends RelativeLayout {

    private DeletableEditText mDeletableEditText;
    private SkinCompatCheckBox mSkinCompatCheckBox;
    private boolean mIsEye;

    public EyeRelativeLayout(Context context) {
        super(context);
    }
    public EyeRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public EyeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View viewLayout = LayoutInflater.from(context).inflate(R.layout.layout_eye, this);
        mDeletableEditText = viewLayout.findViewById(R.id.et_pwd);
        mSkinCompatCheckBox = viewLayout.findViewById(R.id.cb_eye);

        mSkinCompatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mDeletableEditText.setInputType(128);
                }else{
                    mDeletableEditText.setInputType(129);
                }
            }
        });
    }

    public void setHintText(String txt){
        mDeletableEditText.setHint(txt);
    }
    public void setEye(boolean isEye){
        mIsEye  = isEye;
        if(mIsEye){
            mSkinCompatCheckBox.setVisibility(View.VISIBLE);
        }else{
            mSkinCompatCheckBox.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = (LayoutParams) mDeletableEditText.getLayoutParams();
            layoutParams.setMargins(0,0, AppUtils.dp2px(this.getContext(),15),0);
            mDeletableEditText.setLayoutParams(layoutParams);
        }
    }

    public String getText(){
        return mDeletableEditText.getText().toString().trim();
    }
}
