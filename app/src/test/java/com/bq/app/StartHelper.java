package com.bq.app;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/9
 * 版权：
 */
public class StartHelper {

    private Bus bus;

    public StartHelper(Bus bus){
        this.bus = bus;
    }

    public void step1(){
        System.out.println("step 1: 项目初始化环境");
    }

    public void step2(){
        System.out.println("step 2: 加载系统需要的中间件");
    }

    public void step3(){
        bus.run();
        System.out.println("step 3: 初始化总线");
    }

    public void step4(){
        System.out.println("step 4: 完成模块加载");
    }

    public void step5(){
        System.out.println("step 5: 构建基础运营管理者");
        this.bus.printAppModuls();
    }

    public void run(){
        step1();
        step2();
        step3();
        step4();
        step5();
    }
}
