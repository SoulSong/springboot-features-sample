package com.shf.springboot.resttemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 * Add a response-error-handler sample for rest-template.
 *
 * @author: songhaifeng
 * @date: 2019/9/30 11:21
 */
@Slf4j
public class LoggingResponseErrorHandler extends DefaultResponseErrorHandler {
    private static final String APPLICATION_LOG_FORMAT = "{} :: {} :: {} :: {}";
    private static final String PLACE_HOLDER = "-";
    private static final String UNKNOWN = "[unknown]";

    private ObjectMapper objectMapper;
    private TypeReference<Map<String, Object>> typeRef;

    public LoggingResponseErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.typeRef = new TypeReference<Map<String, Object>>() {
        };
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        try {
            final byte[] rspArr = getResponseBody(response);
            if (ArrayUtils.isNotEmpty(rspArr)) {
                String bizErrorCode = StringUtils.EMPTY;
                String errorMessage = StringUtils.EMPTY;

                final Map<String, Object> errMap = objectMapper.readValue(rspArr, typeRef);
                for (Map.Entry<String, Object> entry : errMap.entrySet()) {
                    String key = entry.getKey().toLowerCase(Locale.ENGLISH);
                    if (key.startsWith("biz")) {
                        bizErrorCode = StringUtils.defaultIfBlank(entry.getValue().toString(), PLACE_HOLDER);
                    } else if (key.startsWith("error") && key.endsWith("message")) {
                        errorMessage = StringUtils.defaultIfBlank(entry.getValue().toString(), PLACE_HOLDER);
                    }
                }

                final HttpStatus statusCode = super.getHttpStatusCode(response);
                if (statusCode.is5xxServerError()) {
                    log.error(APPLICATION_LOG_FORMAT, bizErrorCode, PLACE_HOLDER, PLACE_HOLDER, errorMessage);
                } else {
                    log.warn(APPLICATION_LOG_FORMAT, bizErrorCode, PLACE_HOLDER, PLACE_HOLDER, errorMessage);
                }
            }
        } catch (UnrecognizedPropertyException upe) {
            log.error(APPLICATION_LOG_FORMAT, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN);
            log.error("resolve ErrorEntity failed", upe);
        }

        super.handleError(response);
    }

}
