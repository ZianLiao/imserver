package com.css.common;

/**
 * API返回错误码
 * Create by wx on 2020-09-03
 */
public enum ResultCode implements IErrorCode {

    SUCCESS(1, "操作成功"),
    FAILED(0, "操作失败"),
    REQ_SUCCESS(200, "请求成功"),
    SERVER_FAILED(500, "服务端异常"),
    RESOURCE_NONE_EXIST(404, "请求资源不存在"),
    VALIDATE_FAILED(501, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    PERMISSION_TOKEN_EXPIRED(10001, "登录凭证过期"),
    PERMISSION_TOKEN_INVALID(10002, "暂未登录或token已经过期"),
    PERMISSION_SIGNATURE_ERROR(10003, "没有相关权限"),

    LOGIN_ERROR(20001, "账户名或者密码错误!");


    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
