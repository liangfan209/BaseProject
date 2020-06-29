package com.fan.baseuilibrary.view.picker;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.ISelectTimeCallback;
import com.bigkoo.pickerview.view.BasePickerView;
import com.bigkoo.pickerview.view.WheelTime;
import com.blankj.utilcode.util.StringUtils;
import com.fan.baseuilibrary.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间选择器
 * Created by Sai on 15/11/22.
 * Updated by XiaoSong on 2017-2-22.
 */
public class MyTimePickerView extends BasePickerView implements View.OnClickListener {

    private MyWheelTime wheelTime; //自定义控件
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    private MyOnTimeSelectListener mMyOnTimeSelectListener;
    private RadioGroup rg1, rg2;
    private RadioButton rb1, rb21, rb22;
    private String mSelectTime;
    private RelativeLayout mRltDel;


    private CheckBox cbSelect;

    public MyTimePickerView(PickerOptions pickerOptions, String selectTime) {
        super(pickerOptions.context);
        this.mSelectTime = selectTime;
        mPickerOptions = pickerOptions;
        initView(pickerOptions.context);
    }

    public void setTimeSelectListener(MyOnTimeSelectListener myOnTimeSelectListener){
        this.mMyOnTimeSelectListener = myOnTimeSelectListener;
    }

    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews();
        initAnim();


        if (mPickerOptions.customListener == null) {
            LayoutInflater.from(context).inflate(R.layout.layout_pickerview_time, contentContainer);
            //确定和取消按钮
            Button btnSubmit = (Button) findViewById(com.bigkoo.pickerview.R.id.btnSubmit);
            Button btnCancel = (Button) findViewById(com.bigkoo.pickerview.R.id.btnCancel);


            //顶部标题
            TextView tvTitle = (TextView) findViewById(com.bigkoo.pickerview.R.id.tvTitle);
            RelativeLayout rv_top_bar = (RelativeLayout) findViewById(com.bigkoo.pickerview.R.id.rv_topbar);


            btnSubmit.setTag(TAG_SUBMIT);
            btnCancel.setTag(TAG_CANCEL);

            btnSubmit.setOnClickListener(this);
            btnCancel.setOnClickListener(this);

            //设置文字
            btnSubmit.setText(TextUtils.isEmpty(mPickerOptions.textContentConfirm) ?
                    context.getResources().getString(com.bigkoo.pickerview.R.string.pickerview_submit) :
                    mPickerOptions.textContentConfirm);
            btnCancel.setText(TextUtils.isEmpty(mPickerOptions.textContentCancel) ?
                    context.getResources().getString(com.bigkoo.pickerview.R.string.pickerview_cancel) :
                    mPickerOptions.textContentCancel);
            tvTitle.setText(TextUtils.isEmpty(mPickerOptions.textContentTitle) ? "" : mPickerOptions.textContentTitle);//默认为空

            //设置color
            btnSubmit.setTextColor(mPickerOptions.textColorConfirm);
            btnCancel.setTextColor(mPickerOptions.textColorCancel);
            tvTitle.setTextColor(mPickerOptions.textColorTitle);
            rv_top_bar.setBackgroundColor(mPickerOptions.bgColorTitle);

            //设置文字大小
            btnSubmit.setTextSize(mPickerOptions.textSizeSubmitCancel);
            btnCancel.setTextSize(mPickerOptions.textSizeSubmitCancel);
            tvTitle.setTextSize(mPickerOptions.textSizeTitle);

        } else {
            mPickerOptions.customListener.customLayout(LayoutInflater.from(context).inflate(mPickerOptions.layoutRes,
                    contentContainer));
        }
        // 时间转轮 自定义控件
        LinearLayout timePickerView = (LinearLayout) findViewById(com.bigkoo.pickerview.R.id.timepicker);
        timePickerView.setBackgroundColor(mPickerOptions.bgColorWheel);

        initWheelTime(timePickerView);



        rb1 = (RadioButton) findViewById(R.id.rb_time1);
        rb21 = (RadioButton) findViewById(R.id.rb_time21);
        rb22 = (RadioButton) findViewById(R.id.rb_time22);
        rg1 = (RadioGroup) findViewById(R.id.rg1);
        rg2 = (RadioGroup) findViewById(R.id.rg2);
        mRltDel = (RelativeLayout) findViewById(R.id.rlt_delete);

        mRltDel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (cbSelect.isChecked()) {
                    //删除月
                    rb1.setText("");
                }else{
                    rb21.setText("");
                    rb22.setText("");
                }
            }
        });
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String dateStr = sdf.format(new Date());
//        rb21.setText(dateStr);
//            rb22.setText(dateStr);

        rb21.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setRbDate((RadioButton) v);
            }
        });
        rb22.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                setRbDate((RadioButton) v);
            }
        });
        cbSelect = (CheckBox) findViewById(R.id.cb_selecter);

        mSelectTime = mSelectTime.trim();
        if(mSelectTime.contains("至")){
            cbSelect.setChecked(false);
            String[] times = mSelectTime.split("至");
            //按日选择
            showTimeType(false);
            String time1 = times[0].trim();
            String time2 = times[1].trim();
            rb21.setText(time1);
            rb22.setText(time2);
            setRbDate(rb21);
//            setRbDate(rb22);
        }else{
            if(mSelectTime.length() <= 7){
                //选择的是月份
                showTimeType(true);
                rb1.setText(mSelectTime);
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
                try {
                    Date date;
                    if(mSelectTime.length() == 7){
                        date = sdf1.parse(mSelectTime);
                    }else{
                        date = new Date();
                        DateFormat df1 = new SimpleDateFormat("yyyy-MM");
                        rb1.setText(df1.format(new Date()));
                    }
                    cbSelect.setChecked(true);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    setDate(calendar);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else if(mSelectTime.length() == 10){
                //选择的当前日
                cbSelect.setChecked(false);
                showTimeType(false);
                rb21.setText(mSelectTime);
                setRbDate(rb21);
                rb22.setText("");
            }
        }

        cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean boo) {
                showTimeType(boo);
                if(!boo){
                    setRbDate(rb21);
                }else{
                    setRbDate(rb1);
                }
            }
        });

    }

    private void showTimeType(boolean boo){
        if (boo) {
            rg1.setVisibility(View.VISIBLE);
            rg2.setVisibility(View.GONE);
            cbSelect.setText("按月选择");
            wheelTime.setType(new boolean[]{true, true, false, false, false, false});
            wheelTime.update();
        } else {
            rg1.setVisibility(View.GONE);
            rg2.setVisibility(View.VISIBLE);
            cbSelect.setText("按日选择");
            wheelTime.setType(new boolean[]{true, true, true, false, false, false});
            wheelTime.update();
        }
    }

    private void setRbDate(RadioButton rb) {
        String rb1Str = rb.getText().toString();
        SimpleDateFormat sdf;
        if(cbSelect.isChecked()){
            sdf = new SimpleDateFormat("yyyy-MM");
        }else{
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }

        try {
            Date date;
            if (!StringUtils.isEmpty(rb1Str)){
                date = sdf.parse(rb1Str);
                rb.setText(rb1Str);
            }else{
                date = new Date();
                rb.setText(sdf.format(date));
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            setDate(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void returnData() {

        try {
            Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
            //先判断选择月 还是选择日
            if (cbSelect.isChecked()) {
                //判断月份
                mMyOnTimeSelectListener.onTimeSelect(rb1.getText().toString());
                mPickerOptions.timeSelectChangeListener.onTimeSelectChanged(date);
            } else {
                //判断日
                if(StringUtils.isEmpty(rb22.getText().toString())){
                    mMyOnTimeSelectListener.onTimeSelect(rb21.getText().toString());
                }else{
                    String rb21Str = rb21.getText().toString();
                    String rb22Str = rb22.getText().toString();
                    if(StringUtils.isEmpty(rb21Str)){
                        mMyOnTimeSelectListener.onTimeSelect(rb21Str);
                    }
                    if(Integer.valueOf(rb21Str.replaceAll("-",""))>Integer.valueOf(rb22Str.replaceAll("-",""))){
                        mMyOnTimeSelectListener.onTimeSelect(rb22Str+" 至 "+rb21Str);
                    }else if(Integer.valueOf(rb21Str.replaceAll("-","")) == Integer.valueOf(rb22Str.replaceAll("-",""))){
                        mMyOnTimeSelectListener.onTimeSelect(rb21Str);
                    }else{
                        mMyOnTimeSelectListener.onTimeSelect(rb21Str+" 至 "+rb22Str);
                    }

                }
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

//        if (mPickerOptions.timeSelectListener != null) {
//            try {
//                Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
//                mPickerOptions.timeSelectListener.onTimeSelect(date, clickView);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private void initWheelTime(LinearLayout timePickerView) {
        wheelTime = new MyWheelTime(timePickerView, mPickerOptions.type, mPickerOptions.textGravity,
                mPickerOptions.textSizeContent);
        if (mPickerOptions.timeSelectChangeListener != null) {
            wheelTime.setSelectChangeCallback(new ISelectTimeCallback() {
                @Override
                public void onTimeSelectChanged() {
                    try {
                        Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                        //先判断选择月 还是选择日
                        if (cbSelect.isChecked()) {
                            //判断月份
                            DateFormat dfm = new SimpleDateFormat("yyyy-MM");
                            rb1.setText(dfm.format(date));
//                            mPickerOptions.timeSelectChangeListener.onTimeSelectChanged(date);
                        } else {
                            //判断日
                            DateFormat dfd = new SimpleDateFormat("yyyy-MM-dd");
                            //判断左边右边
                            if (rb21.isChecked()) {
                                rb21.setText(dfd.format(date));
                            } else {
                                rb22.setText(dfd.format(date));
                            }
                        }


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        wheelTime.setLunarMode(mPickerOptions.isLunarCalendar);

        if (mPickerOptions.startYear != 0 && mPickerOptions.endYear != 0
                && mPickerOptions.startYear <= mPickerOptions.endYear) {
            setRange();
        }

        //若手动设置了时间范围限制
        if (mPickerOptions.startDate != null && mPickerOptions.endDate != null) {
            if (mPickerOptions.startDate.getTimeInMillis() > mPickerOptions.endDate.getTimeInMillis()) {
                throw new IllegalArgumentException("startDate can't be later than endDate");
            } else {
                setRangDate();
            }
        } else if (mPickerOptions.startDate != null) {
            if (mPickerOptions.startDate.get(Calendar.YEAR) < 1900) {
                throw new IllegalArgumentException("The startDate can not as early as 1900");
            } else {
                setRangDate();
            }
        } else if (mPickerOptions.endDate != null) {
            if (mPickerOptions.endDate.get(Calendar.YEAR) > 2100) {
                throw new IllegalArgumentException("The endDate should not be later than 2100");
            } else {
                setRangDate();
            }
        } else {//没有设置时间范围限制，则会使用默认范围。
            setRangDate();
        }

        setTime();
        wheelTime.setLabels(mPickerOptions.label_year, mPickerOptions.label_month, mPickerOptions.label_day
                , mPickerOptions.label_hours, mPickerOptions.label_minutes, mPickerOptions.label_seconds);
        wheelTime.setTextXOffset(mPickerOptions.x_offset_year, mPickerOptions.x_offset_month, mPickerOptions.x_offset_day,
                mPickerOptions.x_offset_hours, mPickerOptions.x_offset_minutes, mPickerOptions.x_offset_seconds);
        wheelTime.setItemsVisible(mPickerOptions.itemsVisibleCount);
        wheelTime.setAlphaGradient(mPickerOptions.isAlphaGradient);
        setOutSideCancelable(mPickerOptions.cancelable);
        wheelTime.setCyclic(mPickerOptions.cyclic);
        wheelTime.setDividerColor(mPickerOptions.dividerColor);
        wheelTime.setDividerType(mPickerOptions.dividerType);
        wheelTime.setLineSpacingMultiplier(mPickerOptions.lineSpacingMultiplier);
        wheelTime.setTextColorOut(mPickerOptions.textColorOut);
        wheelTime.setTextColorCenter(mPickerOptions.textColorCenter);
        wheelTime.isCenterLabel(mPickerOptions.isCenterLabel);
    }


    /**
     * 设置默认时间
     */
    public void setDate(Calendar date) {
        mPickerOptions.date = date;
        setTime();
    }

    /**
     * 设置可以选择的时间范围, 要在setTime之前调用才有效果
     */
    private void setRange() {
        wheelTime.setStartYear(mPickerOptions.startYear);
        wheelTime.setEndYear(mPickerOptions.endYear);

    }

    /**
     * 设置可以选择的时间范围, 要在setTime之前调用才有效果
     */
    private void setRangDate() {
        wheelTime.setRangDate(mPickerOptions.startDate, mPickerOptions.endDate);
        initDefaultSelectedDate();
    }

    private void initDefaultSelectedDate() {
        //如果手动设置了时间范围
        if (mPickerOptions.startDate != null && mPickerOptions.endDate != null) {
            //若默认时间未设置，或者设置的默认时间越界了，则设置默认选中时间为开始时间。
            if (mPickerOptions.date == null || mPickerOptions.date.getTimeInMillis() < mPickerOptions.startDate.getTimeInMillis()
                    || mPickerOptions.date.getTimeInMillis() > mPickerOptions.endDate.getTimeInMillis()) {
                mPickerOptions.date = mPickerOptions.startDate;
            }
        } else if (mPickerOptions.startDate != null) {
            //没有设置默认选中时间,那就拿开始时间当默认时间
            mPickerOptions.date = mPickerOptions.startDate;
        } else if (mPickerOptions.endDate != null) {
            mPickerOptions.date = mPickerOptions.endDate;
        }
    }

    /**
     * 设置选中时间,默认选中当前时间
     */
    private void setTime() {
        int year, month, day, hours, minute, seconds;
        Calendar calendar = Calendar.getInstance();

        if (mPickerOptions.date == null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            hours = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            seconds = calendar.get(Calendar.SECOND);
        } else {
            year = mPickerOptions.date.get(Calendar.YEAR);
            month = mPickerOptions.date.get(Calendar.MONTH);
            day = mPickerOptions.date.get(Calendar.DAY_OF_MONTH);
            hours = mPickerOptions.date.get(Calendar.HOUR_OF_DAY);
            minute = mPickerOptions.date.get(Calendar.MINUTE);
            seconds = mPickerOptions.date.get(Calendar.SECOND);
        }

        wheelTime.setPicker(year, month, day, hours, minute, seconds);
    }


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_SUBMIT)) {
            returnData();
        } else if (tag.equals(TAG_CANCEL)) {
            if (mPickerOptions.cancelListener != null) {
                mPickerOptions.cancelListener.onClick(v);
            }
        }
        dismiss();
    }



    /**
     * 动态设置标题
     *
     * @param text 标题文本内容
     */
    public void setTitleText(String text) {
        TextView tvTitle = (TextView) findViewById(com.bigkoo.pickerview.R.id.tvTitle);
        if (tvTitle != null) {
            tvTitle.setText(text);
        }
    }

    /**
     * 目前暂时只支持设置1900 - 2100年
     *
     * @param lunar 农历的开关
     */
    public void setLunarCalendar(boolean lunar) {
        try {
            int year, month, day, hours, minute, seconds;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(WheelTime.dateFormat.parse(wheelTime.getTime()));
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            hours = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            seconds = calendar.get(Calendar.SECOND);

            wheelTime.setLunarMode(lunar);
            wheelTime.setLabels(mPickerOptions.label_year, mPickerOptions.label_month, mPickerOptions.label_day,
                    mPickerOptions.label_hours, mPickerOptions.label_minutes, mPickerOptions.label_seconds);
            wheelTime.setPicker(year, month, day, hours, minute, seconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean isLunarCalendar() {
        return wheelTime.isLunarMode();
    }


    @Override
    public boolean isDialog() {
        return mPickerOptions.isDialog;
    }
}
