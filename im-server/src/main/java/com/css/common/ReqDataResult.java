package com.css.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 通用返回对象
 * Created by macro on 2019/4/19.
 */
@ApiModel
public class ReqDataResult<T> extends ReqResult<T> {

    @ApiModelProperty
    protected T data;

    public ReqDataResult(long code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public ReqDataResult(ResultCode code, T data) {
        super(code);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
