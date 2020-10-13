package com.css.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 异常基类
 */
public class BaseException extends RuntimeException implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(BaseException.class);

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);

        if (log.isErrorEnabled()) {
            log.error(message);
        }
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        if (log.isErrorEnabled()) {
            log.error(message, cause);
        }
    }
}
