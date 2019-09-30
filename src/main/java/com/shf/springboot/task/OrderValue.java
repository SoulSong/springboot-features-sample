package com.shf.springboot.task;

import static org.springframework.boot.web.servlet.filter.OrderedFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER;

/**
 * Description:
 *
 * @author: songhaifeng
 * @date: 2019/10/8 16:43
 */
public interface OrderValue {
    int MDC_FILTER_ORDER = REQUEST_WRAPPER_FILTER_MAX_ORDER - 100;
    // Order defaults to after ordered request context filter
    int REQUEST_ATTRIBUTE_FILTER_ORDER = REQUEST_WRAPPER_FILTER_MAX_ORDER - 104;
}
