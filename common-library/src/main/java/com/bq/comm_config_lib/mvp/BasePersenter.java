package com.bq.comm_config_lib.mvp;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/3/003
 * 版权：
 */
public interface BasePersenter extends LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(@NonNull LifecycleOwner owner);

//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    void onResume(@NonNull LifecycleOwner owner);
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//    void onPause(@NonNull LifecycleOwner owner);
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//    void onStop(@NonNull LifecycleOwner owner);
//
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(@NonNull LifecycleOwner owner);
}

