package com.eden.core.util;

import com.eden.core.exception.BizException;

import java.util.Collection;

/**
 * @author chenqw
 * @version 1.0
 * @since 2020/5/24
 */
public abstract class AssertUtil {

    public static void notNull(Object object, Enum enu) {
        if (object == null) {
            throw new BizException(AopUtil.parseCode(enu), AopUtil.parseMessage(enu));
        }
    }

    public static void notBlank(String object, Enum enu) {
        if (object == null || object.trim().length() == 0) {
            throw new BizException(AopUtil.parseCode(enu), AopUtil.parseMessage(enu));
        }
    }

    public static void notEmpty(Collection<Object> list, Enum enu) {
        if (list == null || list.size() == 0) {
            throw new BizException(AopUtil.parseCode(enu), AopUtil.parseMessage(enu));
        }
    }

}
