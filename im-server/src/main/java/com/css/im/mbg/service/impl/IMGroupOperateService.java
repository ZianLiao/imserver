package com.css.im.mbg.service.impl;

import com.css.im.chat.ChatStatus;
import com.css.im.chat.model.ChatGroup;
import com.css.im.chat.model.ChatMessageSend;
import com.css.im.chat.service.IMChatGroupService;
import com.css.im.chat.service.IMChatService;
import com.css.im.chat.service.IMMessageEventHandler;
import com.css.im.chat.service.IMMsgService;
import com.css.im.mbg.IMMessageType;
import com.css.im.mbg.model.MessagePackage;
import com.css.im.mbg.service.AbstractIMMessageService;
import com.css.im.mbg.util.Command;
import com.css.im.mbg.util.OperateEnum;
import com.css.im.sys.model.SysUser;
import com.css.im.sys.service.SysUserService;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by Charles Zhang
 *
 * @date 9/1/2020
 */
@Service
public class IMGroupOperateService extends AbstractIMMessageService {
    private static final Gson gson = new Gson();
    @Autowired
    IMChatService imChatService;
    @Autowired
    IMChatGroupService chatGroupService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    IMMsgService imMsgService;

    @Override
    public IMMessageType getIMMessageType() {
        return IMMessageType.mtGroup;
    }

    @Override
    public void handlerMessage(String uid, JSONObject messageObj, Session session) {
        logger.info("处理群组操作消息：" + messageObj.toString());
        //get message package by gson
        MessagePackage message = gson.fromJson(messageObj.toString(), MessagePackage.class);

        OperateEnum.GroupStyle operate = OperateEnum.GroupStyle.valueOf(message.getOperate());
        if (operate == OperateEnum.GroupStyle.ADD) {
            //创建群组
            JSONObject dict = messageObj.getJSONObject("Dict");
            JSONObject groupInfoJson = dict.getJSONObject("groupInfo");
            ChatGroup chatGroup = new ChatGroup();
            chatGroup.setCreateUserId(groupInfoJson.getString("CreateUser"));
            chatGroup.setCreateUserId(uid);
            chatGroup.setChatGroupName(groupInfoJson.getString("Name"));
            chatGroup.setMembersUids((String) message.getParameter("userCodes"));
            chatGroup.setChatType((short) 1);
            MessagePackage notification = new MessagePackage();
            notification.setMessageType(IMMessageType.mtGroup);
            notification.setOperate(OperateEnum.GroupStyle.NOTICESRCREATE.toString());
            notification.setSendUser(message.getSendUser());
            try {
                if (chatGroupService.addChatGroup(chatGroup)) {
                    notification.setCode(Command.Common.SUCCESS);
                    SysUser user = sysUserService.getUserById(chatGroup.getCreateUserId());
                    notification.setDescription(user.getName() + "邀请您加入群：" + chatGroup.getChatGroupName());
                    notification.setParameter("groupUuid", chatGroup.getChatGroupId());
                    String groupInfoStr = chatGroup.getChatGroupId() + "~" + chatGroup.getChatGroupName() + "~" +
                            chatGroup.getCreateTime() + "~" + chatGroup.getCreateUserId() + "~" +
                            user.getName() + "~" + chatGroup.getMembersUids();
                    notification.setParameter("groupInfo", groupInfoStr);
                    notification.setParameter("type", "5");
                    List<String> memberIdList = Arrays.asList(chatGroup.getMembersUids().split(","));
                    logger.debug("将要通知所有入群的人：" + gson.toJson(notification));
                    ChatMessageSend messageSend = imChatService.operateGroup(chatGroup.getCreateUserId(),
                            chatGroup.getChatGroupId(), notification.getDescription(), memberIdList);
                    if (messageSend != null) {
                        imChatService.inform(JSONObject.fromObject(gson.toJson(notification)),
                                memberIdList, new IMMessageEventHandler() {
                                    @Override
                                    public void onSuccess(List<String> uids) {
                                        imMsgService.updateMessageReceiverStatus(messageSend.getMsgId(),
                                                messageSend.getFromUid(), uids, ChatStatus.MessageStatus.Send);
                                    }

                                    @Override
                                    public void onFail() {

                                    }
                                });
                    }
                } else {
                    notification.setCode(Command.Common.ERROR);
                    notification.setDescription("群创建失败！");
                    JSONArray messageArr = new JSONArray();
                    messageArr.add(JSONObject.fromObject(gson.toJson(notification)));
                    sessionWrite(session, messageArr.toString());
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        } else if (operate == OperateEnum.GroupStyle.USEREXIT) {
            String exitUserId = message.getSendUser();
            String groupId = (String) message.getParameter("groupId");
            ChatGroup chatGroup = chatGroupService.getChatGroupById(groupId);
            String oldMemberIds = chatGroup.getMembersUids();
            String newMemberIds = updateExitUserCodeLongStr(oldMemberIds, exitUserId);
            if ("".equals(newMemberIds)) {
                chatGroup.setStatus((short) -1);
            }
            chatGroup.setMembersUids(newMemberIds);

            MessagePackage notice = new MessagePackage();
            notice.setMessageType(IMMessageType.mtGroup);
            notice.setOperate(OperateEnum.GroupStyle.USEREXIT.toString());
            notice.setSendUser(message.getSendUser());
            try {
                if (chatGroupService.updateGroup(chatGroup)) {
                    notice.setCode(Command.Common.SUCCESS);
                    notice.setDescription("退出群成功！");
                    notice.setParameter("groupUuid", groupId);
                    SysUser user = sysUserService.getUserById(chatGroup.getCreateUserId());
                    String groupInfoStr = chatGroup.getChatGroupId() + "~" + chatGroup.getChatGroupName() + "~" +
                            chatGroup.getCreateTime() + "~" + chatGroup.getCreateUserId() + "~" +
                            user.getName() + "~" + chatGroup.getMembersUids();
                    notice.setParameter("groupInfo", groupInfoStr);
                    logger.debug("用户退出消息体： " + gson.toJson(notice));
                    imChatService.inform(JSONObject.fromObject(gson.toJson(notice)), oldMemberIds.split(","));
                } else {
                    notice.setCode(Command.Common.ERROR);
                    notice.setDescription("退出群失败！");
                    JSONArray messageArr = new JSONArray();
                    messageArr.add(JSONObject.fromObject(gson.toJson(notice)));
                    sessionWrite(session, messageArr.toString());
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
            //添加讨论组修改功能 2016.1.21
        } else if (operate == OperateEnum.GroupStyle.UPDATE) {
            String groupId = (String) message.getParameter("groupId");
            String groupName = (String) message.getParameter("groupName");
            String createUserName = (String) message.getParameter("CreateUserName");

            ChatGroup chatGroup = chatGroupService.getChatGroupById(groupId);
            String oldGroupName = chatGroup.getChatGroupName();
            chatGroup.setMembersUids((String) message.getParameter("userCodes"));
            chatGroup.setChatGroupName(groupName);
            //获取被删除的人员
            List<String> delUsers = chatGroupService.getDelUsers(chatGroup);
            //获取新增人员
            List<String> addedUsers = chatGroupService.getAddedUsers(chatGroup);
            //群最新的成员
            List<String> newGroupMemberIds = Arrays.asList(chatGroup.getMembersUids().split(","));

            try {
                if (chatGroupService.updateGroup(chatGroup)) {
                    SysUser user = sysUserService.getUserById(chatGroup.getCreateUserId());
                    String groupInfoStr = chatGroup.getChatGroupId() + "~" + chatGroup.getChatGroupName() + "~" +
                            chatGroup.getCreateTime() + "~" + chatGroup.getCreateUserId() + "~" +
                            user.getName() + "~" + chatGroup.getMembersUids();
                    MessagePackage updateNotice = new MessagePackage();
                    updateNotice.setMessageType(IMMessageType.mtGroup);
                    updateNotice.setOperate(OperateEnum.GroupStyle.UPDATE.toString());
                    updateNotice.setSendUser(message.getSendUser());
                    updateNotice.setCode(Command.Common.SUCCESS);
                    updateNotice.setParameter("groupInfo", groupInfoStr);
                    updateNotice.setParameter("type", "5");
                    updateNotice.setParameter("groupUuid", groupId);
                    //处理群新增人员start
                    if (addedUsers != null && addedUsers.size() > 0) {
                        updateNotice.setDescription(createUserName + "邀请您加入群：" + groupName);
                        //通知新入群的人
                        ChatMessageSend message2AddUsers = imChatService.operateGroup(chatGroup.getCreateUserId(),
                                groupId, updateNotice.getDescription(), addedUsers);
                        if (message2AddUsers != null) {
                            imChatService.inform(JSONObject.fromObject(gson.toJson(updateNotice)),
                                    addedUsers, new IMMessageEventHandler() {
                                        @Override
                                        public void onSuccess(List<String> uids) {
                                            imMsgService.updateMessageReceiverStatus(message2AddUsers.getMsgId(),
                                                    message2AddUsers.getFromUid(), uids, ChatStatus.MessageStatus.Send);
                                        }

                                        @Override
                                        public void onFail() {
                                        }
                                    });
                        }
                    }
                    //群名称发生改变
                    if (!oldGroupName.equals(groupName)) {
                        updateNotice.setDescription(createUserName + "将群" + oldGroupName + "修改为: " + groupName);
                        ChatMessageSend msg2AllGroupMember =
                                imChatService.operateGroup(chatGroup.getCreateUserId(), groupId,
                                        updateNotice.getDescription(), newGroupMemberIds);
                        if (msg2AllGroupMember != null) {
                            imChatService.inform(JSONObject.fromObject(gson.toJson(updateNotice)),
                                    newGroupMemberIds, new IMMessageEventHandler() {
                                        @Override
                                        public void onSuccess(List<String> uids) {
                                            imMsgService.updateMessageReceiverStatus(msg2AllGroupMember.getMsgId(),
                                                    msg2AllGroupMember.getFromUid(), uids, ChatStatus.MessageStatus.Send);
                                        }

                                        @Override
                                        public void onFail() {
                                        }
                                    });
                        }
                    }
                    //处理群组删除人员
                    if (delUsers != null && delUsers.size() > 0) {
                        updateNotice.setOperate(OperateEnum.GroupStyle.NOTICESRDELETE.toString());
                        updateNotice.setDescription(createUserName + "已将您从：" + groupName + " 中移除");
                        //通知被删除的人
                        ChatMessageSend message2DelUsers = imChatService.operateGroup(chatGroup.getCreateUserId(),
                                groupId, updateNotice.getDescription(), delUsers);
                        if (message2DelUsers != null) {
                            imChatService.inform(JSONObject.fromObject(gson.toJson(updateNotice)),
                                    delUsers, new IMMessageEventHandler() {
                                        @Override
                                        public void onSuccess(List<String> uids) {
                                            imMsgService.updateMessageReceiverStatus(message2DelUsers.getMsgId(),
                                                    message2DelUsers.getFromUid(), uids, ChatStatus.MessageStatus.Send);
                                        }

                                        @Override
                                        public void onFail() {
                                        }
                                    });
                        }

                    }
                } else {
                    MessagePackage failNotice = new MessagePackage();
                    failNotice.setMessageType(IMMessageType.mtGroup);
                    failNotice.setOperate(OperateEnum.GroupStyle.UPDATE.toString());
                    failNotice.setSendUser(message.getSendUser());
                    failNotice.setCode(Command.Common.ERROR);
                    failNotice.setDescription("群修改失败！");
                    JSONArray messageArr = new JSONArray();
                    messageArr.add(JSONObject.fromObject(gson.toJson(failNotice)));
                    sessionWrite(session, messageArr.toString());
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        } else if (operate == OperateEnum.GroupStyle.DELETE) {
            String groupId = (String) message.getParameter("groupId");
            String groupName = (String) message.getParameter("groupName");
            ChatGroup chatGroup = chatGroupService.getChatGroupById(groupId);
            SysUser user = sysUserService.getUserById(chatGroup.getCreateUserId());
            String groupInfoStr = chatGroup.getChatGroupId() + "~" + chatGroup.getChatGroupName() + "~" +
                    chatGroup.getCreateTime() + "~" + chatGroup.getCreateUserId() + "~" +
                    user.getName() + "~" + chatGroup.getMembersUids();
            chatGroup.setStatus((short) -1);
            MessagePackage notice = new MessagePackage();
            notice.setMessageType(IMMessageType.mtGroup);
            notice.setOperate(OperateEnum.GroupStyle.DELETE.toString());
            notice.setSendUser(message.getSendUser());
            try {
                if (chatGroupService.updateGroup(chatGroup)) {
                    notice.setCode(Command.Common.SUCCESS);
                    notice.setDescription(groupName + " 群已被删除！");
                    notice.setParameter("groupUuid", groupId);
                    notice.setParameter("groupInfo", groupInfoStr);
                    List<String> groupMemberIdList = Arrays.asList(chatGroup.getMembersUids().split(","));
                    ChatMessageSend messageSend = imChatService.operateGroup(chatGroup.getCreateUserId(),
                            groupId, notice.getDescription(), groupMemberIdList);
                    if (messageSend != null) {
                        imChatService.inform(JSONObject.fromObject(gson.toJson(notice)),
                                groupMemberIdList, new IMMessageEventHandler() {
                                    @Override
                                    public void onSuccess(List<String> uids) {
                                        imMsgService.updateMessageReceiverStatus(messageSend.getMsgId(),
                                                messageSend.getFromUid(), uids, ChatStatus.MessageStatus.Send);
                                    }

                                    @Override
                                    public void onFail() {
                                    }
                                });
                        logger.info("发送删除群消息体: " + gson.toJson(notice));
                    }
                } else {
                    notice.setCode(Command.Common.ERROR);
                    notice.setDescription("群删除失败！");
                    JSONArray messageArr = new JSONArray();
                    messageArr.add(JSONObject.fromObject(gson.toJson(notice)));
                    sessionWrite(session, messageArr.toString());
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private static String updateExitUserCodeLongStr(String groupMemberIds, String exitUserId) {
        List<String> memberUIds = new ArrayList<>(Arrays.asList(groupMemberIds.split(",")));
        boolean flag = memberUIds.remove(exitUserId);
        if (!flag) {
            throw new RuntimeException("要删除的人员不在此群内！");
        }
        if (memberUIds.size() < 1) {
            return "";
        } else {
            String[] array = memberUIds.toArray(new String[0]);
            return String.join(",", array);
        }
    }
}
