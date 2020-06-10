package com.bq.app;

import org.junit.Test;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/9
 * 版权：
 */
public class entry {

//    public static void main(String[] args) {
//
//    }
    @Test
    public void fun(){
        Bus bus = new Bus();
        StartHelper start = new StartHelper(bus);
        start.run();
    }
}
