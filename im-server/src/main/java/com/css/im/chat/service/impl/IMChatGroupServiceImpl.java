package com.css.im.chat.service.impl;

import com.css.common.BaseService;
import com.css.im.chat.mapper.ChatGroupMapper;
import com.css.im.chat.model.ChatGroup;
import com.css.im.chat.service.IMChatGroupService;
import com.css.im.mbg.exceptions.GroupException;
import com.css.utils.DateTimeUtils;
import com.css.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * created by Charles Zhang
 *
 * @date 9/11/2020
 */
@Service
public class IMChatGroupServiceImpl extends BaseService implements IMChatGroupService {
    @Autowired
    ChatGroupMapper chatGroupMapper;

    @Override
    public List<ChatGroup> getChatGroupsByUser(String userId) {
        return chatGroupMapper.queryByMemberId(userId);
    }

    @Override
    public ChatGroup getChatGroupById(String chatGroupId) {
        return chatGroupMapper.selectByPrimaryKey(chatGroupId);
    }

    @Override
    public boolean addChatGroup(ChatGroup chatGroup) {
        if (chatGroup != null) {
            chatGroup.setChatGroupId(UUIDUtils.generateUUID());
            chatGroup.setCreateTime(DateTimeUtils.getTodayDateTime());
            chatGroup.setStatus((short) 0);
            int affected = chatGroupMapper.insert(chatGroup);
            return affected > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateGroup(ChatGroup chatGroup) {
        int affected = chatGroupMapper.updateByPrimaryKeySelective(chatGroup);
        return affected > 0;
    }

    @Override
    public List<String> getAddedUsers(ChatGroup chatGroup) {
        List<String> userList = new ArrayList<>();
        //new user code list
        String[] userCodeStrArray = chatGroup.getMembersUids().split(",");

        ChatGroup oldChatGroup = chatGroupMapper.selectByPrimaryKey(chatGroup.getChatGroupId());
        if (oldChatGroup != null && oldChatGroup.getMembersUids() != null) {
            //old user code list
            String[] oldUserCodeArray = oldChatGroup.getMembersUids().split(",");
            Set<String> oldUserCodeSet = new HashSet<>(Arrays.asList(oldUserCodeArray));
            for (String s : userCodeStrArray) {
                if (!oldUserCodeSet.contains(s)) {
                    userList.add(s);
                }
            }
        } else {
            throw new GroupException("用户列表为空！");
        }
        return userList;
    }

    @Override
    public List<String> getDelUsers(ChatGroup chatGroup) {
        List<String> userList = new ArrayList<>();
        //new user code list
        String[] userCodeStrArray = chatGroup.getMembersUids().split(",");
        Set<String> newUserCodeSet = new HashSet<>(Arrays.asList(userCodeStrArray));
        ChatGroup oldChatGroup = chatGroupMapper.selectByPrimaryKey(chatGroup.getChatGroupId());
        if (oldChatGroup != null && oldChatGroup.getMembersUids() != null) {
            //old user code list
            String[] oldUserCodeArray = oldChatGroup.getMembersUids().split(",");
            for (String s : oldUserCodeArray) {
                if (!newUserCodeSet.contains(s)) {
                    userList.add(s);
                }
            }
        } else {
            throw new GroupException("用户列表为空！");
        }
        return userList;
    }

}
