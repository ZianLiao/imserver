package com.css.im.chat.service;

import java.util.List;

public interface IMMessageEventHandler {
    /***
     * 消息发送成功的用户
     * @param uids
     */
    void onSuccess(List<String> uids);

    void onFail();
}
