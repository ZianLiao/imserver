package com.css.im.websocket.service.impl;

import com.css.im.cache.service.IMSessionService;
import com.css.im.chat.model.ChatGroup;
import com.css.im.chat.service.IMChatGroupService;
import com.css.im.chat.service.IMChatService;
import com.css.im.info.model.InfoFrequentConnect;
import com.css.im.info.service.InfoFrequentConnectService;
import com.css.im.log.Log;
import com.css.im.mbg.IMMessageType;
import com.css.im.mbg.model.MessagePackage;
import com.css.im.mbg.service.WebSocketMessageService;
import com.css.im.mbg.util.Command;
import com.css.im.mbg.util.OperateEnum;
import com.css.im.org.model.OrgDept;
import com.css.im.org.service.IMOrgService;
import com.css.im.sys.model.SysUser;
import com.css.im.sys.service.SysUserService;
import com.css.im.websocket.service.AbstractWebSocketService;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 提供老版本socket服务
 * Create by wx on 2020-09-15
 */
@Service
public class WebSocketServiceV1Impl extends AbstractWebSocketService {

    @Autowired
    WebSocketMessageService webSocketMessageService;

    @Autowired
    IMSessionService iMSessionService;

    @Autowired
    IMChatService imChatService;

    @Autowired
    SysUserService userService;

    @Autowired
    IMOrgService orgService;

    @Autowired
    IMChatGroupService chatGroupService;

    @Autowired
    InfoFrequentConnectService ifcService;

    @Override
    public WebSocketVersion getWsVersion() {
        return WebSocketVersion.v1;
    }

    @Override
    public void prevOpen(Session session, String wsVersion, String uid) {
        //检查用户是否已经登录，已经登录
        Session existSession = iMSessionService.getUserSession(uid);
        if (existSession != null && existSession.isOpen()) {
            if (!existSession.getQueryString().equals(session.getQueryString())) {
                MessagePackage res = new MessagePackage();
                res.setMessageType(IMMessageType.mtLogin);
                res.setCode(Command.Common.ERROR);
                res.setOperate(OperateEnum.LoginStyle.REPEATLOGIN.toString());
                res.setDescription("您的账号已经在其它地方登录！");
                JSONArray messageArr = new JSONArray();
                messageArr.add(JSONObject.fromObject(new Gson().toJson(res)));
                sessionWrite(existSession, messageArr.toString());
                try {
                    existSession.close();
                } catch (IOException e) {
                    logger.info("clos");
                }

            }

        }
    }

    @Log("onOpen方法用时")
    @Override
    public void onOpen(Session session, String wsVersion, String uid) {
        //TODO: 推送用户信息、机构人员信息
        //获取所有组织机构信息
        List<OrgDept> allOrgs = orgService.getAllOrg();
        StringBuffer allOrgInfo = new StringBuffer();
        for (OrgDept orgDept : allOrgs) {
            allOrgInfo.append(orgDept.getDeptId() + "," + orgDept.getDeptName() + "," + orgDept.getFatherId() + "," + orgDept.getOrderNum() + "%");
        }
        allOrgInfo.append("*");
        //拼接所有的组织机构和人员
        String userInfo = allOrgInfo.toString() + buildAllUser();
//        logger.debug("登录获得的组织机构和人员信息" + userInfo);
        //获取群组信息
        List<ChatGroup> chatGroups = chatGroupService.getChatGroupsByUser(uid);
        StringBuffer buffer = new StringBuffer();
        if (chatGroups != null && chatGroups.size() > 0) {
            for (ChatGroup group : chatGroups) {
                buffer.append(group.getChatGroupId() + "~" + group.getChatGroupName() + "~"
                        + group.getCreateTime() + "~" + group.getCreateUserId() + "~" +
                        group.getCreateUserName() + "~" + group.getMembersUids() + "&");
            }
        }
        String groupList = buffer.toString();
        logger.debug("登录获得的群组信息" + groupList);
        buffer = new StringBuffer();
        List<InfoFrequentConnect> ifcList = ifcService.getListByUser(uid);
        for (InfoFrequentConnect ifc : ifcList) {
            buffer.append(ifc.getInfoId() + "~" + ifc.getGroupName() + "~" + ifc.getConnectUids() + "&");
        }
        String diyGroupList = buffer.toString();
        logger.debug("登录获得的常用联系人分组信息" + diyGroupList);
        MessagePackage loginResponse = new MessagePackage();
        loginResponse.setMessageType(IMMessageType.mtLogin);
        loginResponse.setCode("success");
        loginResponse.setOperate("LOGIN");
        loginResponse.setDescription("登录成功");
        loginResponse.setParameter("groupList", groupList);
        loginResponse.setParameter("userinfo", userInfo);
        loginResponse.setParameter("commonContact", "");
        loginResponse.setParameter("diyGroupList", diyGroupList);
        loginResponse.setParameter("userCode", uid);
        JSONArray messageArr = new JSONArray();
        messageArr.add(JSONObject.fromObject(new Gson().toJson(loginResponse)));
        sessionWrite(session, messageArr.toString());
    }

    @Override
    public void afterOpen(Session session, String wsVersion, String uid) {
        //广播用户上线消息
        //给所有在线用户发送当前用户在线情况.
        MessagePackage onLineNotice = new MessagePackage();
        onLineNotice.setMessageType(IMMessageType.mtOnLine);
        onLineNotice.setCode(Command.Common.SUCCESS);
        onLineNotice.setOperate(OperateEnum.LoginStyle.LOGIN.toString());
        onLineNotice.setDescription("在线用户！");
        //TODO: 取在线用户信息
        //先给登录人发送mtOnLine,再给其他人发送mtOnLine,确保总是登录成功
        StringBuffer buffer = new StringBuffer();
        List<String> onlineUsers = iMSessionService.getAllOnlineUsers();
        for (String userId : onlineUsers) {
            Short status = iMSessionService.getUserOnlineStatus(userId).status();
            buffer.append(userId).append(",").append(status).append("&");
        }
//        buffer.deleteCharAt(buffer.length() - 1);
        onLineNotice.setParameter("onLineUser", buffer.toString());
        logger.info(uid + "上线，给所有在线用户发送消息：" + new Gson().toJson(onLineNotice));
        imChatService.inform(JSONObject.fromObject(new Gson().toJson(onLineNotice)),
                onlineUsers, null);
    }

    @Override
    public void onClose(Session session, String wsVersion, String uid) {
        //获得现存的在线用户
        StringBuffer buffer = new StringBuffer();
        List<String> onlineUsers = iMSessionService.getAllOnlineUsers();
        for (String userId : onlineUsers) {
            SysUser user = userService.getUserById(userId);
            //获取在线状态并添加
            buffer.append(userId).append(",").append(iMSessionService.getUserOnlineStatus(userId).status()).append("&");
        }
        if (buffer.length() > 0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        // 通知其他在线用户该用户已下线
        MessagePackage offLineNotice = new MessagePackage();
        offLineNotice.setMessageType(IMMessageType.mtLogout);
        offLineNotice.setCode(Command.Common.SUCCESS);
        offLineNotice.setOperate(OperateEnum.LoginStyle.LOGIN.toString());
        offLineNotice.setDescription("用户下线！");
        offLineNotice.setParameter("offLineUser", uid);
        imChatService.inform(JSONObject.fromObject(new Gson().toJson(offLineNotice)),
                onlineUsers.toArray(new String[0]));

        //广播用户下线消息,给所有人发送最新在线用户
        MessagePackage onLineNotice = new MessagePackage();
        onLineNotice.setMessageType(IMMessageType.mtOnLine);
        onLineNotice.setCode(Command.Common.SUCCESS);
        onLineNotice.setOperate(OperateEnum.LoginStyle.LOGIN.toString());
        onLineNotice.setDescription("在线用户！");


        onLineNotice.setParameter("onLineUser", buffer.toString());
        logger.info(uid + "下线，给所有在线用户发送消息：" + new Gson().toJson(onLineNotice));
        imChatService.inform(JSONObject.fromObject(new Gson().toJson(onLineNotice)),
                onlineUsers.toArray(new String[onlineUsers.size()]));
    }

    @Override
    public void afterClose(Session session, String wsVersion, String uid) {

    }

    @Override
    public void onMessage(String uid, String message, Session session) {
        webSocketMessageService.messageHandle(uid, message, session);
    }

    @Override
    public void onError(Session session, Throwable error) {

    }


    private String buildAllUser() {
        StringBuilder str = new StringBuilder();
        List<SysUser> allUser = userService.getAllUser();
        for (SysUser user : allUser) {
            str.append(user.getDeptId()).append("~").append(user.getUsername()).
                    append("~").append(user.getUserId()).append("~").append(user.getName()).
                    append("~").append(user.getSex()).append("~").append(user.getMobile()).
                    append("~").append(user.getPhone()).append("~").append(user.getEmail()).
                    append("~").append("~").append(user.getDuty()).append("~").
                    append(user.getBirthday()).append("~").append(user.getOffice()).append("~").
                    append(user.getExtensionNum()).append("~").append(user.getSign()).append("~").
                    append(converterToFirstSpell(user.getName())).append("~").append(user.getMail()).append("&");
        }
        return str.toString();
    }

    public static void main(String[] args) {
        System.out.println(converterToFirstSpell("长城朝"));
    }

    //多音字处理
    public static String converterToFirstSpell(String chines) {
        if(StringUtils.isBlank(chines)){
            return "";
        }
        StringBuffer pinyinName = new StringBuffer();
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : nameChar) {
            if (c > 128) {
                try {
                    //183 名字中存在中间黑点(183)的过滤
                    if (c != 183) {
                        String[] strs = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
                        if (strs != null) {
                            for (int j = 0; j < strs.length; j++) {
                                pinyinName.append(strs[j].charAt(0));
                                if (j != strs.length - 1) {
                                    pinyinName.append(",");
                                }
                            }
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName.append(c);
            }
            pinyinName.append(" ");
        }
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
    }

    private static String parseTheChineseByObject(List<Map<String, Integer>> list) {
        Map<String, Integer> first = null;
        for (Map<String, Integer> stringIntegerMap : list) {
            Map<String, Integer> temp = new Hashtable<String, Integer>();
            if (first != null) {
                for (String s : first.keySet()) {
                    for (String s1 : stringIntegerMap.keySet()) {
                        String str = s + s1;
                        temp.put(str, 1);
                    }
                }
                if (temp.size() > 0) {
                    first.clear();
                }
            } else {
                for (String s : stringIntegerMap.keySet()) {
                    temp.put(s, 1);
                }
            }
            if (temp.size() > 0) {
                first = temp;
            }
        }
        StringBuilder returnStr = new StringBuilder();
        if (first != null) {
            for (String str : first.keySet()) {
                returnStr.append(str).append(",");
            }
        }
        if (returnStr.length() > 0) {
            returnStr = new StringBuilder(returnStr.substring(0, returnStr.length() - 1));
        }
        return returnStr.toString();
    }

    private static List<Map<String, Integer>> discountTheChinese(String theStr) {
        List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();
        Map<String, Integer> onlyOne = null;
        String[] firsts = theStr.split(" ");
        for (String str : firsts) {
            onlyOne = new Hashtable<String, Integer>();
            String[] china = str.split(",");
            for (String s : china) {
                Integer count = onlyOne.get(s);
                if (count == null) {
                    onlyOne.put(s, 1);
                } else {
                    onlyOne.remove(s);
                    count++;
                    onlyOne.put(s, count);
                }
            }
            mapList.add(onlyOne);
        }
        return mapList;
    }
}
