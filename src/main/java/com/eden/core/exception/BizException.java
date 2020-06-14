package com.eden.core.exception;

/**
 * @author chenqw
 * @version 1.0
 * @since 2020/5/24
 */
public class BizException extends BaseException {

    public BizException(int code, String message) {
        super(code, message);
    }
}
