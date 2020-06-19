package com.bq.comm_config_lib.msgService;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/17
 * 版权：
 */
public class MessageBody {

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = -1;


    private int code;
    private String content;

    public MessageBody() {
    }

    public MessageBody(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
