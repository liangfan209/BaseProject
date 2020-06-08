package com.fan.baseuilibrary;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/5/29
 * 版权：
 */
@Interceptor(priority = 2)
public class TestInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        System.out.println("=============="+ postcard.getPath());
    }

    @Override
    public void init(Context context) {

    }
}
