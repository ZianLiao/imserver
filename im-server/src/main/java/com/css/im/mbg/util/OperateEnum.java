package com.css.im.mbg.util;

public class OperateEnum {
    public enum GroupStyle {
        ADD, UPDATE, SELECT, DELETE, USEREXIT, USERJOIN, NOTICESRDELETE, NOTICESRCREATE
    }

    public enum DiyGroupStyle {
        ADD, UPDATE, DELETE, DELETEUSER
    }

    public enum BroadcastingStyle {
        RECEIVER
    }

    public enum LoginStyle {
        LOGIN, REPEATLOGIN, AUTOLOGIN
    }

    public enum FileType {
        QUEST, ACCEPT, CANCEL, CHANAGE, DATA
    }

    public enum UserType {
        UPDATE
    }

    public enum CommonContactType {
        ADD, DELETE, SELECT, UPDATE
    }

}
