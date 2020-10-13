package com.css.common.exception;


import com.css.common.ReqResult;

/**
 * 系统级异常。
 * 指系统级别的，如：网络通信时连接中断、系统连接、超时等异常
 */
public class SystemException extends BaseException {
    private ReqResult result = ReqResult.failed();

    public SystemException(ReqResult result) {
        super(result.getCode() + ":" + result.getMessage());
        this.result = result;
    }

    public SystemException(long code, String msg) {
        super(code + ":" + msg);
        this.result.setCode(code);
        this.result.setMessage(msg);
    }

    public SystemException(ReqResult result, Throwable cause) {
        super(result.getCode() + ":" + result.getMessage(), cause);
        this.result = result;
    }

    public SystemException(long code, String message, Throwable cause) {
        super(code + ":" + message, cause);
        this.result.setCode(code);
        this.result.setMessage(message);
    }

    public ReqResult getResult() {
        return result;
    }

    public void setResult(ReqResult result) {
        this.result = result;
    }
}
