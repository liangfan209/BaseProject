package com.clkj.login.finger.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author ：wuchao on 2020/10/16 10：56
 * @describe ：指纹背景、动画
 * email: 519510228@qq.com
 */

public class FingerDrawableBg extends Drawable {

    private Rect mRect;
    private Paint mPaint;
    private Paint mCirlePaint;
    private int mHeight;
    private int mWidth;
    private Float mRadio;
    private int mW, mH;

    public FingerDrawableBg(Context context, int mW, int mH) {
        this.mW = mW;
        this.mH = mH;
        init();
    }

    private void init() {
        mRect = new Rect(0, 0, mW, mH);
        mHeight = mH;
        mWidth = mW;
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mCirlePaint = new Paint();
        mRadio = 0f;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawRect(mRect, mPaint);
        canvas.drawCircle((float) mWidth / 2, (float) mHeight / 2, mRadio, mCirlePaint);
    }

    public void updateBgColor(@ColorInt int mbgColor) {
        mCirlePaint.setColor(mbgColor);
        starAmni();
    }

    private void starAmni() {
        ValueAnimator move1 = ValueAnimator.ofFloat(0f, mWidth / 2);
        move1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRadio = (Float) animation.getAnimatedValue();
                invalidateSelf();
            }
        });
        move1.setDuration(300);
        move1.setInterpolator(new AccelerateDecelerateInterpolator());
        move1.start();
    }


    @Override
    public void setAlpha(int alpha) {
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }
}
