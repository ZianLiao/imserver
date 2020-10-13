package com.css.im.mbg.service.impl;

import com.css.im.cache.service.IMSessionService;
import com.css.im.chat.service.IMChatService;
import com.css.im.info.model.InfoFrequentConnect;
import com.css.im.info.service.InfoFrequentConnectService;
import com.css.im.mbg.IMMessageType;
import com.css.im.mbg.model.MessagePackage;
import com.css.im.mbg.service.AbstractIMMessageService;
import com.css.im.mbg.util.Command;
import com.css.im.mbg.util.OperateEnum;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

/**
 * created by Charles Zhang
 *
 * @date 9/1/2020
 */
@Service
public class IMDiyGroupOperateService extends AbstractIMMessageService {
    private static final Gson gson = new Gson();
    @Autowired
    IMChatService imChatService;
    @Autowired
    InfoFrequentConnectService ifcService;
    @Autowired
    IMSessionService imSessionService;

    @Override
    public IMMessageType getIMMessageType() {
        return IMMessageType.mtDiyGroup;
    }

    @Override
    public void handlerMessage(String uid, JSONObject messageObj, Session session) {
        logger.info("处理diyGroup信息：" + messageObj.toString());
        //get message package by gson
        MessagePackage message = gson.fromJson(messageObj.toString(), MessagePackage.class);

        OperateEnum.DiyGroupStyle operate = Enum.valueOf(OperateEnum.DiyGroupStyle.class, message.getOperate());
        if (operate == OperateEnum.DiyGroupStyle.ADD) {
            String Name = (String) message.getParameter("Name");
            String userCodes = (String) message.getParameter("userCodes");
            InfoFrequentConnect ifc = new InfoFrequentConnect();
            ifc.setGroupName(Name);
            ifc.setUserId(uid);
            ifc.setConnectUids(userCodes);
            MessagePackage notice = new MessagePackage();
            notice.setMessageType(IMMessageType.mtDiyGroup);
            notice.setOperate(OperateEnum.DiyGroupStyle.ADD.toString());
            try {
                if (ifcService.addInfoFrequentConnect(ifc)) {
                    notice.setCode(Command.Common.SUCCESS);
                    notice.setDescription("自定义分组创建成功！");
                    String diyGroupInfoStr = ifc.getInfoId() + "~" + ifc.getGroupName() + "~" +
                            ifc.getConnectUids();
                    notice.setParameter("diygroupInfo", diyGroupInfoStr);
                } else {
                    notice.setCode(Command.Common.ERROR);
                    notice.setDescription("自定义分组创建失败！");
                }
                JSONArray messageArr = new JSONArray();
                messageArr.add(JSONObject.fromObject(gson.toJson(notice)));
                sessionWrite(session, messageArr.toString());
            } catch (Exception e) {
                logger.error("添加常用联系人分组失败： " + e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        } else if (operate == OperateEnum.DiyGroupStyle.UPDATE) {
            String groupId = (String) message.getParameter("groupId");
            String Name = (String) message.getParameter("Name");
            String userCodes = (String) message.getParameter("userCodes");
            InfoFrequentConnect ifc = new InfoFrequentConnect();
            ifc.setInfoId(groupId);
            ifc.setGroupName(Name);
            ifc.setUserId(uid);
            ifc.setConnectUids(userCodes);
            MessagePackage notice = new MessagePackage();
            notice.setMessageType(IMMessageType.mtDiyGroup);
            notice.setOperate(OperateEnum.DiyGroupStyle.UPDATE.toString());
            try {
                if (ifcService.updateInfoFrequentConnect(ifc)) {
                    notice.setCode(Command.Common.SUCCESS);
                    notice.setDescription("自定义分组修改成功！");
                    String diyGroupInfoStr = ifc.getInfoId() + "~" + ifc.getGroupName() + "~" +
                            ifc.getConnectUids();
                    notice.setParameter("diygroupInfo", diyGroupInfoStr);
                } else {
                    notice.setCode(Command.Common.ERROR);
                    notice.setDescription("自定义分组修改失败！");
                }
                JSONArray messageArr = new JSONArray();
                messageArr.add(JSONObject.fromObject(gson.toJson(notice)));
                sessionWrite(session, messageArr.toString());
            } catch (Exception e) {
                logger.error("更新常用联系人分组失败： " + e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        } else if (operate == OperateEnum.DiyGroupStyle.DELETE) {
            String groupId = (String) message.getParameter("groupId");
            MessagePackage notice = new MessagePackage();
            notice.setMessageType(IMMessageType.mtDiyGroup);
            notice.setOperate(OperateEnum.DiyGroupStyle.DELETE.toString());
            try {
                if (ifcService.delInfoFrequentConnect(groupId)) {
                    notice.setCode(Command.Common.SUCCESS);
                    notice.setDescription("自定义分组删除成功！");
                    notice.setParameter("groupId", groupId);
                } else {
                    notice.setCode(Command.Common.ERROR);
                    notice.setDescription("自定义分组删除失败！");
                }
                JSONArray messageArr = new JSONArray();
                messageArr.add(JSONObject.fromObject(gson.toJson(notice)));
                sessionWrite(session, messageArr.toString());
            } catch (Exception e) {
                logger.error("删除常用联系人分组失败： " + e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        } else if (operate == OperateEnum.DiyGroupStyle.DELETEUSER) {
            String userId = (String) message.getParameter("userId");
            String groupId = (String) message.getParameter("groupId");
            MessagePackage notice = new MessagePackage();
            notice.setMessageType(IMMessageType.mtDiyGroup);
            notice.setOperate(OperateEnum.DiyGroupStyle.DELETEUSER.toString());
            try {
                if (ifcService.delIFCmember(userId, groupId)) {
                    notice.setCode(Command.Common.SUCCESS);
                    notice.setDescription("移除用户成功！");
                    notice.setParameter("userId", userId);
                    notice.setParameter("groupId", groupId);
                    InfoFrequentConnect ifc = ifcService.getByInfoId(groupId);
                    String diyGroupInfoStr = ifc.getInfoId() + "~" + ifc.getGroupName() + "~" +
                            ifc.getConnectUids();
                    notice.setParameter("diygroupInfo", diyGroupInfoStr);
                } else {
                    notice.setCode(Command.Common.ERROR);
                    notice.setDescription("移除用户失败！");
                }
                JSONArray messageArr = new JSONArray();
                messageArr.add(JSONObject.fromObject(gson.toJson(notice)));
                sessionWrite(session, messageArr.toString());
            } catch (Exception e) {
                logger.error("移除常用联系人分组用户失败： " + e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
