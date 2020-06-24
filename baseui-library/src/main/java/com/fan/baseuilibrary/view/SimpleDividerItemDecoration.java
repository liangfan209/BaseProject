package com.fan.baseuilibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 文件名：  SimpleDividerItemDecoration
 */
public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {

    //分割线Drawable
    private Drawable mDivider;
    //分割线高度
    private int mDividerHeight;
    //分割线缩进值
    private int leftInsert = 0;
    private int rightInsert = 0;

    /**
     * 使用line_divider中定义好的颜色
     *
     * @param context
     * @param dividerHeight 分割线高度
     */
//    public SimpleDividerItemDecoration(Context context, int dividerHeight) {
//        mDivider = ContextCompat.getDrawable(context, R.drawable.public_line_divider);
//        mDividerHeight = dividerHeight;
//    }
    public SimpleDividerItemDecoration(Context context, int color, int dividerHeight, int left, int right) {
        mDivider = ContextCompat.getDrawable(context, color);
        mDividerHeight = dividerHeight;
        this.leftInsert = left;
        this.rightInsert = right;
    }


    public SimpleDividerItemDecoration(Context context, int color, int dividerHeight) {
        mDivider = ContextCompat.getDrawable(context, color);
        mDividerHeight = dividerHeight;
    }


    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        //最后一个item不画分割线
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
//            int bottom = top + mDividerHeight;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left + leftInsert, top, right - rightInsert, bottom);
            if (i == childCount - 1) return;
            mDivider.draw(c);
        }
    }

}
