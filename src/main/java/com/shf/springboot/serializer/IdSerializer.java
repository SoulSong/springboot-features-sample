package com.shf.springboot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Description:
 * Encode original value to base64 string value
 *
 * @Author: songhaifeng
 * @Date: 2019/6/16 00:29
 */
public class IdSerializer extends JsonSerializer {

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (null == o) {
            return;
        }
        jsonGenerator.writeString(Base64Utils.encodeToString(o.toString().getBytes(Charset.defaultCharset())));
    }
}




