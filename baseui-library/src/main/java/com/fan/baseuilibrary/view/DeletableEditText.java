package com.fan.baseuilibrary.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.fan.baseuilibrary.utils.Utils;

import androidx.appcompat.widget.AppCompatEditText;

public class DeletableEditText extends AppCompatEditText {

    private Drawable mRightDrawable;
    private boolean isHasFocus;



    public DeletableEditText(Context context) {
        this(context, null);
    }

    public DeletableEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public DeletableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupViews();
        setPadding(0,0, Utils.dp2px(getContext(),15),0);
        EmojiFilter.setEditTextFilter(this);
    }

    private void setupViews() {

        //取的view的上下左右边距
        Drawable[] drawables = this.getCompoundDrawables();

        // 取得right位置的Drawable
        // 即我们在布局文件中设置的android:drawableRight
        mRightDrawable = drawables[2];

        // 设置焦点变化的监听
        this.setOnFocusChangeListener(new FocusChangeListenerImpl());
        // 设置EditText文字变化的监听
        this.addTextChangedListener(new TextWatcherImpl());
        // 初始化时让右边clean图标不可见
        setClearDrawableVisible(false);



    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            //当点击松开时判断点击的位置。这里只进行了X轴方向的判断。
            case MotionEvent.ACTION_UP:
                //判断是否点击到了右边的图标区域
                boolean isClean = (event.getX() > (getWidth() - getTotalPaddingRight()))
                        && (event.getX() < (getWidth() - getPaddingRight()));
                if (isClean) {
                    //清除字符
                    setText("");
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private class FocusChangeListenerImpl implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            isHasFocus = hasFocus;
            if (isHasFocus) {
                boolean isVisible = getText().toString().length() >= 1;
                setClearDrawableVisible(isVisible);
            } else {
                setClearDrawableVisible(false);
            }
        }

    }

    // 当输入结束后判断是否显示右边clean的图标
    private class TextWatcherImpl implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            //当有字符时为true
            boolean isVisible = getText().toString().length() >= 1;
            //显示右边的图标
            setClearDrawableVisible(isVisible);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }

    }

    // 隐藏或显示右边clean的图标
    public void setClearDrawableVisible(boolean isVisible) {
        Drawable rightDrawable;
        if (isVisible) {
            rightDrawable = mRightDrawable;
        } else {
            rightDrawable = null;
        }
        // 使用代码设置该控件right处的图标
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], rightDrawable,
                getCompoundDrawables()[3]);
    }

    // 显示动画
    public void setShakeAnimation() {
        this.startAnimation(shakeAnimation(3));

    }

    // CycleTimes动画重复的次数
    public Animation shakeAnimation(int CycleTimes) {
        //设置偏移动画 其中new TranslateAnimation(0,10,0,10)四个值表示为 X坐标从0-->10,Y坐标从0-->10
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
        //设置动画次数
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        //设置动画间隔
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

}
