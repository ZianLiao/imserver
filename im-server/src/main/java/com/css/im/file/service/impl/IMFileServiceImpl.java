package com.css.im.file.service.impl;

import com.css.common.BaseService;
import com.css.im.chat.ChatStatus;
import com.css.im.config.FileConfig;
import com.css.im.file.mapper.FileItemMapper;
import com.css.im.file.model.FileItem;
import com.css.im.file.model.FileItemExample;
import com.css.im.file.service.IMFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * 聊天文件操作服务类
 * Create by wx on 2020-09-07
 */
@Service
public class IMFileServiceImpl extends BaseService implements IMFileService {
    @Autowired
    FileItemMapper fileItemMapper;
    @Autowired
    FileConfig fileConfig;


    @Transactional
    @Override
    public boolean addFileItem(FileItem fileItem) {
        try {
            if (fileItem != null) {
                fileItemMapper.insert(fileItem);
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Transactional
    @Override
    public boolean updateFileItem(FileItem fileItem) {
        try {
            if (fileItem != null) {
                int i = fileItemMapper.updateByPrimaryKeySelective(fileItem);
                return i > 0;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delFile(String fileId, String userId) {
        if (StringUtils.isNotBlank(fileId) && StringUtils.isNotBlank(userId)) {
            FileItem fileItem = getFileItem(fileId);
            if (fileItem == null) {
                return false;
            }
            if (!fileItem.getCreateUser().equals(userId)) {
                logger.error("****** 用户权限不足! 文件删除失败******");
                return false;
            }
            String path = fileItem.getPath();
            File file = new File(path);
            if (file.exists()) {
                file.delete();
                logger.info("******文件: " + fileId + " 删除成功!******");
                return true;
            } else {
                logger.error("******文件路径: " + path + "存在错误 删除失败!******");
                return false;
            }
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean updateFileStatus(String fileId, ChatStatus.FileStatus targetStatus) {
        try {
            FileItem fileItem = getFileItem(fileId);
            fileItem.setStatus(targetStatus.status());
            return updateFileItem(fileItem);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public FileItem getFileItem(String fileId) {
        FileItemExample example = new FileItemExample();
        example.createCriteria().andFileIdEqualTo(fileId).andStatusEqualTo(ChatStatus.FileStatus.Normal.status());
        List<FileItem> fileItems = fileItemMapper.selectByExampleWithBLOBs(example);
        return fileItems.get(0);
    }

    @Override
    public boolean uploadFile(InputStream in, String path) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            byte[] b = new byte[1024 * 10];
            int length;
            while ((length = in.read(b, 0, b.length)) != -1) {
                out.write(b, 0, length);
            }
            out.flush();
            out.close();
            in.close();
            return true;
        } catch (Exception e) {
            logger.error("上传文件失败:" + e.getMessage());
            return false;
        }
    }

    @Override
    public List<FileItem> getGroupFileList(String groupId) {
        FileItemExample example = new FileItemExample();
        example.createCriteria().andChatGroupIdEqualTo(groupId).andStatusEqualTo(ChatStatus.FileStatus.Normal.status());
        example.setOrderByClause("create_time");
        List<FileItem> list = fileItemMapper.selectByExample(example);
        return list;
    }

}
