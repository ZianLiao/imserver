package com.css.im.info.mapper;

import com.css.im.info.model.InfoFrequentConnect;
import com.css.im.info.model.InfoFrequentConnectExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InfoFrequentConnectMapper {
    long countByExample(InfoFrequentConnectExample example);

    int deleteByExample(InfoFrequentConnectExample example);

    int deleteByPrimaryKey(String infoId);

    int insert(InfoFrequentConnect record);

    int insertSelective(InfoFrequentConnect record);

    List<InfoFrequentConnect> selectByExampleWithBLOBs(InfoFrequentConnectExample example);

    List<InfoFrequentConnect> selectByExample(InfoFrequentConnectExample example);

    InfoFrequentConnect selectByPrimaryKey(String infoId);

    int updateByExampleSelective(@Param("record") InfoFrequentConnect record, @Param("example") InfoFrequentConnectExample example);

    int updateByExampleWithBLOBs(@Param("record") InfoFrequentConnect record, @Param("example") InfoFrequentConnectExample example);

    int updateByExample(@Param("record") InfoFrequentConnect record, @Param("example") InfoFrequentConnectExample example);

    int updateByPrimaryKeySelective(InfoFrequentConnect record);

    int updateByPrimaryKeyWithBLOBs(InfoFrequentConnect record);

    int updateByPrimaryKey(InfoFrequentConnect record);
}