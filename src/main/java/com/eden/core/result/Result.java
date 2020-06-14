package com.eden.core.result;

import com.eden.core.util.AopUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author chenqw
 * @version 1.0
 * @since 2020/5/24
 */
@Getter
@Setter
public class Result implements Serializable {

    private Integer code;
    private String message;
    private Object data;

    private Result() {
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Enum enu, Object data) {
        this.code = AopUtil.parseCode(enu);
        this.message = AopUtil.parseMessage(enu);
        this.data = data;
    }

    public static Result success() {
        return new Result(ResultEnum.SUCCESS, null);
    }

    public static Result success(Object data) {
        return new Result(ResultEnum.SUCCESS, data);
    }

    public static Result failure(Enum enu) {
        return new Result(enu, null);
    }

    public static Result failure(Enum enu, Object data) {
        return new Result(enu, data);
    }

    public static Result failure(int code, String message) {
        return new Result(code, message);
    }

}
