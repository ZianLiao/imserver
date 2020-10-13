package com.css.im.chat.model;

import java.io.Serializable;

public class ChatGroup implements Serializable {
    private String chatGroupId;

    private String chatGroupName;

    private String createTime;

    /**
     * 0： 单聊，1：群聊
     *
     * @mbg.generated
     */
    private Short chatType;

    private String createUserId;

    /**
     * 聊天组状态 0：正常,-1:删除，1：不可发消息
     *
     * @mbg.generated
     */
    private Short status;

    private String membersUids;

    private String createUserName;

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    private static final long serialVersionUID = 1L;

    public String getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(String chatGroupId) {
        this.chatGroupId = chatGroupId == null ? null : chatGroupId.trim();
    }

    public String getChatGroupName() {
        return chatGroupName;
    }

    public void setChatGroupName(String chatGroupName) {
        this.chatGroupName = chatGroupName == null ? null : chatGroupName.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public Short getChatType() {
        return chatType;
    }

    public void setChatType(Short chatType) {
        this.chatType = chatType;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getMembersUids() {
        return membersUids;
    }

    public void setMembersUids(String membersUids) {
        this.membersUids = membersUids == null ? null : membersUids.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", chatGroupId=").append(chatGroupId);
        sb.append(", chatGroupName=").append(chatGroupName);
        sb.append(", createTime=").append(createTime);
        sb.append(", chatType=").append(chatType);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", status=").append(status);
        sb.append(", membersUids=").append(membersUids);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}