package com.shf.springboot.conversion;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Collections;
import java.util.Set;

/**
 * Description:
 * A converter for string
 *
 * @Author: songhaifeng
 * @Date: 2019/6/15 23:16
 */
public final class IdConverter implements ConditionalGenericConverter {

    private ConversionService idConversionService;

    public IdConverter(ConversionService idConversionService) {
        this.idConversionService = idConversionService;
    }

    /**
     * Judge the logic with {@link IdConversionService}
     *
     * @param sourceType sourceType
     * @param targetType ta
     * @return true:match; false:not match
     */
    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return idConversionService.canConvert(sourceType, targetType);
    }

    /**
     * Only support convert from String to String
     *
     * @return a set of ConvertiblePair
     */
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, String.class));
    }

    /**
     * base64 decoder
     *
     * @param source     source string
     * @param sourceType source type
     * @param targetType target type
     * @return string
     */
    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return idConversionService.convert(source, sourceType, targetType);
    }
}


