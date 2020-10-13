package com.css.im.chat.web;

import com.css.common.BaseController;
import com.css.common.ReqResult;
import com.css.im.auth.SecurityFacade;
import com.css.im.chat.ChatStatus;
import com.css.im.chat.service.IMChatService;
import com.css.im.chat.service.IMMsgService;
import com.css.im.mbg.model.MessagePackage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Create by wx on 2020-09-10
 */
@Api(tags = "聊天信息相关接口")
@RestController
@RequestMapping("/im/api/chat/message")
public class IMChatController extends BaseController {
    @Autowired
    IMMsgService imMsgService;
    @Autowired
    IMChatService imChatService;

    @ApiOperation(value = "发送消息")
    @PostMapping(value = "/send")
    @ResponseBody
    public ReqResult send(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String groupId = get(request, "groupId");  //聊天组
        String message = get(request, "message");  //消息


        return ReqResult.failed();
    }

    @ApiOperation(value = "消息状态")
    @PostMapping(value = "/status")
    @ResponseBody
    public ReqResult status(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String messageId = get(request, "message_id");  //消息id


        return ReqResult.failed();
    }



    @ApiOperation(value = "消息撤回")
    @PostMapping(value = "/reback")
    @ResponseBody
    public ReqResult reback(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String messageId = get(request, "message_id");  //消息id


        return ReqResult.failed();
    }

}
