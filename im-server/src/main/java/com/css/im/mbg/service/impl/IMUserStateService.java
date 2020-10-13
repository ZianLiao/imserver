package com.css.im.mbg.service.impl;

import com.css.im.cache.service.IMSessionService;
import com.css.im.chat.service.IMChatService;
import com.css.im.mbg.IMMessageType;
import com.css.im.mbg.model.MessagePackage;
import com.css.im.mbg.service.AbstractIMMessageService;
import com.css.im.mbg.util.Command;
import com.css.im.sys.Status;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.List;

/**
 * created by Charles Zhang
 *
 * @date 9/18/2020
 */
@Service
public class IMUserStateService extends AbstractIMMessageService {
    @Autowired
    IMChatService imChatService;
    @Autowired
    IMSessionService imSessionService;

    @Override
    public IMMessageType getIMMessageType() {
        return IMMessageType.mtChangeUserState;
    }

    @Override
    public void handlerMessage(String uid, JSONObject messageObj, Session session) {
        logger.info("处理用户状态改变：" + messageObj.toString());
        try {
            MessagePackage message = gson.fromJson(messageObj.toString(), MessagePackage.class);
            Status.UserOnlineStatus userOnlineStatus =
                    Status.UserOnlineStatus.statusOf(Short.valueOf(message.getDescription()));
            imSessionService.cacheUserOnlineStatus
                    (uid, userOnlineStatus);
            MessagePackage onLineNotice = new MessagePackage();
            onLineNotice.setMessageType(IMMessageType.mtChangeUserState);
            onLineNotice.setCode(Command.Common.SUCCESS);
            if (userOnlineStatus.equals(Status.UserOnlineStatus.ONLINE))
                onLineNotice.setDescription(userOnlineStatus.getStatusInfo());
            else if (userOnlineStatus.equals(Status.UserOnlineStatus.BUSY))
                onLineNotice.setDescription(userOnlineStatus.getStatusInfo());
            else if (userOnlineStatus.equals(Status.UserOnlineStatus.LEAVE))
                onLineNotice.setDescription(userOnlineStatus.getStatusInfo());
            List<String> allOnlineUsers = imSessionService.getAllOnlineUsers();
            StringBuffer buffer = new StringBuffer();
            for (String userId : allOnlineUsers) {
                buffer.append(userId).append(",").append(imSessionService.getUserOnlineStatus(userId).status()).append("&");
            }
            onLineNotice.setParameter("onLineUser", buffer.toString());

            logger.debug("发送给客户端关于userChangeState的消息：" + gson.toJson(onLineNotice));
            imChatService.inform(JSONObject.fromObject(gson.toJson(onLineNotice)), allOnlineUsers.toArray(new String[0]));
        } catch (Exception e) {
            logger.error("处理用户状态改变出错： " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
