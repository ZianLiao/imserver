package com.css.im.chat.service;

import com.css.im.chat.model.ChatGroup;

import java.util.List;

/**
 * created by Charles Zhang
 *
 * @date 9/11/2020
 */
public interface IMChatGroupService {
    List<ChatGroup> getChatGroupsByUser(String userId);

    ChatGroup getChatGroupById(String chatGroupId);

    boolean addChatGroup(ChatGroup chatGroup);

    boolean updateGroup(ChatGroup chatGroup);

    //for 1.0
    List<String> getAddedUsers(ChatGroup chatGroup);

    List<String> getDelUsers(ChatGroup chatGroup);




}
