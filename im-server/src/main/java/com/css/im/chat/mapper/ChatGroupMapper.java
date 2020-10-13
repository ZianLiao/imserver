package com.css.im.chat.mapper;

import com.css.im.chat.model.ChatGroup;
import com.css.im.chat.model.ChatGroupExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatGroupMapper {
    long countByExample(ChatGroupExample example);

    int deleteByExample(ChatGroupExample example);

    int deleteByPrimaryKey(String chatGroupId);

    int insert(ChatGroup record);

    int insertSelective(ChatGroup record);

    List<ChatGroup> selectByExampleWithBLOBs(ChatGroupExample example);

    List<ChatGroup> selectByExample(ChatGroupExample example);

    ChatGroup selectByPrimaryKey(String chatGroupId);

    int updateByExampleSelective(@Param("record") ChatGroup record, @Param("example") ChatGroupExample example);

    int updateByExampleWithBLOBs(@Param("record") ChatGroup record, @Param("example") ChatGroupExample example);

    int updateByExample(@Param("record") ChatGroup record, @Param("example") ChatGroupExample example);

    int updateByPrimaryKeySelective(ChatGroup record);

    int updateByPrimaryKeyWithBLOBs(ChatGroup record);

    int updateByPrimaryKey(ChatGroup record);

    List<ChatGroup> queryByMemberId(String userId);
}