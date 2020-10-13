package com.css.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 通用返回对象
 * Created by macro on 2019/4/19.
 */
public class ReqResult<T> implements Serializable {
    protected CodeResult codeResult;

    protected ReqResult() {
    }

    protected ReqResult(long code, String message) {
        this.codeResult = new CodeResult(code, message);
    }

    protected ReqResult(ResultCode resultCode) {
        this.codeResult = new CodeResult(resultCode.getCode(), resultCode.getMessage());
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ReqResult success(T data) {
        if (null == data) {
            return new ReqResult(ResultCode.SUCCESS);
        }
        return new ReqDataResult<T>(ResultCode.SUCCESS, data);
    }

    public static ReqResult success() {
        return new ReqResult(ResultCode.SUCCESS);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> ReqResult<T> success(T data, String message) {
        return new ReqDataResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> ReqResult<T> failed(IErrorCode errorCode) {
        return new ReqResult<T>(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static <T> ReqResult<T> failed(IErrorCode errorCode, String message) {
        return new ReqResult<T>(errorCode.getCode(), message);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> ReqResult<T> failed(String message) {
        return new ReqResult<T>(ResultCode.FAILED.getCode(), message);
    }

    /**
     * 失败返回结果
     */
    public static <T> ReqResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> ReqResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> ReqResult<T> validateFailed(String message) {
        return new ReqResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message);
    }

    /**
     * 未登录返回结果
     */
    public static <T> ReqResult<T> unauthorized(T data) {
        return new ReqDataResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> ReqResult<T> forbidden(T data) {
        return new ReqDataResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    public long getCode() {
        return this.codeResult.code;
    }

    public void setCode(long code) {
        this.codeResult.setCode(code);
    }

    public String getMessage() {
        return this.codeResult.getMessage();
    }

    public void setMessage(String message) {
        this.codeResult.setMessage(message);
    }

    @ApiModel
    public static class CodeResult implements Serializable {
        @ApiModelProperty
        private long code;
        @ApiModelProperty
        private String message;

        protected CodeResult(long code, String message) {
            this.code = code;
            this.message = message;
        }

        protected CodeResult(ResultCode result) {
            this.code = result.getCode();
            this.message = result.getMessage();
        }

        public long getCode() {
            return code;
        }

        public void setCode(long code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
