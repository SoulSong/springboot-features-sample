package com.shf.springboot.i18n;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

/**
 * @author songhaifeng
 */
@Slf4j
public class MessageHelper {
    private static MessageSource messageSource;

    private MessageHelper() {
        // make constructor private
    }

    static void setMessageSource(MessageSource messageSource) {
        MessageHelper.messageSource = messageSource;
    }

    public static String getMessage(String code, Object[] args, String defaultMessage) {
        if (messageSource == null) {
            log.debug("Message source not found, i18n translation ignored.");
            return StringUtils.isEmpty(defaultMessage) ? code : defaultMessage;
        } else {
            return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
        }
    }
}
