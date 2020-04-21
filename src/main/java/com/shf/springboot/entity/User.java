package com.shf.springboot.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shf.springboot.i18n.MessageHelper;
import com.shf.springboot.serializer.IdDeserializer;
import com.shf.springboot.serializer.IdSerializer;
import com.shf.springboot.validator.BaseEntityValid;
import com.shf.springboot.validator.EmailValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/15 16:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@BaseEntityValid
@Builder
public class User extends BaseEntity {
    @NotNull
    private String id;
    private String username;
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(using = IdDeserializer.class)
    private String password;
    private Integer age;
    private Double weight;
    @EmailValid
    private String email;

    @Override
    public boolean validate(ConstraintValidatorContext context) {
        // Valid age and weight
        if (null == age && null == weight) {
            violation(context, MessageHelper.getMessage("both.NULL.message",new Object[]{"age","weight"},"Age and weight cannot be null at the same time"));
            return false;
        }
        return super.validate(context);
    }
}
