package com.bq.order.requset.bean;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/6
 * 版权：
 */
public class TransactionInfo implements Serializable {
    /**
     * remark : 2019-07-02 00:00:00 客户充值
     * id : 1
     * number : TR1594010796
     * pay_type : alipay
     * amount : -87322990
     * create_time : 2020-07-06 12:46:36
     */

    private String remark;
    private int id;
    private String number;
    private String pay_type;
    private int amount;
    private String create_time;
    private String status;

    private TransactionMonthInfo mMonthInfo;


    public String getStatus() {
        if("pay_finish".equals(status)){
            return "付款成功";
        }else if("transaction_dealing".equals(status)){
            return "交易处理中";
        }else if("account_finish".equals(status)){
            return "到账成功";
        }else if("account_fail".equals(status)){
            return "交易失败";
        }
        return "";
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public TransactionMonthInfo getMonthInfo() {
        return mMonthInfo;
    }

    public void setMonthInfo(TransactionMonthInfo monthInfo) {
        mMonthInfo = monthInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPay_type() {
        if("bank".equals(pay_type)){
            return "银行卡";
        }else if("alipay".equals(pay_type)){
            return "支付宝";
        }else if("wechat".equals(pay_type)){
            return "微信";
        }else if("balance".equals(pay_type)){
            return "余额";
        }
        return "";
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
