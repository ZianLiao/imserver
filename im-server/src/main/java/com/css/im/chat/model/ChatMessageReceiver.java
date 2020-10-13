package com.css.im.chat.model;

import java.io.Serializable;

public class ChatMessageReceiver implements Serializable {
    private String id;

    /**
     * 发送者消息id
     *
     * @mbg.generated
     */
    private String fromUid;

    /**
     * 接收者用户id
     *
     * @mbg.generated
     */
    private String toUid;

    /**
     * 消息id
     *
     * @mbg.generated
     */
    private String msgId;

    /**
     * 消息状态，0：尚未发送，1：已发送，2：已读
     *
     * @mbg.generated
     */
    private Short status;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFromUid() {
        return fromUid;
    }

    public void setFromUid(String fromUid) {
        this.fromUid = fromUid == null ? null : fromUid.trim();
    }

    public String getToUid() {
        return toUid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid == null ? null : toUid.trim();
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fromUid=").append(fromUid);
        sb.append(", toUid=").append(toUid);
        sb.append(", msgId=").append(msgId);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}