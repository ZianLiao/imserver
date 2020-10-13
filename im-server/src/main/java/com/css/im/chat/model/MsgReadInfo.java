package com.css.im.chat.model;

import java.util.List;

/**
 * created by Charles Zhang
 *
 * @date 2020/10/9
 */
public class MsgReadInfo {
    private String msgId;
    private String content;//普通消息：文本内容  文件消息：文件名称
    private List<String> readerList;
    private String msgTime;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getReaderList() {
        return readerList;
    }

    public void setReaderList(List<String> readerList) {
        this.readerList = readerList;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }
}
