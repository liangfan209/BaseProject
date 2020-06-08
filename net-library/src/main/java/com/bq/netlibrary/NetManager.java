package com.bq.netlibrary;

import com.bq.netlibrary.http.HttpNetManager;
import com.bq.netlibrary.http.JsonCallback;
import com.bq.netlibrary.tcp.TcpManager;

import java.util.Map;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/3/003
 * 版权：
 */
public abstract class NetManager {

    public static NetManager getNetManger(){
        return getNetManger(NetType.http);
    }
    public static NetManager getNetManger(NetType type){
        return type == NetType.http ? HttpNetManager.getInstance() : TcpManager.getInstance();
    }
    public abstract <T> void request(Map<String,String> map, JsonCallback<T> callback);

}
