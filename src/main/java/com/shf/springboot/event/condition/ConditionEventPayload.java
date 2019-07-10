package com.shf.springboot.event.condition;

import com.shf.springboot.event.BaseEventPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/7/12 14:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConditionEventPayload extends BaseEventPayload {
    private String message;
    @NotNull
    private String header;
}
