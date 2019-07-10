package com.shf.springboot.event.order;

import com.shf.springboot.event.BaseEventPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/7/12 14:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventPayload extends BaseEventPayload {
    private String message;
}
