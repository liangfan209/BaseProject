package com.bq.walletapp.requset.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/6
 * 版权：
 */
public class TransactionListBean implements Serializable {

    /**
     * data_list : [{"remark":"2019-07-02 00:00:00 客户充值 ","id":1,"number":"TR1594010796","pay_type":"alipay",
     * "amount":-87322990,"create_time":"2020-07-06 12:46:36"},{"remark":"2019-07-02 00:00:00 客户充值 ","id":2,
     * "number":"TR1594010796","pay_type":"alipay","amount":-63221164,"create_time":"2020-07-06 12:46:36"},{"remark":"2019-07
     * -02 00:00:00 客户充值 ","id":3,"number":"TR1594010796","pay_type":"bank","amount":98321214,"create_time":"2020-07-06
     * 12:46:36"},{"remark":"2019-07-02 00:00:00 客户充值 ","id":4,"number":"TR1594010797","pay_type":"alipay","amount":-809855,
     * "create_time":"2020-07-06 12:46:37"},{"remark":"2019-07-03 00:00:00 客户充值 ","id":5,"number":"TR1594010797",
     * "pay_type":"alipay","amount":15207735,"create_time":"2020-07-06 12:46:37"},{"remark":"2019-07-03 00:00:00 客户充值 ","id":6,
     * "number":"TR1594010797","pay_type":"alipay","amount":-93851023,"create_time":"2020-07-06 12:46:37"},{"remark":"2019-07
     * -03 00:00:00 客户充值 ","id":7,"number":"TR1594010797","pay_type":"wechat","amount":64827158,"create_time":"2020-07-06
     * 12:46:37"},{"remark":"2019-07-04 00:00:00 客户充值 ","id":8,"number":"TR1594010797","pay_type":"alipay","amount":-95216609,
     * "create_time":"2020-07-06 12:46:37"},{"remark":"2019-07-04 00:00:00 客户充值 ","id":9,"number":"TR1594010798",
     * "pay_type":"bank","amount":-91069397,"create_time":"2020-07-06 12:46:38"},{"remark":"2019-07-04 00:00:00 客户充值 ","id":10,
     * "number":"TR1594010798","pay_type":"bank","amount":-55640469,"create_time":"2020-07-06 12:46:38"}]
     * total_page : 624
     * total : 6239
     */

    private int total_page;
    private int total;

    public List<TransactionInfo> getData_list() {
        return data_list;
    }

    public void setData_list(List<TransactionInfo> data_list) {
        this.data_list = data_list;
    }

    private List<TransactionInfo> data_list;

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }



}
