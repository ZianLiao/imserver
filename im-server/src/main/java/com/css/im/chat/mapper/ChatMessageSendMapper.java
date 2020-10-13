package com.css.im.chat.mapper;

import com.css.im.chat.model.ChatMessageSend;
import com.css.im.chat.model.ChatMessageSendExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatMessageSendMapper {
    long countByExample(ChatMessageSendExample example);

    int deleteByExample(ChatMessageSendExample example);

    int deleteByPrimaryKey(String msgId);

    int insert(ChatMessageSend record);

    int insertSelective(ChatMessageSend record);

    List<ChatMessageSend> selectByExampleWithBLOBs(ChatMessageSendExample example);

    List<ChatMessageSend> selectByExample(ChatMessageSendExample example);

    ChatMessageSend selectByPrimaryKey(String msgId);

    int updateByExampleSelective(@Param("record") ChatMessageSend record, @Param("example") ChatMessageSendExample example);

    int updateByExampleWithBLOBs(@Param("record") ChatMessageSend record, @Param("example") ChatMessageSendExample example);

    int updateByExample(@Param("record") ChatMessageSend record, @Param("example") ChatMessageSendExample example);

    int updateByPrimaryKeySelective(ChatMessageSend record);

    int updateByPrimaryKeyWithBLOBs(ChatMessageSend record);

    int updateByPrimaryKey(ChatMessageSend record);

    List<ChatMessageSend> queryMsgSendList(@Param("groupId") String groupId, @Param("senderId") String senderId,
                                           @Param("isFile") boolean isFile, @Param("rowIndex") int rowIndex,
                                           @Param("pageSize") int pageSize);
}