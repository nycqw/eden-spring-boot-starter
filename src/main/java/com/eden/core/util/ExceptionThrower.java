package com.eden.core.util;

import com.eden.core.exception.BizException;

/**
 * @author chenqw
 * @version 1.0
 * @since 2020/6/14
 */
public class ExceptionThrower {

    public static void export(Enum message) {
        throw new BizException(AopUtil.parseCode(message), AopUtil.parseMessage(message));
    }
}
