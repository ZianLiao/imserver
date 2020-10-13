package com.css.common.exception;

import com.css.common.ReqResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常捕获处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务逻辑异常。
     * HTTP响应状态为200
     *
     * @param businessException
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity businessExceptionHandler(BusinessException businessException) {
        ReqResult result = businessException.getResult();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * 系统异常。
     * HTTP响应状态为400
     *
     * @param systemException
     * @return
     */
    @ExceptionHandler(value = SystemException.class)
    public ResponseEntity systemExceptionHandler(SystemException systemException) {
        ReqResult result = systemException.getResult();
        return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * IM异常。
     * HTTP响应状态为200
     *
     * @param systemException
     * @return
     */
    @ExceptionHandler(value = IMException.class)
    public ResponseEntity imExceptionHandler(IMException systemException) {
        String result = systemException.getMessage();
        return new ResponseEntity(result, HttpStatus.OK);
    }
}