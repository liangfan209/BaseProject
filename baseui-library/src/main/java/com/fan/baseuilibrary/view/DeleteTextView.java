package com.fan.baseuilibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatTextView;

public class DeleteTextView extends AppCompatTextView {

    private DeleteCallBack mDeleteCallBack;
    public static interface DeleteCallBack{
        void delete();
    }

    public DeleteTextView(Context context) {
        this(context, null);
    }
    public DeleteTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }
    public DeleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCallBack(DeleteCallBack inter){
        this.mDeleteCallBack = inter;
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
                    this.mDeleteCallBack.delete();
//                    setVisibility(View.GONE);
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

}
