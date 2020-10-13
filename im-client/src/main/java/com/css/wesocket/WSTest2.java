package com.css.wesocket;

import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

/**
 * Create by wx on 2020-09-07
 */
public class WSTest2 {
    private static Logger logger = LoggerFactory.getLogger(WSTest2.class);

    @Test
    public void test() {
        String url = "http://10.13.250.90:8080/oa/api/notify";
        JSONObject notifyJson = new JSONObject();
        JSONObject params = new JSONObject();
        List<String> targetUserList = new ArrayList<>();
        targetUserList.add("ls");
        params.put("title", "来自应用的消息");
        params.put("url", "http://www.baidu.com");
        params.put("targetUser", targetUserList);
        params.put("srcIp", "isLoginName:10.13.1.195");
        params.put("fromAppName", "电子公文系统");
        params.put("sendUser", "zs");
        params.put("content", "您被选为会议参会人，会议名称: test");
        notifyJson.put("notifyStr", params);
        HttpResponse response = HttpRequest.post(url).body(notifyJson.toString()).header("Content-Type","application/json").timeout(-1).execute();
        String ret = response.body();
    }
    public static void main(String[] args) throws Exception{


        String urlLogin = "http://10.13.250.90:8080/im/api/login";
        JSONObject params = new JSONObject();
        params.put("username","ls");
        params.put("password","123456");
        HttpResponse response = HttpRequest.post(urlLogin).body(params.toString()).header("Content-Type","application/json").timeout(-1).execute();
        String ret = response.body();
        String token = response.header("Authorization");

        String urlGetWs = "http://10.13.250.90:8080/im/api/ws/server";
        if(StringUtils.isNotBlank(token)){
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("version","1.0");
            response = HttpRequest
                    .get(urlGetWs).header("Authorization",token)
                    .form(paramMap).timeout(-1).execute();
            System.out.println(response.body());
            JSONObject obj = JSONObject.fromObject(response.body());

            System.out.println(obj.toString());
            String wsUrl = obj.getString("data");
            URI uri = new URI(wsUrl);
            IMWebSocketClient client = new IMWebSocketClient(uri,new MessageHandler(){

                @Override
                public void handleMessage(String message) {
                    logger.info("接收到来自服务端消息: " + message);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            client.connect();
//{"Code":"","Description":"","Dict":{},"MessageType":"mtOffLineMsg","Operate":"","ReceiveUser":"","SendUser":""}
            Thread.sleep(5000);
//            obj = new JSONObject();
//            obj.put("Description","111");
//            obj.put("MessageType","mtPingServer");
//            client.send(obj.toString());
            obj = new JSONObject();
            JSONObject dict = new JSONObject();
            obj.put("Description","");
            obj.put("MessageType","mtOffLineMsg");
            obj.put("Code", "");
            obj.put("Operate", "");
            obj.put("ReceiverUser", "");
            obj.put("SendUser", "");
            obj.put("Dict", dict);
            client.send(obj.toString());

            System.out.println("ss");
        }



    }

}
