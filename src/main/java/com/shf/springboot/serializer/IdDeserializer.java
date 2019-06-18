package com.shf.springboot.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Description:
 * Decode base64 string value to original value
 *
 * @Author: songhaifeng
 * @Date: 2019/6/16 00:58
 */
public class IdDeserializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return new String(Base64Utils.decodeFromString(jsonParser.getText()), Charset.defaultCharset());
    }
}
