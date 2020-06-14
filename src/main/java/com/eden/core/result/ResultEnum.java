package com.eden.core.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chenqw
 * @version 1.0
 * @since 2020/6/14
 */
@AllArgsConstructor
public enum ResultEnum {

    SUCCESS(1, "成功"),

    /* 参数错误：1001-1999 */
    PARAM_ERROR(1001, "参数不正"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_NOT_COMPLETE(1003, "参数缺失"),
    PARAM_IS_INVALID(1004, "参数无效"),

    /* 系统错误：2001-2999 */
    NET_ERROR(2001, "网络异常"),
    READ_TIMEOUT(2002, "连接超时"),
    DATABASE_ERROR(2003, "数据库异常"),
    MACHINEID_NOT_EXIST(2004, "机器ID未配置"),
    DATACENTERID_NOT_EXIST(2005, "数据中心ID未配置"),
    ASSERT_ERROR(2006, "断言异常"),
    UNKNOWN_ERROR(2888, "未知异常"),
    SYSTEM_ERROR(2999, "系统异常");

    @Getter
    private int code;
    @Getter
    private String message;

    public static String valueOf(int code) {
        ResultEnum[] values = ResultEnum.values();
        for (ResultEnum val : values) {
            if (val.getCode() == code) {
                return val.getMessage();
            }
        }
        return "";
    }

}
