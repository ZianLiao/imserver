package com.css.im.mbg.web;

import com.css.common.BaseController;
import com.css.im.chat.ChatStatus;
import com.css.im.chat.model.ChatMessageSend;
import com.css.im.chat.service.IMChatService;
import com.css.im.chat.service.IMMessageEventHandler;
import com.css.im.chat.service.IMMsgService;
import com.css.im.mbg.IMMessageType;
import com.css.im.mbg.model.MessagePackage;
import com.css.im.mbg.model.OANotify;
import com.css.im.sys.service.SysUserService;
import com.css.utils.DateTimeUtils;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * created by Charles Zhang
 *
 * @date 2020/9/24
 */
@Api(tags = "OA消息提醒接口")
@Controller
@RequestMapping("/oa/api")
public class MsgPushController extends BaseController {
    private static Gson gson = new Gson();
    @Autowired
    IMChatService imChatService;
    @Autowired
    SysUserService userService;
    @Autowired
    IMMsgService imMsgService;

    @PostMapping(value = "/notify")
    private void OANotify(@RequestBody JSONObject notifyJson) {
        String notifyStr = notifyJson.getString("notifyStr");
        OANotify notification = gson.fromJson(notifyStr, OANotify.class);
        if (notification == null || notification.getSendUser() == null
                || notification.getTargetUser() == null || notification.getTargetUser().size() < 1) {
            return;
        }
        List<String> targetUserIdList = new ArrayList<>();
        if (notification.getSrcIp().startsWith("isLoginName")) {
            //此时发过来的发送人和接收人均为用户名，需要转换成UUID
            targetUserIdList = notification.getTargetUser().stream().map(user -> translate(user)).collect(Collectors.toList());
            notification.setSendUser(translate(notification.getSendUser()));
        }
        ChatMessageSend messageSend = imChatService.OANotify(notification.getSendUser(),
                targetUserIdList, notification.getContent());
        for (String userId : targetUserIdList) {
            MessagePackage message = new MessagePackage();
            message.setMessageType(IMMessageType.mtMsgPush);
            message.setReceiveUser(userId);
            message.setSendUser(notification.getSendUser());
            message.setDescription("1" + "@" + notification.getTitle() + "@" + notification.getContent()
                    + "@" + notification.getUrl() + "@" + DateTimeUtils.getTodayDateTime() + "@" +
                    notification.getSendUser() + "@" + notification.getFromAppName() + "@" + notification.getSrcIp());
            logger.info("OA消息提醒发送给服务端: " + gson.toJson(message));
            imChatService.response(JSONObject.fromObject(gson.toJson(message)), userId, new IMMessageEventHandler() {
                @Override
                public void onSuccess(List<String> uids) {
                    imMsgService.updateMessageReceiverStatus(messageSend.getMsgId(), messageSend.getFromUid(), uids,
                            ChatStatus.MessageStatus.Send);
                }

                @Override
                public void onFail() {

                }
            });
        }
    }

    private String translate(String username) {
        return userService.getUserIdByUserName(username);
    }
}
