package com.css.im.websocket.mapper;

import com.css.im.websocket.model.WsServer;
import com.css.im.websocket.model.WsServerExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WsServerMapper {
    long countByExample(WsServerExample example);

    int deleteByExample(WsServerExample example);

    int deleteByPrimaryKey(String serverId);

    int insert(WsServer record);

    int insertSelective(WsServer record);

    List<WsServer> selectByExampleWithBLOBs(WsServerExample example);

    List<WsServer> selectByExample(WsServerExample example);

    WsServer selectByPrimaryKey(String serverId);

    int updateByExampleSelective(@Param("record") WsServer record, @Param("example") WsServerExample example);

    int updateByExampleWithBLOBs(@Param("record") WsServer record, @Param("example") WsServerExample example);

    int updateByExample(@Param("record") WsServer record, @Param("example") WsServerExample example);

    int updateByPrimaryKeySelective(WsServer record);

    int updateByPrimaryKeyWithBLOBs(WsServer record);

    int updateByPrimaryKey(WsServer record);
}