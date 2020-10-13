package com.css.wesocket;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by wx on 2020-09-07
 */
public class WSTest {
    private static Logger logger = LoggerFactory.getLogger(WSTest.class);
    public static IMWebSocketClient client1;
    public static IMWebSocketClient client2;
    public static IMWebSocketClient client3;
//
//    static {
//        String urlLogin = "http://localhost:8080/im/api/login";
//        Map<String,Object> params = new HashMap<String,Object>();
//        params.put("username","test");
//        params.put("password","123456");
//        HttpResponse response = HttpRequest.post(urlLogin).header("Contetnt-Type","application/json").form(params).timeout(-1).execute();
//        String ret = response.body();
//        String token = response.header("Authorization");
//
//        String urlGetWs = "http://localhost:8080/im/api/ws/server";
//        Map<String, Object> params1 = new HashMap<String, Object>();
//        Map<String, Object> params2 = new HashMap<String, Object>();
//        Map<String, Object> params3 = new HashMap<String, Object>();
//
//        params1.put("username", "test");
//        params1.put("password", "123456");
//
//        params2.put("username", "test1");
//        params2.put("password", "112233");
//
//        params3.put("username", "test2");
//        params3.put("password", "445566");
//
//        HttpResponse response1 = HttpRequest.post(urlLogin).header("Contetnt-Type","application/json").form(params1).timeout(-1).execute();
//        String ret1 = response1.body();
//        String token1 = response1.header("Authorization");
//        if (StringUtils.isNotBlank(token1)) {
//            params1 = new HashMap<>();
//            params1.put("version", "v1");
//            response1 = HttpRequest
//                    .post(urlGetWs).header("Authorization", token1)
//                    .form(params1).timeout(-1).execute();
//            JSONObject obj1 = JSONObject.fromObject(response1.body());
//            String wsUrl1 = obj1.getString("data");
//            URI uri1 = null;
//            try {
//                uri1 = new URI(wsUrl1);
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//            client1 = new IMWebSocketClient(uri1, null);
//            client1.connect();
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
////        HttpResponse response2 = HttpRequest.post(urlLogin).form(params2).timeout(-1).execute();
////        String token2 = response2.header("Authorization");
////        if (StringUtils.isNotBlank(token2)) {
////            params2 = new HashMap<>();
////            params2.put("version", "v1");
////            response2 = HttpRequest
////                    .post(urlGetWs).header("Authorization", token2)
////                    .form(params2).timeout(-1).execute();
////            JSONObject obj2 = JSONObject.fromObject(response2.body());
////            String wsUrl2 = obj2.getString("data");
////            URI uri2 = null;
////            try {
////                uri2 = new URI(wsUrl2);
////            } catch (URISyntaxException e) {
////                e.printStackTrace();
////            }
////            client2 = new IMWebSocketClient(uri2, null);
////            client2.connect();
////            try {
////                Thread.sleep(3000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////        }
//
////        HttpResponse response3 = HttpRequest.post(urlLogin).form(params3).timeout(-1).execute();
////        String token3 = response3.header("Authorization");
////        if (StringUtils.isNotBlank(token3)) {
////            params3 = new HashMap<>();
////            params3.put("version", "v1");
////            response3 = HttpRequest
////                    .post(urlGetWs).header("Authorization", token3)
////                    .form(params3).timeout(-1).execute();
////            JSONObject obj3 = JSONObject.fromObject(response3.body());
////            String wsUrl3 = obj3.getString("data");
////            URI uri3 = null;
////            try {
////                uri3 = new URI(wsUrl3);
////            } catch (URISyntaxException e) {
////                e.printStackTrace();
////            }
////            client3 = new IMWebSocketClient(uri3, null);
////            client3.connect();
////            try {
////                Thread.sleep(3000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////        }
//    }

    @Test
    public void testMessage() {
        JSONObject obj = new JSONObject();
        obj.put("MessageType", "mtMessage");
        obj.put("Code", "");
        obj.put("Description", "hello 你好啊");
        obj.put("ReceiveUser", "9f90fffc6bd44b3abcea1549a194a01a");
        obj.put("SendUser", "2b3ad4abf51d43b1a01715ab724682f6");
        JSONObject dict = new JSONObject();
        dict.put("msgToApp", "");
        dict.put("msgType", "0");
        obj.put("Dict", dict);
        obj.put("Operate", "");
        client1.send(obj.toString());
        System.out.println("====end====");
    }
    @Test
    public void testCommonContact() {
        JSONObject obj = new JSONObject();
        obj.put("MessageType", "mtCommonContact");
        obj.put("Code", "");
        obj.put("Description", "hello 你好啊");
        obj.put("ReceiveUser", "9f90fffc6bd44b3abcea1549a194a01a");
        obj.put("SendUser", "2b3ad4abf51d43b1a01715ab724682f6");
        JSONObject dict = new JSONObject();
        dict.put("msgToApp", "");
        dict.put("msgType", "");
        obj.put("Dict", dict);
        obj.put("Operate", "");
        client1.send(obj.toString());
        System.out.println("====end====");
    }

    @Test
    public void testDiyGroupAdd() {
        JSONObject obj = new JSONObject();
        obj.put("MessageType", "mtDiyGroup");
        obj.put("Code", "");
        obj.put("Description", "this is diy group add message!");
        obj.put("ReceiveUser", "");
        obj.put("SendUser", "");
        JSONObject dict = new JSONObject();
        dict.put("Name", "DiyGroupTest");
        dict.put("userCodes", "9f90fffc6bd44b3abcea1549a194a01a,541726f82c664f1d8e278c953f25334e");
        obj.put("Dict", dict);
        obj.put("Operate", "ADD");
        client1.send(obj.toString());
        System.out.println("====end====");
    }

    @Test
    public void testDiyGroupUpdate() {
        JSONObject obj = new JSONObject();
        obj.put("MessageType", "mtDiyGroup");
        obj.put("Code", "");
        obj.put("Description", "this is diy group update message!");
        obj.put("ReceiveUser", "");
        obj.put("SendUser", "");
        JSONObject dict = new JSONObject();
        dict.put("Name", "DiyGroupTestUpdate");
        dict.put("userCodes", "9f90fffc6bd44b3abcea1549a194a01a,541726f82c664f1d8e278c953f25334e");
        dict.put("groupId", "e20efc7675ee43d4ac9daa2ccb5c762d");
        obj.put("Dict", dict);
        obj.put("Operate", "UPDATE");
        client1.send(obj.toString());
        System.out.println("====end====");
    }

    @Test
    public void testDiyGroupDelUser() {
        JSONObject obj = new JSONObject();
        obj.put("MessageType", "mtDiyGroup");
        obj.put("Code", "");
        obj.put("Description", "this is diy group delete user message!");
        obj.put("ReceiveUser", "");
        obj.put("SendUser", "");
        JSONObject dict = new JSONObject();
//        dict.put("Name", "DiyGroupTestUpdate");
        dict.put("userId", "9f90fffc6bd44b3abcea1549a194a01a");
        dict.put("groupId", "e20efc7675ee43d4ac9daa2ccb5c762d");
        obj.put("Dict", dict);
        obj.put("Operate", "DELETEUSER");
        client1.send(obj.toString());
        System.out.println("====end====");
    }

    @Test
    public void test1() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        boolean flag = list.remove("A");
        if (!flag) {
            System.out.println("111");
        } else {
            System.out.println(list.get(0));
        }

    }

    @Test
    public void testDiyGroupDel() {
        JSONObject obj = new JSONObject();
        obj.put("MessageType", "mtDiyGroup");
        obj.put("Code", "");
        obj.put("Description", "this is diy group delete message!");
        obj.put("ReceiveUser", "");
        obj.put("SendUser", "");
        JSONObject dict = new JSONObject();
//        dict.put("Name", "DiyGroupTestUpdate");
//        dict.put("userId", "9f90fffc6bd44b3abcea1549a194a01a");
        dict.put("groupId", "e20efc7675ee43d4ac9daa2ccb5c762d");
        obj.put("Dict", dict);
        obj.put("Operate", "DELETE");
        client1.send(obj.toString());
        System.out.println("====end====");
    }

    @Test
    public void testOfflineMsg() {
        JSONObject obj = new JSONObject();
        obj.put("MessageType", "mtOffLineMsg");
        obj.put("Code", "");
        obj.put("Description", "this is about offline message");
        obj.put("ReceiveUser", "");
        obj.put("SendUser", "");
        JSONObject dict = new JSONObject();
        obj.put("Dict", dict);
        obj.put("Operate", "");
        client1.send(obj.toString());
        System.out.println("====end====");
    }

    public static void main(String[] args) throws Exception {
        String urlLogin = "http://localhost:8080/im/api/login";
        JSONObject params1 = new JSONObject();
//        Map<String,Object> params1 = new HashMap<String,Object>();
        params1.put("username","test");
        params1.put("password","123456");
        HttpResponse response1 = HttpRequest.post(urlLogin).body(params1.toString()).header("Content-Type","application/json").timeout(-1).execute();
        String ret = response1.body();
        String token1 = response1.header("Authorization");
        String urlGetWs = "http://localhost:8080/im/api/ws/server";
        if (StringUtils.isNotBlank(token1)) {
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("version", "1.0");
            response1 = HttpRequest
                    .get(urlGetWs).header("Authorization", token1).form(params).timeout(-1).execute();
            JSONObject obj1 = JSON.parseObject(response1.body());
            String wsUrl1 = obj1.getString("data");
            URI uri1 = null;
            try {
                uri1 = new URI(wsUrl1);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            client1 = new IMWebSocketClient(uri1, null);
            client1.connect();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        JSONObject obj = new JSONObject();
        obj.put("MessageType", "mtCommonContact");
        obj.put("Code", "");
        obj.put("Description", "hello 你好啊");
        obj.put("ReceiveUser", "9f90fffc6bd44b3abcea1549a194a01a");
        obj.put("SendUser", "2b3ad4abf51d43b1a01715ab724682f6");
        JSONObject dict = new JSONObject();
        dict.put("msgToApp", "");
        dict.put("msgType", "");
        obj.put("Dict", dict);
        obj.put("Operate", "");
        client1.send(obj.toString());
        System.out.println("ss");
    }
}
