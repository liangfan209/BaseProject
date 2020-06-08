package com.bq.netlibrary.http;

import com.bq.netlibrary.NetManager;
import com.lzy.okgo.OkGo;

import java.util.Map;

public class HttpNetManager extends NetManager {

    private HttpNetManager() {
    }

    public static HttpNetManager getInstance() {
        return NetHolder.sHttpNetManager;
    }

    @Override
    public <T> void request(Map<String, String> map, JsonCallback<T> callback) {
        OkGo.<T>post("http://1.1.1.1")
                .retryCount(1)
                .params(map)
                .execute(callback);
    }

    private static class NetHolder {
        private static HttpNetManager sHttpNetManager = new HttpNetManager();
    }

}