package com.bq.user_center.requset.bean;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/22
 * 版权：
 */
public class CertificationInfo {
    private String name;
    private String identification;
    private String id_front;
    private String id_back;
    private String id_in_hand;

    public CertificationInfo(String name, String identification, String id_front, String id_back, String id_in_hand) {
        this.name = name;
        this.identification = identification;
        this.id_front = id_front;
        this.id_back = id_back;
        this.id_in_hand = id_in_hand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getId_front() {
        return id_front;
    }

    public void setId_front(String id_front) {
        this.id_front = id_front;
    }

    public String getId_back() {
        return id_back;
    }

    public void setId_back(String id_back) {
        this.id_back = id_back;
    }

    public String getId_in_hand() {
        return id_in_hand;
    }

    public void setId_in_hand(String id_in_hand) {
        this.id_in_hand = id_in_hand;
    }
}
