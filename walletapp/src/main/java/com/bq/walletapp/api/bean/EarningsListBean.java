package com.bq.walletapp.api.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/4/21
 * 版权：
 */
public class EarningsListBean {


    private String totalPage;
    private double totalMoney;
    private List<EarningBean> list;




    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public List<EarningBean> getList() {
        return list;
    }

    public void setList(List<EarningBean> list) {
        this.list = list;
    }

    public static class MonthDateDto implements Serializable{


        public MonthDateDto(Double monthAllEntry, Double monthAllOut, String monthStr) {
            this.monthAllEntry = monthAllEntry;
            this.monthAllOut = monthAllOut;
            this.monthStr = monthStr;
        }

        private Double monthAllEntry;
        private Double monthAllOut;
        private String monthStr;

        public Double getMonthAllEntry() {
            return monthAllEntry;
        }

        public void setMonthAllEntry(Double monthAllEntry) {
            this.monthAllEntry = monthAllEntry;
        }

        public Double getMonthAllOut() {
            return monthAllOut;
        }

        public void setMonthAllOut(Double monthAllOut) {
            this.monthAllOut = monthAllOut;
        }

        public String getMonthStr() {
            return monthStr;
        }

        public void setMonthStr(String monthStr) {
            this.monthStr = monthStr;
        }
    }

    public static class EarningBean implements Serializable {
        /**
         * pid : 1660315740
         * money : 32.41
         * remark : 设备326562658656在2020年04月20日激活，平台给予奖励32.41元
         * income_type : 激活
         * time : 2020-04-20 12:20:58
         */

        private String pid;
        private String money;
        private String remark;
        private String income_type;
        private String time;
        private String status;
        private String create_time;
        private String arrive_time;
        private MonthDateDto monthDataDto;

        public MonthDateDto getMonthDataDto() {
            return monthDataDto;
        }

        public void setMonthDataDto(MonthDateDto monthDataDto) {
            this.monthDataDto = monthDataDto;
        }

        public String getArrive_time() {
            return arrive_time;
        }

        public void setArrive_time(String arrive_time) {
            this.arrive_time = arrive_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getIncome_type() {
            return income_type;
        }

        public void setIncome_type(String income_type) {
            this.income_type = income_type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
