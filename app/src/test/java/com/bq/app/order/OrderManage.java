package com.bq.app.order;

import com.bq.app.register.Component;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/9
 * 版权：
 */
public class OrderManage extends Component {

    public OrderManage(){
        this.model = new OrderModel();
        this.view = new OrderView();
        this.process = new OrderProcess();
        this.routePath = "Order";
    }

}
