package com.css.im.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Create by wx on 2020-09-03
 */
@Component
public class Audience {

    @Value("${jwt.clientId}")
    private String clientId;

    @Value("${jwt.base64Secret}")
    private String base64Secret;

    @Value("${jwt.name}")
    private String name;
    /**
     * 过期时间毫秒
     */
    @Value("${jwt.expiration}")
    private int expiresSecond;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBase64Secret() {
        return base64Secret;
    }

    public void setBase64Secret(String base64Secret) {
        this.base64Secret = base64Secret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExpiresSecond() {
        return expiresSecond;
    }

    public void setExpiresSecond(int expiresSecond) {
        this.expiresSecond = expiresSecond;
    }
}
