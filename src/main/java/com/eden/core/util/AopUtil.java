package com.eden.core.util;

import com.eden.core.result.ResultEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 切面工具类
 *
 * @author chenqw
 * @since 2018/11/30
 */
public class AopUtil extends AopUtils {

    /**
     * 获取切入方法所在类名称
     *
     * @param joinPoint 切入点对象
     * @return
     */
    public static String getClassName(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().getName();
    }

    /**
     * 获取切入方法
     *
     * @param point 切入点对象
     * @return
     * @throws NoSuchMethodException
     */
    public static Method getTargetMethod(JoinPoint point) throws NoSuchMethodException {
        Signature signature = point.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        // 目标类对象
        Object target = point.getTarget();
        return target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }


    public static int parseCode(Object obj) {
        int code = ResultEnum.UNKNOWN_ERROR.getCode();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            try {
                if (field.getType().getSimpleName().equals("int")
                        || field.getType().getSimpleName().equals("Integer")) {
                    field.setAccessible(true);
                    code = field.getInt(obj);
                    break;
                }
            } catch (IllegalAccessException ignore) {
            }
        }
        return code;
    }

    public static String parseMessage(Object obj) {
        String message = "";
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            try {
                if (field.getType().getSimpleName().equals("String")) {
                    field.setAccessible(true);
                    message = (String) field.get(obj);
                    break;
                }
            } catch (IllegalAccessException ignore) {
            }
        }
        return message;
    }


}
