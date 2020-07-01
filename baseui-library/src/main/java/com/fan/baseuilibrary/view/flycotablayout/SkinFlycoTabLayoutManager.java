package com.fan.baseuilibrary.view.flycotablayout;

import android.content.Context;

import skin.support.SkinCompatManager;

/**
 * Created by ximsf on 2017/3/8.
 */

public class SkinFlycoTabLayoutManager {
    private static volatile SkinFlycoTabLayoutManager sInstance;

    public static SkinFlycoTabLayoutManager init(Context context) {
        if (sInstance == null) {
            synchronized (SkinFlycoTabLayoutManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinFlycoTabLayoutManager(context);
                }
            }
        }
        return sInstance;
    }

    public static SkinFlycoTabLayoutManager getInstance() {
        return sInstance;
    }

    private SkinFlycoTabLayoutManager(Context context) {
        SkinCompatManager.init(context).addInflater(new com.fan.baseuilibrary.view.flycotablayout.SkinFlycoTabLayoutInflater());
    }
}
