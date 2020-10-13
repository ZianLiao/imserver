package com.css.common.exception;

import com.css.common.ReqResult;


/**
 * 业务处理异常
 * 指用户输入了非法数据等业务逻辑存在的异常
 */
public class BusinessException extends BaseException {
    private ReqResult result = ReqResult.failed();

    public BusinessException(ReqResult result) {
        super(result.getCode() + ":" + result.getMessage());
        this.result = result;
    }

    public BusinessException(long code, String msg) {
        super(code + ":" + msg);
        this.result.setCode(code);
        this.result.setMessage(msg);
    }

    public BusinessException(ReqResult result, Throwable cause) {
        super(result.getCode() + ":" + result.getMessage(), cause);
        this.result = result;
    }

    public BusinessException(long code, String msg, Throwable cause) {
        super(code + ":" + msg, cause);
        this.result.setCode(code);
        this.result.setMessage(msg);
    }

    public ReqResult getResult() {
        return result;
    }

    public void setResult(ReqResult result) {
        this.result = result;
    }
}
