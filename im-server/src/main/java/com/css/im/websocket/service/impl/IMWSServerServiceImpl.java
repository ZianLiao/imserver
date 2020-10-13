package com.css.im.websocket.service.impl;

import com.css.common.BaseService;
import com.css.im.websocket.mapper.WsServerMapper;
import com.css.im.websocket.model.WsServer;
import com.css.im.websocket.model.WsServerExample;
import com.css.im.websocket.service.IMWSServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by wx on 2020-09-10
 */
@Service
public class IMWSServerServiceImpl extends BaseService implements IMWSServerService {
    @Value("${ws.server}")
    String localServer;

    @Autowired
    WsServerMapper wsServerMapper;

    @Override
    public String getWebSocketServerUrl(String uid, String appVersion) {

        WsServerExample filter = new WsServerExample();
        filter.createCriteria().andAppVersionsEqualTo(appVersion);
        //same url may have different version
        filter.or().andAppVersionsLike(appVersion + ",");
        filter.or().andAppVersionsLike("," + appVersion);
        // filter.createCriteria().a

        List<WsServer> servers = wsServerMapper.selectByExampleWithBLOBs(filter);
        if (servers.isEmpty()) {
            return null;
        }
        String url = localServer + servers.get(0).getWsUrl() + "/" + servers.get(0).getWsVersion() + "/"
                + uid;
        return url;
    }

}
