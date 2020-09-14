package com.clkj.wallet.requset.bean;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 版权：
 */

public class WxOrderInfo implements Serializable {


    /**
     * wc : {"appid":"wx22a8fc65e8d220af","sub_mch_id":null,"attach":null,"body":null,"mch_id":"1517459321",
     * "nonce_str":"a22c5c23e037b1d129dfbf0b9d650d63","notify_url":null,"out_trade_no":null,"spbill_create_ip":null,
     * "total_fee":null,"trade_type":null,"key":"rongmibiquan20181026172354biquan","sign":"E9A5A4D472AD1449806618B75C7D1225",
     * "prepayId":"wx27102811372969b4d062736a1227436200","timeNumber":"1569551291","transaction_id":null,"openid":null,
     * "out_refund_no":null,"refund_fee":null,"refund_fee_type":null,"refund_desc":null,"refund_account":null,"bank_type":null,
     * "cash_fee":null,"fee_type":null,"result_code":null,"return_code":null,"time_end":null,"is_subscribe":null}
     * order_id : 1177409569258082304
     */

    private WcBean wc;
    private String order_id;

    public WcBean getWc() {
        return wc;
    }

    public void setWc(WcBean wc) {
        this.wc = wc;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public static class WcBean {
        /**
         * appid : wx22a8fc65e8d220af
         * sub_mch_id : null
         * attach : null
         * body : null
         * mch_id : 1517459321
         * nonce_str : a22c5c23e037b1d129dfbf0b9d650d63
         * notify_url : null
         * out_trade_no : null
         * spbill_create_ip : null
         * total_fee : null
         * trade_type : null
         * key : rongmibiquan20181026172354biquan
         * sign : E9A5A4D472AD1449806618B75C7D1225
         * prepayId : wx27102811372969b4d062736a1227436200
         * timeNumber : 1569551291
         * transaction_id : null
         * openid : null
         * out_refund_no : null
         * refund_fee : null
         * refund_fee_type : null
         * refund_desc : null
         * refund_account : null
         * bank_type : null
         * cash_fee : null
         * fee_type : null
         * result_code : null
         * return_code : null
         * time_end : null
         * is_subscribe : null
         */

        private String appid;
        private Object sub_mch_id;
        private Object attach;
        private Object body;
        private String mch_id;
        private String nonce_str;
        private Object notify_url;
        private Object out_trade_no;
        private Object spbill_create_ip;
        private Object total_fee;
        private Object trade_type;
        private String key;
        private String sign;
        private String prepayId;
        private String timeNumber;
        private Object transaction_id;
        private Object openid;
        private Object out_refund_no;
        private Object refund_fee;
        private Object refund_fee_type;
        private Object refund_desc;
        private Object refund_account;
        private Object bank_type;
        private Object cash_fee;
        private Object fee_type;
        private Object result_code;
        private Object return_code;
        private Object time_end;
        private Object is_subscribe;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public Object getSub_mch_id() {
            return sub_mch_id;
        }

        public void setSub_mch_id(Object sub_mch_id) {
            this.sub_mch_id = sub_mch_id;
        }

        public Object getAttach() {
            return attach;
        }

        public void setAttach(Object attach) {
            this.attach = attach;
        }

        public Object getBody() {
            return body;
        }

        public void setBody(Object body) {
            this.body = body;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public Object getNotify_url() {
            return notify_url;
        }

        public void setNotify_url(Object notify_url) {
            this.notify_url = notify_url;
        }

        public Object getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(Object out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public Object getSpbill_create_ip() {
            return spbill_create_ip;
        }

        public void setSpbill_create_ip(Object spbill_create_ip) {
            this.spbill_create_ip = spbill_create_ip;
        }

        public Object getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(Object total_fee) {
            this.total_fee = total_fee;
        }

        public Object getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(Object trade_type) {
            this.trade_type = trade_type;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getTimeNumber() {
            return timeNumber;
        }

        public void setTimeNumber(String timeNumber) {
            this.timeNumber = timeNumber;
        }

        public Object getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(Object transaction_id) {
            this.transaction_id = transaction_id;
        }

        public Object getOpenid() {
            return openid;
        }

        public void setOpenid(Object openid) {
            this.openid = openid;
        }

        public Object getOut_refund_no() {
            return out_refund_no;
        }

        public void setOut_refund_no(Object out_refund_no) {
            this.out_refund_no = out_refund_no;
        }

        public Object getRefund_fee() {
            return refund_fee;
        }

        public void setRefund_fee(Object refund_fee) {
            this.refund_fee = refund_fee;
        }

        public Object getRefund_fee_type() {
            return refund_fee_type;
        }

        public void setRefund_fee_type(Object refund_fee_type) {
            this.refund_fee_type = refund_fee_type;
        }

        public Object getRefund_desc() {
            return refund_desc;
        }

        public void setRefund_desc(Object refund_desc) {
            this.refund_desc = refund_desc;
        }

        public Object getRefund_account() {
            return refund_account;
        }

        public void setRefund_account(Object refund_account) {
            this.refund_account = refund_account;
        }

        public Object getBank_type() {
            return bank_type;
        }

        public void setBank_type(Object bank_type) {
            this.bank_type = bank_type;
        }

        public Object getCash_fee() {
            return cash_fee;
        }

        public void setCash_fee(Object cash_fee) {
            this.cash_fee = cash_fee;
        }

        public Object getFee_type() {
            return fee_type;
        }

        public void setFee_type(Object fee_type) {
            this.fee_type = fee_type;
        }

        public Object getResult_code() {
            return result_code;
        }

        public void setResult_code(Object result_code) {
            this.result_code = result_code;
        }

        public Object getReturn_code() {
            return return_code;
        }

        public void setReturn_code(Object return_code) {
            this.return_code = return_code;
        }

        public Object getTime_end() {
            return time_end;
        }

        public void setTime_end(Object time_end) {
            this.time_end = time_end;
        }

        public Object getIs_subscribe() {
            return is_subscribe;
        }

        public void setIs_subscribe(Object is_subscribe) {
            this.is_subscribe = is_subscribe;
        }
    }
}
