package com.css.im.file.mapper;

import com.css.im.file.model.FileItem;
import com.css.im.file.model.FileItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileItemMapper {
    long countByExample(FileItemExample example);

    int deleteByExample(FileItemExample example);

    int deleteByPrimaryKey(String fileId);

    int insert(FileItem record);

    int insertSelective(FileItem record);

    List<FileItem> selectByExampleWithBLOBs(FileItemExample example);

    List<FileItem> selectByExample(FileItemExample example);

    FileItem selectByPrimaryKey(String fileId);

    int updateByExampleSelective(@Param("record") FileItem record, @Param("example") FileItemExample example);

    int updateByExampleWithBLOBs(@Param("record") FileItem record, @Param("example") FileItemExample example);

    int updateByExample(@Param("record") FileItem record, @Param("example") FileItemExample example);

    int updateByPrimaryKeySelective(FileItem record);

    int updateByPrimaryKeyWithBLOBs(FileItem record);

    int updateByPrimaryKey(FileItem record);
}