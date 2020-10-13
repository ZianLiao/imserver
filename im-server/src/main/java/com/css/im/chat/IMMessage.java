package com.css.im.chat;

import com.css.utils.DateTimeUtils;
import com.css.utils.UUIDUtils;
import net.sf.json.JSONObject;

import java.util.Objects;

/**
 * Create by wx on 2020-09-11
 */
public class IMMessage {

    private String id;
    private String time;
    private IMMsgType type;
    private JSONObject content;

    private IMMessage() {
    }

    private IMMessage(IMMsgType type) {
        setId(UUIDUtils.generateUUID());
        setTime(DateTimeUtils.formatCurrentTime());
        setType(type);
    }

    public static IMMessage newIMMessage(IMMsgType type) {
        return new IMMessage(type);
    }

    public static IMMessage getIMMessage(JSONObject message) {
        IMMessage msg = new IMMessage();
        msg.setType(message.getString("type"));
        msg.setId(message.getString("id"));
        msg.setTime(message.getString("time"));
        if (null != message.getJSONObject("content")) {
            msg.setContent(message.getJSONObject("content"));
        }
        return msg;
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("time", this.time);
        obj.put("type", this.type.toString());
        if (null != this.content) {
            obj.put("content", this.content);
        }
        return obj;
    }

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    private void setTime(String time) {
        this.time = time;
    }

    public IMMsgType getType() {
        return type;
    }

    public void setType(IMMsgType type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = IMMsgType.valueOf(type);
    }

    public JSONObject getContent() {
        return content;
    }

    public void setContent(JSONObject content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IMMessage imMessage = (IMMessage) o;
        return id.equals(imMessage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
