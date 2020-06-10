package com.bq.comm_config_lib.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.bq.utilslib.rsa.AccountValidatorUtil;
import com.fan.baseuilibrary.R;
import com.fan.baseuilibrary.utils.Utils;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/2
 * 版权：
 */
public class CheckUtils {
    //检查手机号
    public static boolean checkPhoneNumber(Activity activity,String phone){
        boolean b = false;
        phone = phone.replaceAll(" ","").trim();
        if(TextUtils.isEmpty(phone)){
            Utils.showToast(activity, R.string.phone_is_not_null);
            return b;
        }
        if(!AccountValidatorUtil.isMobile(phone)){
            Utils.showToast(activity, R.string.phone_is_error);
            return b;
        }
        b = true;
        return  b;
    }
}
