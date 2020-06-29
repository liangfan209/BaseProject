package com.bq.walletapp.api.bean;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/4/21
 * 版权：
 */
public class BankCard implements Serializable {

    /**
     * userId : 1197047394227892224
     * bankName : null
     * cardNo : 6217002870052254776
     * idCard : 420821198911160751
     * payeeName : 刘冬
     * mobile : 13918383354
     * isDelete : 0
     * pid : 1252940492428546048
     * createTime : 2020-04-22 20:41:07
     */

    private String userId;
    private String bankName;
    private String cardNo;
    private String idCard;
    private String payeeName;
    private String mobile;
    private int isDelete;
    private String pid;
    private String createTime;
    private String cardType;
    private String bankCode;
    private boolean isCheck;

    public BankCard(String bankName, String cardNo) {
        this.bankName = bankName;
        this.cardNo = cardNo;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
