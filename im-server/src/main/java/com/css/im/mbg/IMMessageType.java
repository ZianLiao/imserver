package com.css.im.mbg;

/**
 * 定义消息类型
 * Create by wx on 2020-08-28
 */
public enum IMMessageType {
    //保留
    InValid,
    /// <summary>
    /// 登陆
    /// </summary>
    mtLogin,
    /// <summary>
    /// 登出
    /// </summary>
    mtLogout,
    /// <summary>
    /// 消息
    /// </summary>
    mtMessage,
    mtMessageReceipt,
    mtMessageAutoReply,
    /// <summary>
    /// 消息推送
    /// </summary>
    mtMsgPush,
    mtMsgDBPush,//待办事项数目推送
    mtMsgOfficePush,//公告推送
    mtMsgServerBroadcast,//管理员在服务端添加的提醒
    mtOnLine,
    /// <summary>
    /// SSO登录
    /// </summary>
    mtSSOLogin,
    /// <summary>
    /// 改变用户登陆状态
    /// </summary>
    mtChangeUserState,
    /// <summary>
    /// 离线消息
    /// </summary>
    mtOffLineMsg,
    /// <summary>
    /// 自定义分组
    /// </summary>
    mtDiyGroup,
    /// <summary>
    /// 群组
    /// </summary>
    mtGroup,
    /// <summary>
    /// 群组聊天信息
    /// </summary>
    mtGroupMsg,
    mtGroupMsgReceipt,
    /// <summary>
    /// 广播消息
    /// </summary>
    mtBroadcastingMsg,
    mtBroadcastingReceipt,
    /// <summary>
    /// 接受文件
    /// </summary>
    mtFileAccept,
    /// <summary>
    /// 拒绝接收
    /// </summary>
    mtFileCancel,
    /// <summary>
    /// 文件接收选项
    /// </summary>
    mtFileQuest,
    /// <summary>
    /// 文件
    /// </summary>
    mtFile,
    mtFileWithdraw,
    /// <summary>
    /// 用户相关
    /// </summary>
    mtUser,
    mtLookMsgPush,
    mtCommonContact,
    /// <summary>
    /// 窗口抖动
    /// </summary>
    mtShake,
    /// <summary>
    /// 发送文件
    /// </summary>
    mtFileSendData,
    mtFileReceiveNotice,
    mtFileReceiveData,
    /// <summary>
    /// URL
    /// </summary>
    mtGetUrl,
    /// <summary>
    /// 在线升级
    /// </summary>
    mtCheckUpdate,
    mtUpdate,
    //通过ip获得用户信息
    mtIP2UserInfo,
    //客户端间隔调用,查看此客户端是否在线.
    mtPingServer,
    //处理外部系统消息
    mtExtSysMessage,


    //移动端消息
    mtTextPC2APP,

    //消息已读
    mtMsgReadAlready

}
