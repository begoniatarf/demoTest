package com.example.chat.chat.domain;

public class BaseMsg {
    private String type;
    private String client;
    private String target;
    private String msg;

    public BaseMsg(String type, String client, String target, String msg) {
        this.type = type;
        this.client = client;
        this.target = target;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
