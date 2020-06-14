package com.eden.core.config;

import com.eden.core.result.ResultControllerAdvice;
import com.eden.core.lock.LockAspect;
import com.eden.core.lock.RedisLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author chenqw
 * @version 1.0
 * @since 2020/5/24
 */
@Configuration
@ConditionalOnWebApplication
public class EdenAutoConfiguration {

    @Bean
    public ResultControllerAdvice resultControllerAdvice() {
        return new ResultControllerAdvice();
    }

    @Bean
    @ConditionalOnBean(value = RedisTemplate.class)
    public RedisLock redisLock() {
        return new RedisLock();
    }

    @Bean
    @ConditionalOnBean(value = RedisLock.class)
    public LockAspect lockAspect() {
        return new LockAspect();
    }

}
