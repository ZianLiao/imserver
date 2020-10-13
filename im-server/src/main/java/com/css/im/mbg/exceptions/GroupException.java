package com.css.im.mbg.exceptions;

public class GroupException extends RuntimeException {


    private static final long serialVersionUID = -9187510744300898227L;

    public GroupException(String errorMsg) {
        super(errorMsg);
    }
}
