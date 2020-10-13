package com.css.im.chat.web;

import com.alibaba.fastjson.JSON;
import com.css.common.BaseController;
import com.css.common.ReqResult;
import com.css.im.auth.SecurityFacade;
import com.css.im.chat.IMMessage;
import com.css.im.chat.IMMsgType;
import com.css.im.chat.model.ChatGroup;
import com.css.im.chat.model.MsgReadInfo;
import com.css.im.chat.service.IMChatGroupService;
import com.css.im.chat.service.IMChatService;
import com.css.im.chat.service.IMMsgService;
import com.css.im.sys.model.SysUser;
import com.css.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Create by wx on 2020-09-10
 */
@Api(tags = "聊天组相关接口")
@RestController
@RequestMapping("/im/api/chat/group")
public class IMChatGroupController extends BaseController {
    @Autowired
    IMChatGroupService chatGroupService;
    @Autowired
    IMChatService imChatService;
    @Autowired
    IMMsgService imMsgService;

    @ApiOperation(value = "创建聊天组接口")
    @PostMapping(value = "/create")
    @ResponseBody
    public ReqResult create(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String chatGroupStr = get(request, "chatGroupStr");
        if (chatGroupStr != null) {
            ChatGroup chatGroup = JSON.parseObject(chatGroupStr, ChatGroup.class);
            String[] groupMembers = chatGroup.getMembersUids().split(",");
            SysUser createUser = SecurityFacade.getUserProfile();
            if (chatGroupService.addChatGroup(chatGroup)) {
                //通知所有的加入群的人
                IMMessage message = IMMessage.newIMMessage(IMMsgType.GROUP_ENTER);
                message.setContent(
                        JSONObject.fromObject(createUser.getName() + "邀请您加入群:" + chatGroup.getChatGroupName()));
                imChatService.inform(JSONObject.fromObject(message), Arrays.asList(groupMembers), null);
                return ReqResult.success();
            }
        }
        return ReqResult.failed();
    }

    @ApiOperation(value = "添加聊天组成员接口")
    @PostMapping(value = "/add")
    @ResponseBody
    public ReqResult addMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //TODO inform the added users
        String[] addUserIds = getValues(request, "addUserIds"); //添加的userId
        String groupId = get(request, "groupId");  //chat group id
        StringBuffer buffer = new StringBuffer();
        if (addUserIds != null) {
            for (String id : addUserIds) {
                buffer.append(id).append(",");
            }
        }
        ChatGroup chatGroup = chatGroupService.getChatGroupById(groupId);
        chatGroup.setMembersUids(buffer.toString() + chatGroup.getMembersUids());
        //这里假设所有人的都可以拉人
        SysUser user = SecurityFacade.getUserProfile();
        if (chatGroupService.updateGroup(chatGroup)) {
            IMMessage message = IMMessage.newIMMessage(IMMsgType.GROUP_ENTER);
            message.setContent(
                    JSONObject.fromObject(user.getName() + "邀请您加入群:" + chatGroup.getChatGroupName()));
            imChatService.inform(JSONObject.fromObject(message), addUserIds);
            return ReqResult.success();
        }
        return ReqResult.failed();
    }

    @ApiOperation(value = "移除聊天组成员接口")
    @PostMapping(value = "/remove")
    @ResponseBody
    public ReqResult removeMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //TODO inform the del users
        String[] delUserIds = getValues(request, "delUserIds");
        String groupId = get(request, "groupId");  //chat group id
        String createUserId = get(request, "createUserId");
        //删除成员的时候只有群主可以删除
        if (!createUserId.equals(SecurityFacade.getCurUserId())) {
            return ReqResult.failed("只有群主才能删除群成员");
        }
        ChatGroup chatGroup = chatGroupService.getChatGroupById(groupId);
        String memberIds = chatGroup.getMembersUids();
        for (String delId : delUserIds) {
            if (memberIds.contains(delId)) {
                memberIds.replace(delId, "");
                if (memberIds.startsWith(",")) {
                    memberIds = memberIds.substring(1);
                } else if (memberIds.endsWith(",")) {
                    memberIds = memberIds.substring(0, memberIds.length() - 1);
                } else {
                    memberIds = memberIds.replace(",,", ",");
                }
            }
        }
        chatGroup.setMembersUids(memberIds);
        if (chatGroupService.updateGroup(chatGroup)) {
            IMMessage message = IMMessage.newIMMessage(IMMsgType.GROUP_KIFF);
            message.setContent(
                    JSONObject.fromObject("您被群主移出群:" + chatGroup.getChatGroupName()));
            imChatService.inform(JSONObject.fromObject(message), delUserIds);
            return ReqResult.success();
        }
        return ReqResult.failed("删除群成员失败！");
    }

    @ApiOperation(value = "更改聊天组名称接口")
    @PostMapping(value = "/update")
    @ResponseBody
    public ReqResult update(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String groupId = get(request, "groupId"); //所有聊天组人员
        String groupName = get(request, "groupName");  //聊天组名称
        if (groupId != null && groupName != null) {
            ChatGroup chatGroup = new ChatGroup();
            chatGroup.setChatGroupId(groupId);
            chatGroup.setChatGroupName(groupName);
            if (chatGroupService.updateGroup(chatGroup)) {
                return ReqResult.success();
            }
        }
        return ReqResult.failed();
    }

    @ApiOperation(value = "获取群组列表")
    @GetMapping(value = "/chatgrouplist")
    @ResponseBody
    public ReqResult getChatGroupList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //get the current user's id
        String userId = SecurityFacade.getCurUserId();
        List<ChatGroup> chatGroupList = chatGroupService.getChatGroupsByUser(userId);
        if (chatGroupList != null) {
            return ReqResult.success(chatGroupList);
        }
        return ReqResult.failed();
    }

    @ApiOperation(value = "获取某个群的详细信息")
    @GetMapping(value = "/groupdetail")
    @ResponseBody
    public ReqResult getGroupDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String groupId = get(request, "groupId");  //chat group Id
        ChatGroup chatGroup = chatGroupService.getChatGroupById(groupId);
        if (chatGroup != null) {
            return ReqResult.success(chatGroup);
        }
        return ReqResult.failed();
    }

    @ApiOperation(value = "删除聊天群")
    @PostMapping(value = "/delete")
    @ResponseBody
    public ReqResult delete(HttpServletRequest request) throws Exception {
        String groupId = get(request, "groupId");
        String createUserId = get(request, "createUserId");
        if (!createUserId.equals(SecurityFacade.getCurUserId())) {
            return ReqResult.failed("只有群主能够删除群！");
        }
        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setChatGroupId(groupId);
        chatGroup.setStatus((short) -1);
        if (chatGroupService.updateGroup(chatGroup)) {
            return ReqResult.success();
        }
        return ReqResult.failed();
    }

    @ApiOperation(value = "群主转移")
    @PostMapping(value = "/delegategroupowner")
    @ResponseBody
    public ReqResult delegateGroupOwner(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String groupId = get(request, "groupId");
        String newOwner = get(request, "newOwner");
        //TODO check if the new owner is the group member
        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setChatGroupId(groupId);
        chatGroup.setCreateUserId(newOwner);
        if (chatGroupService.updateGroup(chatGroup)) {
            return ReqResult.success();
        }
        return ReqResult.failed();
    }

    @ApiOperation(value = "获取群消息已读情况")
    @GetMapping(value = "/readinfo/{groupId}")
    @ResponseBody
    public ReqResult getReadMsg(@PathVariable String groupId) throws Exception {
        if (StringUtils.isEmpty(groupId)) {
            return ReqResult.failed();
        }
        String senderId = SecurityFacade.getCurUserId();
        JSONObject infoJson = new JSONObject();
        infoJson.put("groupId", groupId);
        //获取普通消息已读情况
        List<MsgReadInfo> msgReadInfoList = imMsgService.getReadUserList(groupId, senderId, false);
        //获取文件消息已读情况
        List<MsgReadInfo> fileReadInfoList = imMsgService.getReadUserList(groupId, senderId, true);
        infoJson.put("msgReadInfo", msgReadInfoList);
        infoJson.put("fileReadInfo", fileReadInfoList);
        logger.info("群消息已读情况的JSON: " + infoJson.toString());
        return ReqResult.success(infoJson);
    }
}
