package com.shf.springboot.conversion;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.Base64Utils;

import java.nio.charset.Charset;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/15 22:09
 */
public class IdConversionService implements ConversionService {
    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return false;
    }

    /**
     * Three points:
     * - Annotation with {@link IdDecoder}
     * - require is true
     * - sourceType is {@link String}
     *
     * @param sourceType sourceType
     * @param targetType sourceType
     * @return
     */
    @Override
    public boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
        IdDecoder idDecoder = targetType.getAnnotation(IdDecoder.class);
        return null != idDecoder && idDecoder.required() && sourceType.getObjectType() == String.class;
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        return null;
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
        if (null == source) {
            return null;
        }
        //decoder input by base64
        return new String(Base64Utils.decodeFromString(source.toString()), Charset.defaultCharset());
    }
}
