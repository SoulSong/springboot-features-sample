package com.shf.springboot.validator;

import com.shf.springboot.entity.User;
import com.shf.springboot.exception.entity.FieldValidationFailure;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class ValidatorUtilsTest  {

    @Test
    public void validateEntity() {
        Map<String, FieldValidationFailure> result = ValidatorUtils.validateEntity(User.builder().age(13).email("aa.com").build());
        Assert.assertEquals(result.size(), 2);
    }

    @Test
    public void validateProperty() {
        Map<String, FieldValidationFailure> result = ValidatorUtils.validateProperty(User.builder().age(13).email("aa.com").build(),"email");
        Assert.assertEquals(result.size(), 1);
    }
}