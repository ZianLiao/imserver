package com.css.im.chat.mapper;

import com.css.im.chat.model.ChatMessageReceiver;
import com.css.im.chat.model.ChatMessageReceiverExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface ChatMessageReceiverMapper {
    long countByExample(ChatMessageReceiverExample example);

    int deleteByExample(ChatMessageReceiverExample example);

    int deleteByPrimaryKey(String id);

    int insert(ChatMessageReceiver record);

    int insertSelective(ChatMessageReceiver record);

    List<ChatMessageReceiver> selectByExample(ChatMessageReceiverExample example);

    ChatMessageReceiver selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ChatMessageReceiver record, @Param("example") ChatMessageReceiverExample example);

    int updateByExample(@Param("record") ChatMessageReceiver record, @Param("example") ChatMessageReceiverExample example);

    int updateByPrimaryKeySelective(ChatMessageReceiver record);

    int updateByPrimaryKey(ChatMessageReceiver record);

    int updateStatusByFileId(@Param("fileId") String fileId, @Param("status") Short status, @Param("receiverId") String receiverId);

}