package com.css.im.file.web;

import com.css.common.BaseController;
import com.css.common.ReqResult;
import com.css.im.auth.SecurityFacade;
import com.css.im.cache.service.IMSessionService;
import com.css.im.chat.ChatStatus;
import com.css.im.chat.model.ChatGroup;
import com.css.im.chat.model.ChatMessageSend;
import com.css.im.chat.service.IMChatGroupService;
import com.css.im.chat.service.IMChatService;
import com.css.im.chat.service.IMMsgService;
import com.css.im.config.FileConfig;
import com.css.im.file.model.FileItem;
import com.css.im.file.service.IMFileService;
import com.css.im.mbg.IMMessageType;
import com.css.im.mbg.model.MessagePackage;
import com.css.im.mbg.util.Command;
import com.css.utils.DateTimeUtils;
import com.css.utils.UUIDUtils;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 提供文件操作接口
 * Create by wx on 2020-09-03
 */
@Api(tags = "文件操作接口")
@RestController
@RequestMapping("/im/api/file")
public class IMFileController extends BaseController {


    @Autowired
    IMFileService imFileService;

    @Autowired
    FileConfig fileConfig;

    @Autowired
    IMChatService imChatService;
    @Autowired
    IMSessionService iMSessionService;
    @Autowired
    IMChatGroupService imChatGroupService;
    @Autowired
    IMMsgService imMsgService;

    /****
     * 个人上传文件接口
     * @param request
     * @return
     */
    @ApiOperation(value = "个人上传文件接口")
    @RequestMapping(value = "/single/upload", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ReqResult singleUpload(MultipartHttpServletRequest request) {
        String receiveUser = get(request, "receiveUser");
        String userId = SecurityFacade.getCurUserId();
        JSONObject fileJsons = new JSONObject();
        JSONArray successFile = new JSONArray();
        JSONArray failedFile = new JSONArray();
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            String fileNameKey = fileNames.next();
            MultipartFile multipartFile = request.getFile(fileNameKey);
            if (multipartFile != null && StringUtils.isNotBlank(multipartFile.getOriginalFilename().trim())) {
                String fileId = UUIDUtils.generateUUID();
                String fileName = multipartFile.getOriginalFilename();
                String type = fileName.substring(fileName.lastIndexOf(".") + 1);
                String path = fileConfig.getStorage() + "/" + DateTimeUtils.getTodayDate() + "/" + fileId + "." + type;
                String url = fileConfig.getUrl() + "?fileId=" + fileId;
                Long size = multipartFile.getSize();
                JSONObject fileJson = new JSONObject();
                try {
                    //上传文件
                    File file = new File(path);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    multipartFile.transferTo(file);
                    //向fileItem表中添加记录
                    String md5 = DigestUtils.md5Hex(new FileInputStream(path));
                    FileItem fileItem = new FileItem();
                    fileItem.setFileId(fileId);
                    fileItem.setFileName(fileName);
                    fileItem.setFileType(type);
                    fileItem.setFileSize(Math.toIntExact(size));
                    fileItem.setCreateUser(userId);
                    fileItem.setReceiveUser(receiveUser);//个人
                    fileItem.setPath(path);
                    fileItem.setUrl(url);
                    fileItem.setMd5(md5);
                    fileItem.setStatus(ChatStatus.FileStatus.Normal.status());
                    fileItem.setCreateTime(DateTimeUtils.getTodayDateTime());
                    imFileService.addFileItem(fileItem);
                    //发送消息
                    MessagePackage notice = new MessagePackage();
                    notice.setMessageType(IMMessageType.mtFileReceiveNotice);
                    notice.setSendUser(userId);
                    notice.setReceiveUser(receiveUser);
                    String description = fileItem.getMd5() + "&" + fileItem.getFileName() + "&" + fileItem.getFileSize() + "&" + fileItem.getFileId();
                    notice.setDescription(description);
                    ChatMessageSend messageSend = imChatService.chat(userId, receiveUser, null,
                            null, ChatStatus.MessageContentType.File, fileId);
                    if (messageSend != null) {
                        imChatService.response(JSONObject.fromObject(new Gson().toJson(notice)), receiveUser, null);
                    }
                    //返回上传成功的文件
                    fileJson.put("fileId", fileId);
                    fileJson.put("fileName", fileName);
                    fileJson.put("filePath", path);
                    successFile.add(fileJson);
                } catch (Exception e) {
                    //上传失败的文件id和文件名
                    fileJson.put("fileId", fileId);
                    fileJson.put("fileName", fileName);
                    failedFile.add(fileJson);
                    logger.error(e.getMessage());
                }
                fileJsons.put("successFile", successFile);
                fileJsons.put("failedFile", failedFile);
            }
        }
        //调xxapi发送：文件消息(文件名就是文件ID)FileMsg2APP.SendFileMsg(msg, appUuid);
        //-------------------
        logger.info("个人上传文件：" + fileJsons);
        return ReqResult.success(fileJsons);
    }

    /****
     * 群组上传文件接口
     * @param request
     * @return
     */
    @ApiOperation(value = "群组上传文件接口")
    @RequestMapping(value = "/group/upload", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ReqResult groupUpload(MultipartHttpServletRequest request) {
        String chatGroupId = get(request, "chatGroupId");
        String info = get(request, "info");
        String userId = SecurityFacade.getCurUserId();
        JSONObject fileJsons = new JSONObject();
        JSONArray successFile = new JSONArray();
        JSONArray failedFile = new JSONArray();
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            String fileNameKey = fileNames.next();
            MultipartFile multipartFile = request.getFile(fileNameKey);
            if (multipartFile != null && StringUtils.isNotBlank(multipartFile.getOriginalFilename().trim())) {
                String fileId = UUIDUtils.generateUUID();
                String fileName = multipartFile.getOriginalFilename();
                String type = fileName.substring(fileName.lastIndexOf(".") + 1);
                String path = fileConfig.getStorage() + "/" + DateTimeUtils.getTodayDate() + "/" + fileId + "." + type;
                String url = fileConfig.getUrl() + "?fileId=" + fileId;
                Long size = multipartFile.getSize();
                JSONObject fileJson = new JSONObject();
                try {
                    //上传文件
                    File file = new File(path);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    multipartFile.transferTo(file);
                    //向fileItem表中添加记录
                    String md5 = DigestUtils.md5Hex(new FileInputStream(path));
                    FileItem fileItem = new FileItem();
                    fileItem.setFileId(fileId);
                    fileItem.setFileName(fileName);
                    fileItem.setFileType(type);
                    fileItem.setFileSize(Math.toIntExact(size));
                    fileItem.setCreateUser(userId);
                    fileItem.setChatGroupId(chatGroupId);//个人
                    fileItem.setPath(path);
                    fileItem.setUrl(url);
                    fileItem.setMd5(md5);
                    fileItem.setStatus(ChatStatus.FileStatus.Normal.status());
                    fileItem.setCreateTime(DateTimeUtils.getTodayDateTime());
                    imFileService.addFileItem(fileItem);
                    //返回上传成功的文件
                    fileJson.put("fileId", fileId);
                    fileJson.put("fileName", fileName);
                    fileJson.put("filePath", path);
                    successFile.add(fileJson);
                    //定义消息体
                    MessagePackage notice = new MessagePackage();
                    notice.setMessageType(IMMessageType.mtGroupMsg);
                    notice.setSendUser(userId);
                    notice.setParameter("msg", info);
                    notice.setParameter("groupUuid", chatGroupId);
                    String description = fileItem.getMd5() + "&" + fileItem.getFileName() + "&" + fileItem.getFileSize() + "&" + fileItem.getFileId();
                    notice.setDescription(description);
                    notice.setCode(Command.Common.SUCCESS);
                    //存消息体
                    ChatMessageSend messageSend = imChatService.chatGroup(userId, chatGroupId, info,
                            null, ChatStatus.MessageContentType.File, fileId);
                    if (messageSend != null) {
                        ChatGroup chatGroup = imChatGroupService.getChatGroupById(chatGroupId);
                        //给群成员发消息
                        List<String> members = Arrays.stream(chatGroup.getMembersUids().split(",")).
                                filter(member -> !member.equals(userId)).collect(Collectors.toList());
//                        List<String> members = Arrays.asList(chatGroup.getMembersUids().replace(userId,"").split(","));
                        imChatService.inform(JSONObject.fromObject(new Gson().toJson(notice)), members, null);
                    }
                } catch (Exception e) {
                    //上传失败的文件id和文件名
                    fileJson.put("fileId", fileId);
                    fileJson.put("fileName", fileName);
                    failedFile.add(fileJson);
                    logger.error(e.getMessage());
                }
                fileJsons.put("successFile", successFile);
                fileJsons.put("failedFile", failedFile);
            }
        }
        //调xxapi发送：固定群共享文件通知GroupShareFile2APP.SendFileMsg(msg, appUuid, fileSYZMD5);
        //================
        logger.info("上传群组共享文件：" + fileJsons);
        return ReqResult.success(fileJsons);

    }


    @ApiOperation(value = "下载文件接口")
    @GetMapping("/download/{fileId}")
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileId) {
        OutputStream stream = null;
        String type = get(request, "type");
        FileItem fileItem = imFileService.getFileItem(fileId);
        byte[] data = null;
        try {
            if (null != fileItem && StringUtils.isNotBlank(fileItem.getPath())) {
                if (new File(fileItem.getPath()).exists()) {
                    data = FileUtils.readFileToByteArray(FileUtils.getFile(fileItem.getPath()));
                } else {
                    //实体文件不存在，但文件表和离线表可能有记录，删除该记录下次不再重复提醒
                    imFileService.updateFileStatus(fileId, ChatStatus.FileStatus.Delete);
                }
            }
            if (StringUtils.isNoneBlank(type)) {
                response.addHeader("Content-disposition", " attachment; filename=" + fileItem.getFileName());
            }
            response.setHeader("fileSize", String.valueOf(data == null ? 0 : data.length));
            stream = response.getOutputStream();
            stream.write(data);
            stream.flush();
            stream.close();
            stream = null;
            //如果是个人上传文件接收后（可能）删除,群组共享文件就不删除文件信息
            //更新日志表：“发送个人文件-接收成功”、“下载群组文件”
            if (StringUtils.isNotBlank(fileItem.getReceiveUser())) {
                //个人文件消息
                deleteFile(fileId, fileItem.getCreateUser(), ChatStatus.FileStatus.Accept);
                logger.info(fileItem.getFileName() + "文件传输完成！路径:" + fileItem.getPath());
                //文件消息发送成功
                imMsgService.updateMessageReceiverStatus(fileId, ChatStatus.MessageStatus.Send, null);
            } else {
                //群组文件消息
                String receiverId = SecurityFacade.getCurUserId();
                imMsgService.updateMessageReceiverStatus(fileId, ChatStatus.MessageStatus.Downloaded, receiverId);
                logger.info("下载群组文件：" + fileItem.getFileName() + "成功！路径:" + fileItem.getPath());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (null != stream) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    /****
     * 删除群组文件
     * @return
     */
    @ApiOperation(value = "删除群组文件")
    @DeleteMapping("/group/delete/{fileId}")
    @ResponseBody
    public ReqResult delete(@PathVariable String fileId) {
        String userId = SecurityFacade.getCurUserId();
        boolean flag = deleteFile(fileId, userId, ChatStatus.FileStatus.Delete);
        if (flag) {
            //如果有群成员在删除文件之前未读该条离线消息，报错
            imMsgService.updateMessageReceiverStatus(fileId, ChatStatus.MessageStatus.Send, null);
            //调xxapi发送：删除文件消息：GroupShareFile2APP.SendFileMsg(msg,fileUuid,groupId);
            //================
            return ReqResult.success("删除文件成功");
        } else {
            return ReqResult.failed("删除文件失败");
        }
    }


    /****
     * 获取群组文件
     * @return
     */
    @ApiOperation(value = "获取群组文件")
    @PostMapping("/group/list/get")
    @ResponseBody
    public ReqResult getGroupFileList(@RequestBody JSONObject jsonObject) {
        String groupId = jsonObject.getString("groupId");
        List<FileItem> list = imFileService.getGroupFileList(groupId);
        Hashtable<String, Object> dict = new Hashtable<>();
        for (int i = 0; i < list.size(); i++) {
            dict.put((i + 1) + "", list.get(i).oldToString());
        }
        MessagePackage notice = new MessagePackage();
        notice.setMessageType(IMMessageType.mtGroupMsg);
        notice.setSendUser(SecurityFacade.getCurUserId());
        notice.setReceiveUser(groupId);
        notice.setDescription("getGroupFileList");
        notice.setCode(Command.Common.SUCCESS);
        notice.setDict(dict);
        logger.info("获取群组文件: " + new Gson().toJson(notice));
        return ReqResult.success(JSONObject.fromObject(new Gson().toJson(notice)));
    }

    /****
     * 取消、拒绝文件服务
     */
    @ApiOperation(value = "取消、拒绝文件服务")
    @PostMapping("/cancel")
    @ResponseBody
    public ReqResult fileCancel(@RequestBody JSONObject jsonObject) {
        String fileIds = jsonObject.getString("fileId");
        Boolean flag = false;
        for (String fileId : fileIds.split(",")) {
            //取消、拒绝文件服务
            String userId = imFileService.getFileItem(fileId).getCreateUser();
            flag = deleteFile(fileId, userId, ChatStatus.FileStatus.Reject);
            if (flag) {
                //文件消息设置拒收
                imMsgService.updateMessageReceiverStatus(fileId, ChatStatus.MessageStatus.Reject, null);
            }
            logger.info("拒绝接收文件:" + fileId);
        }
        if (flag) {
            return ReqResult.success("取消接收文件成功");
        } else {
            return ReqResult.failed("取消接收文件失败");
        }
    }

    /****
     * 文件撤回
     * @return
     */
    @ApiOperation(value = "文件撤回")
    @PostMapping("/withdraw")
    @ResponseBody
    public ReqResult fileWithDraw(@RequestBody JSONObject jsonObject) {
        String fileId = jsonObject.getString("fileId");
        String fileName = jsonObject.getString("fileName");
        String receiveUser = jsonObject.getString("receiveUser");
        boolean flag = false;
        //判断是否可撤回?
        List<ChatMessageSend> offLineMsg = imMsgService.getFileOffLineMsg(receiveUser);
        for (ChatMessageSend msg : offLineMsg) {
            if (msg.getFileId().equals(fileId)) {
                flag = true;
                break;
            }
        }
        if (flag) {
            MessagePackage receipt = new MessagePackage();
            receipt.setMessageType(IMMessageType.mtMessageReceipt);
            receipt.setCode(Command.Common.SUCCESS);
            receipt.setReceiveUser(receiveUser);
            receipt.setSendUser("");
            receipt.setParameter("msgType", "0");
            String boldFileName = "<span style='font-weight:bold;'>" + fileName + "</span>";
            receipt.setDescription(boldFileName + " 撤回成功！");
            flag = deleteFile(fileId, SecurityFacade.getCurUserId(), ChatStatus.FileStatus.Withdraw);
            // 文件消息设置撤回
            imMsgService.updateMessageReceiverStatus(fileId, ChatStatus.MessageStatus.Withdraw, null);
            if (flag) {
                logger.info("发送个人文件-文件：" + fileName + " 撤回成功");
                return ReqResult.success(JSONObject.fromObject(new Gson().toJson(receipt)));
            } else {
                return ReqResult.failed("文件撤回出现错误");
            }
        } else {
            //不可撤回
            MessagePackage receipt = new MessagePackage();
            receipt.setMessageType(IMMessageType.mtMessageReceipt);
            receipt.setCode(Command.Common.SUCCESS);
            receipt.setReceiveUser(receiveUser);
            receipt.setParameter("msgType", "0");
            String boldFileName = "<span style='font-weight:bold;'>" + fileName + "</span>";
            receipt.setDescription(boldFileName + " 不可撤回，原因可能是对方已接收该文件或您已经成功撤回。");
            logger.info("文件：" + fileName + " 不可撤回" +JSONObject.fromObject(new Gson().toJson(receipt)));
            return ReqResult.success(JSONObject.fromObject(new Gson().toJson(receipt)));
        }
    }

    /*
     * 删除文件及文件表
     */
    private boolean deleteFile(String fileId, String userId, ChatStatus.FileStatus fileStatus) {
        boolean flag = true;
        if (fileConfig.getDelete()) {
            //删除文件
            flag = imFileService.delFile(fileId, userId);
        }
        if (flag) {
            //更改文件表状态
            flag = imFileService.updateFileStatus(fileId, fileStatus);
        }
        return flag;
    }

}
