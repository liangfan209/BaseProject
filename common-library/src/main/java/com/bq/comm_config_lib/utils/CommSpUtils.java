package com.bq.comm_config_lib.utils;

import com.bq.comm_config_lib.service.SpField;
import com.bq.utilslib.SPUtils;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public class CommSpUtils {

    public static void saveToken(String token){
        SPUtils.getInstance(SpField.AUTH_TOKEN).put(SpField.AUTH_TOKEN,token);
    }

    public static String getToken(){
//        return "jz1hF77lRKdqMYmEHULdssdNPaoYJMfd1p9WjQ2O0uzJE3LGGlq9H-nTUps5eYXe";
        return SPUtils.getInstance(SpField.AUTH_TOKEN).getString(SpField.AUTH_TOKEN);
    }
}
