package com.css.im.chat.model;

import java.io.Serializable;

public class ChatMessageSend implements Serializable {
    private String msgId;

    /**
     * 发送者
     *
     * @mbg.generated
     */
    private String fromUid;

    /**
     * 接收者
     *
     * @mbg.generated
     */
    private String toUid;

    /**
     * 消息创建时间
     *
     * @mbg.generated
     */
    private String msgTime;

    /**
     * 序列号
     *
     * @mbg.generated
     */
    private String msgSeq;

    /**
     * 消息类型
     *
     * @mbg.generated
     */
    private Short contentType;

    /**
     * 关联的文件id
     *
     * @mbg.generated
     */
    private String fileId;

    /**
     * 群组id
     *
     * @mbg.generated
     */
    private String chatGroupId;

    /**
     * 是否召回，1：召回，0：正常
     *
     * @mbg.generated
     */
    private Short recall;

    /**
     * 消息类型0：单聊，1：群聊，2：广播消息
     *
     * @mbg.generated
     */
    private Integer chatType;

    /**
     * 文本消息的文字内容
     *
     * @mbg.generated
     */
    private String plainText;

    /**
     * 消息体
     *
     * @mbg.generated
     */
    private String content;

    private static final long serialVersionUID = 1L;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
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

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime == null ? null : msgTime.trim();
    }

    public String getMsgSeq() {
        return msgSeq;
    }

    public void setMsgSeq(String msgSeq) {
        this.msgSeq = msgSeq == null ? null : msgSeq.trim();
    }

    public Short getContentType() {
        return contentType;
    }

    public void setContentType(Short contentType) {
        this.contentType = contentType;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public String getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(String chatGroupId) {
        this.chatGroupId = chatGroupId == null ? null : chatGroupId.trim();
    }

    public Short getRecall() {
        return recall;
    }

    public void setRecall(Short recall) {
        this.recall = recall;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText == null ? null : plainText.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", msgId=").append(msgId);
        sb.append(", fromUid=").append(fromUid);
        sb.append(", toUid=").append(toUid);
        sb.append(", msgTime=").append(msgTime);
        sb.append(", msgSeq=").append(msgSeq);
        sb.append(", contentType=").append(contentType);
        sb.append(", fileId=").append(fileId);
        sb.append(", chatGroupId=").append(chatGroupId);
        sb.append(", recall=").append(recall);
        sb.append(", chatType=").append(chatType);
        sb.append(", plainText=").append(plainText);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}