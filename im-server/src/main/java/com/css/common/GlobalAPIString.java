package com.css.common;

import com.css.swagger.model.ApiSingleParam;

/**
 * Create by wx on 2020-09-27
 */
public class GlobalAPIString {

    @ApiSingleParam(value = "错误码", example = "0", type = Integer.class)
    public static final String JSON_ERROR_CODE = "code";

    @ApiSingleParam(value = "错误信息", example = "OK")
    public static final String JSON_ERROR_MSG = "message";
}
