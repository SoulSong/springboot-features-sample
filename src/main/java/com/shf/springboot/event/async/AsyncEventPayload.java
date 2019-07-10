package com.shf.springboot.event.async;

import com.shf.springboot.event.BaseEventPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Description:
 * The subject of async-event definition.
 *
 * @Author: songhaifeng
 * @Date: 2019/7/12 20:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsyncEventPayload extends BaseEventPayload {
    private int index;
}
