package com.css.im.mbg.service.impl;

import com.css.im.cache.service.IMSessionService;
import com.css.im.mbg.IMMessageType;
import com.css.im.mbg.model.MessagePackage;
import com.css.im.mbg.service.AbstractIMMessageService;
import com.css.im.mbg.util.Command;
import com.css.im.mbg.util.OperateEnum;
import com.css.im.sys.model.SysUser;
import com.css.im.sys.service.SysUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

/**
 * 处理老版本用户消息
 *
 * @date 9/1/2020
 */
@Service
public class IMUserServerService extends AbstractIMMessageService {
    @Autowired
    IMSessionService imSessionService;
    @Autowired
    SysUserService sysUserService;

    @Override
    public IMMessageType getIMMessageType() {
        return IMMessageType.mtUser;
    }

    @Override
    public void handlerMessage(String uid, JSONObject messageObj, Session session) {
        logger.info(String.format("处理用户个人信息：%s", messageObj.toString()));
        MessagePackage message = gson.fromJson(messageObj.toString(), MessagePackage.class);

        String operate = message.getOperate();
        if (OperateEnum.UserType.UPDATE.toString().equals(operate)) {

            SysUser sysUser = sysUserService.getUserById(uid);
            sysUser.setBirthday((String) message.getParameter("birthday"));
            sysUser.setMobile((String) message.getParameter("mobile"));
            sysUser.setPhone((String) message.getParameter("phone"));
            sysUser.setEmail((String) message.getParameter("email"));
            sysUser.setSign((String) message.getParameter("sign"));
            sysUser.setOffice((String) message.getParameter("office"));

            try {
                sysUserService.updateUser(sysUser);
                message.setCode(Command.Common.SUCCESS);
                message.setDescription("个人设置成功！");
            } catch (Exception e) {
                message.setCode(Command.Common.ERROR);
                message.setDescription("个人设置失败！");
            }
            message.setSendUser(uid);
            JSONArray messageArr = new JSONArray();
            messageArr.add(JSONObject.fromObject(gson.toJson(message)));
            sessionWrite(session, messageArr.toString());
        }
    }
}
