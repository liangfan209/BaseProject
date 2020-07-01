package com.fan.baseuilibrary.view.flycotablayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.fan.baseuilibrary.view.flycotablayout.widget.SkinCommonTabLayout;
import com.fan.baseuilibrary.view.flycotablayout.widget.SkinMsgView;
import com.fan.baseuilibrary.view.flycotablayout.widget.SkinSegmentTabLayout;
import com.fan.baseuilibrary.view.flycotablayout.widget.SkinSlidingTabLayout;

import androidx.annotation.NonNull;
import skin.support.app.SkinLayoutInflater;

/**
 * Created by ximsf on 2017/3/8.
 */

public class SkinFlycoTabLayoutInflater implements SkinLayoutInflater {
    @Override
    public View createView(@NonNull Context context, String name, @NonNull AttributeSet attrs) {
        View view = null;
        switch (name) {
            case "com.flyco.tablayout.SlidingTabLayout":
                view = new SkinSlidingTabLayout(context, attrs);
                break;
            case "com.flyco.tablayout.CommonTabLayout":
                view = new SkinCommonTabLayout(context, attrs);
                break;
            case "com.flyco.tablayout.SegmentTabLayout":
                view = new SkinSegmentTabLayout(context, attrs);
                break;
            case "com.flyco.tablayout.widget.MsgView":
                view = new SkinMsgView(context, attrs);
                break;
        }
        return view;
    }
}
