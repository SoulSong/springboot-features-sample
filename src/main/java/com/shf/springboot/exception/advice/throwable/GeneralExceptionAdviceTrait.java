package com.shf.springboot.exception.advice.throwable;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Description:
 * Set the lowest priority
 *
 * @Author: songhaifeng
 * @Date: 2019/6/29 00:59
 */
@Order(Ordered.LOWEST_PRECEDENCE)
public interface GeneralExceptionAdviceTrait extends ThrowableAdviceTrait {
}
