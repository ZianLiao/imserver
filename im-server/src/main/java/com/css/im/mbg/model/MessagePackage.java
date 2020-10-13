package com.css.im.mbg.model;

import com.css.im.mbg.IMMessageType;

import java.util.Hashtable;

public class MessagePackage {
    //消息类型
    private IMMessageType MessageType;
    //操作结果
    private String Code;
    //操作类型
    private String Operate;
    //发送用户
    private String SendUser;
    //接收用户
    private String ReceiveUser;
    //描述
    private String Description;
    //集合对象
    private Hashtable<String, Object> Dict = new Hashtable<String, Object>();

    public MessagePackage() {
    }

    public MessagePackage(IMMessageType messageType, String code, String operate, String sendUser, String receiveUser, String description, Hashtable<String, Object> dict) {
        MessageType = messageType;
        Code = code;
        Operate = operate;
        SendUser = sendUser;
        ReceiveUser = receiveUser;
        Description = description;
        Dict = dict;
    }

    public Object getParameter(String key) {
        if (key == null)
            try {
                throw new Exception("key is null int the method getParameter of MessagePackage");
            } catch (Exception e) {
                e.printStackTrace();
            }
        if (!Dict.containsKey(key))
            return null;
        return Dict.get(key);
    }

//    public <T> T getParameter(String key, String inValid) {
//        Object obj = getParameter(key);
//        if (obj != null)
//            return (T) obj;
//        return (T) obj;
//    }

    public void setParameter(String key, Object obj) {
        if (key == null)
            try {
                throw new Exception("key is null int the method getParameter of MessagePackage");
            } catch (Exception e) {
                e.printStackTrace();
            }
        Dict.put(key, obj);
    }

    public void Reset() {
        MessageType = IMMessageType.mtLogin;
        Code = null;
        Operate = null;
        SendUser = null;
        ReceiveUser = null;
        Description = null;
        Dict.clear();
    }

    public IMMessageType getMessageType() {
        return MessageType;
    }

    public void setMessageType(IMMessageType messageType) {
        MessageType = messageType;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getOperate() {
        return Operate;
    }

    public void setOperate(String operate) {
        Operate = operate;
    }

    public String getSendUser() {
        return SendUser;
    }

    public void setSendUser(String sendUser) {
        SendUser = sendUser;
    }

    public String getReceiveUser() {
        return ReceiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        ReceiveUser = receiveUser;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Hashtable<String, Object> getDict() {
        return Dict;
    }

    public void setDict(Hashtable<String, Object> dict) {
        Dict = dict;
    }

    //	登陆状态
    public enum LoginState {
        //保留
        InValid,
        // 在线
        OnLine,
        // 忙碌
        Busy,
        // 离开
        Leave,
        // 离线
        OffLine
    }
}