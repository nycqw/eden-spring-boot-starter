package com.eden.core.result;

import com.eden.core.exception.BaseException;
import com.eden.core.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @author chenqw
 * @version 1.0
 * @since 2020/5/24
 */
@Slf4j
@ControllerAdvice
public class ResultControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (body instanceof Result) {
            return body;
        }
        return Result.success(body);
    }

    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public Result handleBusinessException(BizException e) {
        log.error(e.getMessage(), e);
        return Result.failure(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public Result handleBaseException(BaseException e) {
        log.error(e.getMessage(), e);
        return Result.failure(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.failure(ResultEnum.UNKNOWN_ERROR);
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Result handleBindException(BindException e) {
        log.error("参数绑定校验异常", e);
        return wrapperBindingResult(e.getBindingResult());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Result handleValidException(ConstraintViolationException e) {
        log.error("参数错误", e);

        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder message = new StringBuilder();
        constraintViolations.forEach(constraintViolation -> message.append(constraintViolation.getPropertyPath()).append(constraintViolation.getMessage()).append(","));
        return Result.failure(ResultEnum.PARAM_ERROR.getCode(), message.substring(0, message.length() - 1));
    }

    private Result wrapperBindingResult(BindingResult bindingResult) {
        StringBuilder msg = new StringBuilder();

        for (ObjectError error : bindingResult.getAllErrors()) {
            msg.append(", ");
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(": ");
            }
            error.getDefaultMessage();
            msg.append(error.getDefaultMessage());
        }

        return Result.failure(ResultEnum.PARAM_IS_INVALID.getCode(), msg.substring(2));
    }
}
