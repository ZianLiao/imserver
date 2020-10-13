package com.css.im.chat.service;

import com.css.im.chat.ChatStatus;
import com.css.im.chat.model.ChatMessageReceiver;
import com.css.im.chat.model.ChatMessageSend;
import com.css.im.chat.model.MsgReadInfo;

import java.io.IOException;
import java.util.List;

public interface IMMsgService {

    /***
     * 广播用户上线
     */
    void informOnline(String uid);

    /***
     * 广播用户下线功能
     * @param uid
     */
    void informOffline(String uid);

    /***
     * 广播用户退群
     * @param uid
     * @param gid
     */
    void informLeaveGroup(String uid, String gid);

    /***
     * 被踢出群消息
     * @param uid
     * @param gid
     */
    void informKiffGroup(String uid, String gid);

    /***
     * 进群通知
     * @param uid
     * @param gid
     */
    void informEnterGroup(String uid, String gid);

    /***
     *  发送用户uid的离线消息
     */
    List<ChatMessageSend> getOffLineMsg(String uid);

    /***
     *
     */
    boolean updateMessageReceiverStatus(String msgId, String fromUid, List<String> toUids, ChatStatus.MessageStatus targetStatus);

    /**
     * 针对于一对一消息已读未读
     *
     * @param msgId
     * @param fromUid
     * @param toUid
     * @param originStatus
     * @param targetStatus
     * @return
     */
    boolean updateMessageReceiverStatus(String msgId, String fromUid, String toUid, ChatStatus.MessageStatus originStatus, ChatStatus.MessageStatus targetStatus);


    boolean updateMessageReceiverStatus(List<ChatMessageSend> messageSends, List<String> toUids, ChatStatus.MessageStatus targetStatus);

    boolean updateMessageReceiverStatus(String senderId, String receiverId, ChatStatus.MessageStatus targetStatus);

    boolean updateMsgStatusByGroupId(String groupId, ChatStatus.MessageStatus targetStatus);

    boolean updateMessageReceiverStatus(String fileId, ChatStatus.MessageStatus targetStatus, String receiverId);

    boolean updateMessageSendContentType(String msgId, ChatStatus.MessageContentType targetStatus);

    boolean updateMessageReceiverByMsgId(ChatMessageReceiver chatMessageReceiver);

    List<ChatMessageSend> getFileOffLineMsg(String uid);

    void cacheMessage(ChatMessageSend messageSend);

    String readCacheMsg(String msgId) throws IOException;

    List<ChatMessageSend> getReadUnInformedMsg(String uid);

    List<MsgReadInfo> getReadUserList(String groupId, String senderId, boolean isFile);
}
