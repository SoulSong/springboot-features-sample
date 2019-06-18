package com.shf.springboot.exception.entity;

import com.shf.springboot.exception.UnknownException;
import lombok.Data;

import java.util.UUID;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/29 00:50
 */
@Data
public class UnknownErrorEntity extends ErrorEntity {
    private String trackingId;

    public UnknownErrorEntity(String errMessage) {
        super(errMessage);
        this.trackingId = UUID.randomUUID().toString();
    }

    public UnknownErrorEntity(UnknownException exception) {
        super(exception);
        this.setTrackingId(exception.getTrackingId());
    }

}