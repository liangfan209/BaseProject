package com.bq.walletapp.api.bean;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/6
 * 版权：
 */
public class TransactionMonthInfo implements Serializable {


    /**
     * year : 2020
     * income : 90
     * month : 7
     * expense : 70
     */

    private int year;
    private int income;
    private int month;
    private String expense;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }
}