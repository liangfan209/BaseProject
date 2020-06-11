package com.bq.netlibrary.http;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public int code;
    public String msg;
    public String status;
    public T result;

    @Override
    public String toString() {
        return "LzyResponse{\n" +//
               "\tcode=" + code + "\n" +//
               "\tmsg='" + msg + "\'\n" +//
               "\tresult=" + result + "\n" +//
               '}';
    }
}
