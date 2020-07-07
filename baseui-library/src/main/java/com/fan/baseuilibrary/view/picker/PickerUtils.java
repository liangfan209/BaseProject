package com.fan.baseuilibrary.view.picker;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.fan.baseuilibrary.R;

import java.util.Calendar;
import java.util.Date;

import skin.support.content.res.SkinCompatResources;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/4/26
 * 版权：
 */
public class PickerUtils {



    public interface TimeCallBack{
       default void timeCallBack(String date){}
       default void timeCallBack(Date date){}
    }

    private MyTimePickerView pvTime;
    /**
     * 选择月份
     */
    public void showTimePick(Context context,TimeCallBack callBack,String selectTime){
        Date date = new Date();
        int month = date.getMonth();
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2010, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, month, date.getDate());
        pvTime = new MyTimePickerBuilder(context)
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
            @Override
            public void onTimeSelectChanged(Date date) {
                callBack.timeCallBack(date);
            }
        })
                .setType(new boolean[]{true, true, false, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .setTitleText("选择时间")
                .setSubmitColor(SkinCompatResources.getColor(context, R.color.ui_primary_color))
                .setCancelColor(SkinCompatResources.getColor(context,R.color.ui_primary_color))
                .setRangDate(startDate, endDate)
                .build(selectTime);
        pvTime.setTimeSelectListener(new MyOnTimeSelectListener() {
            @Override
            public void onTimeSelect(String date) {
                callBack.timeCallBack(date);
            }
        });

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            WindowManager systemService = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = systemService.getDefaultDisplay().getWidth();
            int height = systemService.getDefaultDisplay().getHeight();
            params.width = width;
            params.height = height;
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                WindowManager.LayoutParams attributes = dialogWindow.getAttributes();
                attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
                attributes.height = systemService.getDefaultDisplay().getHeight();
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
            }
        }
        pvTime.show();
    }


}
