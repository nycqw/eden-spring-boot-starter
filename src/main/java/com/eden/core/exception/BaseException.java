package com.eden.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenqw
 * @version 1.0
 * @since 2020/5/24
 */
public class BaseException extends RuntimeException {

    @Getter
    @Setter
    private int code;

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }
}
