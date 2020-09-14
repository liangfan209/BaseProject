package com.clkj.wallet.requset.bean;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/24
 * 版权：
 */
public class WxBean {
    private  PayInfo pay_info;

    public PayInfo getPay_info() {
        return pay_info;
    }

    public void setPay_info(PayInfo pay_info) {
        this.pay_info = pay_info;
    }

    public static class PayInfo{
        private String noncestr;
        private String prepayid;
        private String sign;
        private String timestamp;

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

}
