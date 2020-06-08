package com.fan.baseuilibrary.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.fan.baseuilibrary.R;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 版权：
 */
public class CheckUtils {
    //检查手机号
    public static boolean checkPhoneNumber(Activity activity,String phone){
        boolean boo = false;
        phone = phone.replaceAll(" ","").trim();
        if(TextUtils.isEmpty(phone)){
            Utils.showToast(activity, R.string.phone_is_not_null);
            return boo;
        }
        if(!AccountValidatorUtil.isMobile(phone)){
            Utils.showToast(activity, R.string.phone_is_error);
            return boo;
        }
        boo = true;
        return  boo;
    }
}
