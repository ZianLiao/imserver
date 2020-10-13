package com.css;

import com.css.util.DateTimeUtils;
import net.sf.json.JSONObject;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestComponent;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * created by Charles Zhang
 *
 * @date 9/2/2020
 */
public class WsClientTest {

    private static Logger logger = LoggerFactory.getLogger(WsClientTest.class);

    public static WebSocketClient client1;
    public static WebSocketClient client2;
    public static WebSocketClient client3;

    static {
        try {
            client1 = new WebSocketClient(new URI("ws://localhost:8080/imserver/v1/1234567"), new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    logger.info("张三握手成功");
                }
                @Override
                public void onMessage(String msg) {
                    logger.info("张三收到服务端消息==========" + msg);
                }
                @Override
                public void onClose(int i, String s, boolean b) {
                    logger.info("链接已关闭");
                }
                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    logger.info("发生错误已关闭");
                }
            };
            client2 = new WebSocketClient(new URI("ws://localhost:8080/imserver/v1/1234568"), new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    logger.info("李四握手成功");
                }
                @Override
                public void onMessage(String msg) {
                    logger.info("李四收到服务端消息==========" + msg);
                }
                @Override
                public void onClose(int i, String s, boolean b) {
                    logger.info("链接已关闭");
                }
                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    logger.info("发生错误已关闭");
                }
            };
            client3 = new WebSocketClient(new URI("ws://localhost:8080/imserver/v1/1234569"), new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    logger.info("王五握手成功");
                }
                @Override
                public void onMessage(String msg) {
                    logger.info("王五收到服务端消息==========" + msg);
                }
                @Override
                public void onClose(int i, String s, boolean b) {
                    logger.info("链接已关闭");
                }
                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    logger.info("发生错误已关闭");
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        client1.connect();
//        while (!client1.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
//            logger.info("client1正在连接...");
//        }
        client2.connect();
        client3.connect();
    }
    @Test
    public void testAddGroup() {
        //连接成功,发送信息
        JSONObject obj = new JSONObject();
        obj.put("MessageType", "mtGroup");
        obj.put("Code", "");
        obj.put("Description", "this time is add message");
        obj.put("ReceiveUser", "");
        obj.put("SendUser", "1234567");
        JSONObject dict = new JSONObject();
        JSONObject groupInfo = new JSONObject();
        groupInfo.put("CreateTime", DateTimeUtils.formatCurrentTime());
        groupInfo.put("CreateUser", "1234567");
        groupInfo.put("CreateUserName", "张三");
        groupInfo.put("Name","testGroup");
        dict.put("groupInfo", groupInfo);
        dict.put("userCodes", "1234567,1234568,1234569");
        obj.put("Dict", dict);
        obj.put("Operate", "ADD");
        client1.send(obj.toString());
        System.out.println("111");
    }

    @Test
    public void testDelGroup() {
        //连接成功,发送信息
        JSONObject obj = new JSONObject();
        obj.put("MessageType", "mtGroup");
        obj.put("Code", "");
        obj.put("Description", "this time is del message");
        obj.put("ReceiveUser", "");
        obj.put("SendUser", "1234567");
        JSONObject dict = new JSONObject();
        dict.put("groupId", "4ff2adfa24344dac84eb49d089c3d7fa");
        dict.put("groupName", "testGroup");
        obj.put("Dict", dict);
        obj.put("Operate", "DELETE");
        client1.send(obj.toString());
        System.out.println("111");
    }

    @Test
    public void testGroupMessage() {
        //连接成功,发送信息
        JSONObject obj = new JSONObject();
        obj.put("MessageType", "mtGroupMsg");
        obj.put("Code", "");
        obj.put("Description", "group message!!!");
        obj.put("ReceiveUser", "");
        obj.put("SendUser", "1234567");
        JSONObject dict = new JSONObject();
        dict.put("groupUuid", "2d83aa574eaa42a1a4f65cf319161862");
        dict.put("msg", "大家好这是一个测试群组消息");
        dict.put("type", 2);
        obj.put("Dict", dict);
        obj.put("Operate", "");
        client1.send(obj.toString());
        System.out.println("111");
    }

    @Test
    public void testBroadCastMessage() {
        //连接成功,发送信息
        JSONObject obj = new JSONObject();
        obj.put("MessageType", "mtBroadcastingMsg");
        obj.put("Code", "");
        obj.put("Description", "broadcast message!!!");
        obj.put("ReceiveUser", "");
        obj.put("SendUser", "");
        JSONObject dict = new JSONObject();
        dict.put("groupUuid", "2d83aa574eaa42a1a4f65cf319161862");
        dict.put("msg", "大家好这是一个测试广播消息");
        dict.put("receiver", "1234568,1234569");
        dict.put("receiverName", "");
        dict.put("selfUserCode", "1234567");
        dict.put("selfUserName", "张三");
        dict.put("text", "大家好我是张三！");
        dict.put("title", "");
        dict.put("type", 3);
        dict.put("uuid", "");
        obj.put("Dict", dict);
        obj.put("Operate", "");
        client1.send(obj.toString());
        System.out.println("111");
    }

    public static void main(String[] args) {

        //连接成功,发送信息
        JSONObject obj = new JSONObject();
        obj.put("MessageType", "mtGroup");
        obj.put("Code", "");
        obj.put("Description", "this time is user exit message");
        obj.put("ReceiveUser", "");
        obj.put("SendUser", "1234567");
        JSONObject dict = new JSONObject();
        dict.put("groupId", "c67ed72d31464c8faf6c1d94ac236543");
        dict.put("groupName", "testUpdateGroup");
        dict.put("CreateUserName", "张三");
        dict.put("Name", "testAddGroup");
        dict.put("userCodes", "1234567,1234569");
        obj.put("Dict", dict);
        obj.put("Operate", "USEREXIT");
        client1.send(obj.toString());
        System.out.println("111");
    }
}
