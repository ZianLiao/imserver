package com.css.im.websocket.web;

import com.css.common.BaseController;
import com.css.common.ReqResult;
import com.css.im.auth.SecurityFacade;
import com.css.im.chat.service.IMMsgService;
import com.css.im.websocket.service.IMWSServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Create by wx on 2020-09-03
 */
@Api(tags = "获取WebSocket接口服务")
@RestController
@RequestMapping("/im/api/ws")
public class IMWsServerController extends BaseController {

    @Autowired
    IMWSServerService imwsServerService;

    @Autowired
    IMMsgService imMsgService;

    @ApiOperation(value = "获取WebSocket服务地址接口")
    @GetMapping("/server")
    @ResponseBody
    public ReqResult getWsServer(@RequestParam("version") String appVersion) {

        //获取app版本
        String wsServer =  imwsServerService.getWebSocketServerUrl(SecurityFacade.getCurUserId(),appVersion);
        if(StringUtils.isNotBlank(wsServer)){
            String url = wsServer + "?token="+SecurityFacade.getUserProfile().getToken();
            return ReqResult.success(url);
        }

        return ReqResult.failed();
    }


}
