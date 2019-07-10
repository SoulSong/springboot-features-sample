package com.shf.springboot.event.sync;

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
 * @Date: 2019/7/12 16:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncEventPayload extends BaseEventPayload {
    private int index;
}
