package com.css.common.exception;

import com.css.common.ResultCode;

/**
 * Create by wx on 2020-09-03
 */

public class IMException extends BaseException {

    private ResultCode code;

    public IMException(ResultCode code) {
        this.code = code;
    }

}
