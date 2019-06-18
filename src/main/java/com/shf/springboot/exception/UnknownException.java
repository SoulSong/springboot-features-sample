package com.shf.springboot.exception;

import com.shf.springboot.exception.entity.UnknownErrorEntity;
import org.springframework.http.HttpStatus;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/29 00:51
 */
public class UnknownException extends ApplicationException {

    private String trackingId;

    public UnknownException(UnknownErrorEntity errorEntity) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorEntity.getBizErrorCode(), errorEntity.getErrorMessage());
        this.setTrackingId(errorEntity.getTrackingId());
    }

    public String getTrackingId() {
        return this.trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }


}
