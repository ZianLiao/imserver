package com.css.im.mbg.service.impl;

import com.css.im.chat.ChatStatus;
import com.css.im.chat.model.ChatMessageSend;
import com.css.im.chat.service.IMChatService;
import com.css.im.chat.service.IMMessageEventHandler;
import com.css.im.chat.service.IMMsgService;
import com.css.im.file.model.FileItem;
import com.css.im.file.service.IMFileService;
import com.css.im.mbg.IMMessageType;
import com.css.im.mbg.model.MessagePackage;
import com.css.im.mbg.service.AbstractIMMessageService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.List;
import java.util.stream.Collectors;

/**
 * created by Charles Zhang
 *
 * @date 9/1/2020
 */
@Service
public class IMOfflineMsgService extends AbstractIMMessageService {
    @Autowired
    IMChatService imChatService;
    @Autowired
    IMMsgService imMsgService;
    @Autowired
    IMFileService imFileService;

    @Override
    public IMMessageType getIMMessageType() {
        return IMMessageType.mtOffLineMsg;
    }

    @Override
    public void handlerMessage(String uid, JSONObject messageObj, Session session) {
        logger.info("处理离线消息：" + messageObj.toString());
        List<ChatMessageSend> offLineMsgList = imMsgService.getOffLineMsg(uid);
        List<ChatMessageSend> readUnInformedMsgList = imMsgService.getReadUnInformedMsg(uid);
        if (offLineMsgList != null && offLineMsgList.size() > 0) {
            JSONArray messages = new JSONArray();
            for (ChatMessageSend messageSend : offLineMsgList) {
                //发送离线消息
                MessagePackage response = new MessagePackage();
                response.setMessageType(IMMessageType.mtOffLineMsg);
                response.setDescription(messageSend.getContent());
                //返回给客户端消息ID
                response.setParameter("msgId", messageSend.getMsgId());
                if (messageSend.getContentType().equals(ChatStatus.MessageContentType.File.status())
                        && messageSend.getChatType().equals(ChatStatus.ChatType.Chat.status().intValue())) {
                    //文件消息，且为单聊情况
                    FileItem fileItem = imFileService.getFileItem(messageSend.getFileId());
                    String description = fileItem.getMd5() + "&" + fileItem.getFileName() + "&"
                            + fileItem.getFileSize() + "&" + fileItem.getFileId();
                    response.setDescription(description);
                    response.setMessageType(IMMessageType.mtFileReceiveNotice);
                } else if (messageSend.getContentType().equals(ChatStatus.MessageContentType.Read_Burn.status())) {
                    response.setParameter("msgName", "burn");
                } else if (messageSend.getContentType().equals(ChatStatus.MessageContentType.To_Burn.status())) {
                    response.setParameter("msgName", "toBurn");
                }
                response.setSendUser(messageSend.getFromUid());
                response.setReceiveUser(uid);
                response.setParameter("type", messageSend.getChatType() + 1);
                response.setParameter("groupUuid", messageSend.getChatGroupId() == null ? ""
                        : messageSend.getChatGroupId());
                response.setParameter("createTime", messageSend.getMsgTime());
                response.setParameter("url", "");
                messages.add(JSONObject.fromObject(gson.toJson(response)));

            }
            imChatService.response(messages, uid, new IMMessageEventHandler() {
                @Override
                public void onSuccess(List<String> uids) {
                    //纯文本离线消息列表
                    List<ChatMessageSend> textOffLineMsgList = offLineMsgList.stream().
                            filter(item -> item.getContentType().equals(ChatStatus.MessageContentType.Text.status())).
                            collect(Collectors.toList());
                    imMsgService.updateMessageReceiverStatus(textOffLineMsgList, uids, ChatStatus.MessageStatus.Send);
                    //群文件消息提示列表,为普通消息(xxx上传了共享文件:xxx)
                    List<ChatMessageSend> groupFileMsgList = offLineMsgList.stream().filter(item -> item.getChatType().
                            equals(ChatStatus.ChatType.GroupChat.status().intValue())).collect(Collectors.toList());
                    imMsgService.updateMessageReceiverStatus(groupFileMsgList, uids, ChatStatus.MessageStatus.Send);
                }

                @Override
                public void onFail() {

                }
            });
            logger.info("上线获得离线消息： " + messages.toString());
        }
        //处理对方已读但是我还没有更新的消息
        //{"Code":"","Description":"","Dict":{"chatGroupId":"","fromUid":"2b3ad4abf51d43b1a01715ab724682f6","msgId":"73b8df6db25247a6abd53708cce27dfe","toUid":"87e63a6c0f85441f84456c3b31e6b3a7"},"MessageType":"mtMsgReadAlready","Operate":"","ReceiveUser":"","SendUser":""}
        if (readUnInformedMsgList != null && readUnInformedMsgList.size() > 0) {
            JSONArray messages = new JSONArray();
            for (ChatMessageSend readUnInformMsg : readUnInformedMsgList) {
                String chatGroupId = readUnInformMsg.getChatGroupId();
                MessagePackage response = new MessagePackage();
                response.setMessageType(IMMessageType.mtMsgReadAlready);
                response.setParameter("chatGroupId", chatGroupId == null ? "" : chatGroupId);
                response.setParameter("fromUid", readUnInformMsg.getFromUid());
                response.setParameter("toUid", readUnInformMsg.getToUid());
                response.setParameter("msgId", readUnInformMsg.getMsgId());
                messages.add(JSONObject.fromObject(gson.toJson(response)));
            }
            imChatService.response(messages, uid, new IMMessageEventHandler() {
                @Override
                public void onSuccess(List<String> uids) {
                    imMsgService.updateMessageReceiverStatus(readUnInformedMsgList, uids,
                            ChatStatus.MessageStatus.Read_informed);
                }

                @Override
                public void onFail() {

                }
            });
        }

    }

}
