package com.css.im.chat.service;


import com.css.im.chat.ChatStatus;
import com.css.im.chat.model.ChatMessageSend;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/***
 * 提供聊天服务
 */
public interface IMChatService {

    /***
     * 响应消息
     * 响应用户发来的消息
     * @return
     */
    boolean response(JSONObject message, String uid, IMMessageEventHandler handler);

    /**
     * 通知，
     * 上线、下线、进群、退群
     */
    List<String> inform(JSONObject message, List<String> targetUids, IMMessageEventHandler handler);

    List<String> inform(JSONObject message, String... uids);

    /***
     * 响应消息
     * 响应用户发来的消息
     * @return
     */
    boolean response(JSONArray messages, String uid, IMMessageEventHandler handler);

    /**
     * 通知，
     * 上线、下线、进群、退群
     */
    List<String> inform(JSONArray messages, List<String> targetUids, IMMessageEventHandler handler);


    /**
     * 单聊
     *
     * @param fromUid     消息发送者
     * @param toUid       消息接收者
     * @param content     消息内容
     * @param contentType 消息类型
     * @param fileId      文件id
     */
    ChatMessageSend chat(String fromUid, String toUid, String content, String plainText,
                         ChatStatus.MessageContentType contentType, String fileId);

    /**
     * 群聊
     *
     * @param fromUid
     * @param toGid
     * @param content
     * @param contentType
     * @param fileId
     */
    ChatMessageSend chatGroup(String fromUid, String toGid, String content, String plainText,
                              ChatStatus.MessageContentType contentType, String fileId);

    /**
     * 群操作相关
     *
     * @param fromUid
     * @param toGid
     * @param content
     */
    ChatMessageSend operateGroup(String fromUid, String toGid, String content, List<String> receivers);

    /**
     * 广播消息
     *
     * @param fromUid
     * @param toUids
     * @param content
     * @param contentType
     * @param fileId
     */
    ChatMessageSend sysBroadcast(String fromUid, List<String> toUids, String content,
                                 ChatStatus.MessageContentType contentType, String fileId);

    ChatMessageSend OANotify(String fromUid, List<String> toUids, String content);
}
