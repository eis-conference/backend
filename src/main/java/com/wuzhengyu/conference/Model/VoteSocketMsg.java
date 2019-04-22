package com.wuzhengyu.conference.Model;

public class VoteSocketMsg {
    private int type;// 类型0：主持人，1：普通用户.
    private String toUser;//接受者.
    private String msg;//消息
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getToUser() {
        return toUser;
    }
    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
