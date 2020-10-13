package com.css.im.file.service;

import com.css.im.chat.ChatStatus;
import com.css.im.file.model.FileItem;

import java.io.InputStream;
import java.util.List;

public interface IMFileService {
    //添加文件基础信息
    boolean addFileItem(FileItem fileItem);

    //更新文件信息
    boolean updateFileItem(FileItem fileItem);

    //获取文件信息
    FileItem getFileItem(String fileId);

    //上传文件
    boolean uploadFile(InputStream inputStream, String path);

    //删除文件
    boolean delFile(String fileId, String userId);

    //删除文件表(逻辑删)
    boolean updateFileStatus(String fileId, ChatStatus.FileStatus targetStatus);

    //获取群组文件列表
    List<FileItem> getGroupFileList(String groupId);


}
