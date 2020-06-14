package com.eden.core.lock;

import com.eden.core.lock.annotation.Lock;
import com.eden.core.util.AopUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author chenqw
 * @version 1.0
 * @since 2018/12/1
 */
@Aspect
@Slf4j
public class LockAspect {

    @Autowired
    private RedisLock redisLock;

    @Pointcut(value = "@annotation(com.eden.core.lock.annotation.Lock)")
    public void lockCut() {
    }

    @Around(value = "lockCut()")
    public Object interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        Method targetMethod = AopUtil.getTargetMethod(joinPoint);
        Lock lock = targetMethod.getAnnotation(Lock.class);
        return handleRedisDistributedLock(joinPoint, lock);
    }

    /**
     * 基于redis的分布式锁
     */
    private Object handleRedisDistributedLock(ProceedingJoinPoint joinPoint, Lock lock) throws Throwable {
        String lockName = getLockName(joinPoint);
        String identifier = UUID.randomUUID().toString();
        boolean acquire;
        if (lock.timeout() == -1L) {
            acquire = redisLock.lock(lockName, identifier, lock.expire());
        } else {
            acquire = redisLock.lockWithTimeout(lockName, identifier, lock.expire(), lock.timeout());
        }
        if (acquire) {
            try {
                return joinPoint.proceed();
            } finally {
                redisLock.unLock(lockName, identifier);
            }
        }
        log.warn("Get lock fail => {}", Thread.currentThread().getName());
        return null;
    }

    private String getLockName(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Method targetMethod = AopUtil.getTargetMethod(joinPoint);
        return AopUtil.getClassName(joinPoint) + "#" + targetMethod.getName();
    }
}
