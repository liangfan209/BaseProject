package com.bq.comm_config_lib.request;

import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.netlibrary.NetManager;
import com.bq.netlibrary.http.BaseResponse;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/8
 * 版权：
 */
public class ReNewFlagHelper {

    public static void renewFlag(Request request){

        LinkedHashMap<String, List<String>> urlParamsMap = request.getParams().urlParamsMap;
        Set<Map.Entry<String, List<String>>> entries = urlParamsMap.entrySet();
        boolean hasAuth = false;
        for (Map.Entry<String, List<String>> map : entries) {
            if(map.getKey().equals("auth")){
                hasAuth = true;
            }
//            if(map.getValue().equals("customer.account.token.renew")){
//                hasAuth = false;
//            }
        }
        if(!hasAuth)
            return;

        //获取过期时间
        String expireTime = CommSpUtils.getExpireTime();
        if(StringUtils.isEmpty(expireTime)){
            return;
        }
        long expireTimeLong =  Long.valueOf(expireTime);
        long currentTime = System.currentTimeMillis()/1000;

        //过期时间 大于 当前时间  0到60秒表示开始续签
        if(expireTimeLong - currentTime < 60 && expireTimeLong - currentTime >=0){
            Map<String,String> map = new HashMap<>();
            map.put("api", "customer.account.token.renew");
            map.put("auth_token", CommSpUtils.getToken());
            map.put("renew_flag", CommSpUtils.getRenewFlag());
            NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<LoginBean>>(){
                @Override
                public void onSuccess(Response<BaseResponse<LoginBean>> response) {
                    super.onSuccess(response);
                    BaseResponse<LoginBean> body = response.body();
                    LoginBean bean = body.result;
                    CommSpUtils.saveLoginInfo(new Gson().toJson(bean));
                }
            });
        }
    }
}
