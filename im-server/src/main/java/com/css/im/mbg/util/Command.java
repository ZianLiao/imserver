package com.css.im.mbg.util;

public class Command {
    public class Common {
        //在线
        public static final int ONLINE = 1;
        //忙碌
        public static final int BUSY = 2;
        //离开
        public static final int LEAVE = 3;
        //脱机
        public static final int OFFLINE = 4;
        //成功
        public static final String SUCCESS = "success";
        //失败
        public static final String ERROR = "error";
        //发送广播
        public static final int SEND_BROADCAST = 13;
    }

    /// <summary>
    /// 服务器端
    /// </summary>
    public class Service {
        //登录
        public static final int LOGIN = 1;
        //消息
        public static final int TEXTMESSAGE = 3;
        //文件请求
        public static final int FILEQUEST = 4;
        //允许文件
        public static final int FILEACCEPT = 5;
        public static final int SLAVEREGISTER = 6;
        public static final int FILESENDREADY = 7;
        public static final int FILESENDDATA = 8;
        public static final int FILESENDEND = 9;
        //修改用户头像
        public static final int UPDATEHEADIMAGE = 10;
        public static final int USERSTATE = 11;
        public static final int OFFLINEMSG = 12;
        public static final int GROUP = 13;
        public static final int INITCLIENT = 14;
        public static final int GETHEADIMAGE = 21; //@author XB

        public static final int GROUPMESSAGE = 15;
        public static final int BROADCASTINGMSG = 16;
        //取消文件、拒绝文件
        public static final int FILECANCEL = 17;
        public static final int REMOVEREGISTER = 20;
    }

    /// <summary>
    /// 客户端
    /// </summary>
    public class Client {
        public static final int LOGIN = 1;
        public static final int ONLINE = 2;
        public static final int TEXTMESSAGE = 3;
        //文件请求
        public static final int FILEQUEST = 4;
        //文件接收
        public static final int FILEACCEPT = 5;
        //文件发送
        public static final int FILESENDDATA = 8;
        public static final int FILESENDERROR = 15;
        public static final int QUIT = 9;
        public static final int USERHEADCHANGE = 10;
        public static final int GROUPCALLBACK = 11;
        public static final int INIT = 12;
        //取消文件、拒绝文件
        public static final int FILECANCEL = 21;
        public static final int GROUPREADERMSG = 14;
    }

}
