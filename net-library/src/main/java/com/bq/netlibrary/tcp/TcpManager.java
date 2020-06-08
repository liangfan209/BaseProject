package com.bq.netlibrary.tcp;

import com.bq.netlibrary.NetManager;
import com.bq.netlibrary.http.JsonCallback;

import java.util.Map;

public class TcpManager extends NetManager {

    private TcpManager() {
    }

    public static TcpManager getInstance(){
        return TcpHodel.mTcpManager;
    }

    @Override
    public <T> void request(Map<String, String> map, JsonCallback<T> callback) {

    }

    private static class TcpHodel{
       private static TcpManager mTcpManager = new TcpManager();
   }
}
