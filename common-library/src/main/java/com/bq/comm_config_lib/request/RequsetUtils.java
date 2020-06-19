package com.bq.comm_config_lib.request;

import com.bq.utilslib.rsa.RSA;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/8
 * 版权：
 */
public class RequsetUtils {

    /**
     * 模拟当前的加签规则
     * @param request
     * @return
     */
    public static Request signRequestParmas(Request request) {
        try {
            Field filed = Request.class.getDeclaredField("url");
            filed.setAccessible(true);
            filed.set(request,"http://192.168.3.213:8769/interface");
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.params("platform", "0");
        request.params("clientType", "1");
        request.params("flag", "customer");
        request.params("version", "1");
        request.params("signType", "sha");
        request.params("proType", "cs");
        request.params("timestamp", String.valueOf(System.currentTimeMillis()));
        LinkedHashMap<String, List<String>> urlParamsMap = request.getParams().urlParamsMap;
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, List<String>>> entries = urlParamsMap.entrySet();
        for (Map.Entry<String, List<String>> map : entries) {
            sb.append(map.getKey()).append("=").append(map.getValue().get(0)).append("&");
        }
        String bufferStr = sb.toString();
        bufferStr = bufferStr.substring(0, bufferStr.length() - 1);
        String unSign = RSA.getSign(bufferStr);
        String s1 = RSA.sha1(unSign);
        String sign = RSA.sampling(s1, RSA.requestBodyStr2Map(bufferStr), 1.4);
        request.params("sign", sign);
        return request;
    }
}
