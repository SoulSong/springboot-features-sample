package com.shf.springboot.exception;

import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/28 17:38
 */
public abstract class AbstractException extends RuntimeException {
    private final String message;
    private final String messageTemplate;
    private final List<String> messageVars = new ArrayList<>(0);

    protected AbstractException() {
        super();

        this.message = "";
        this.messageTemplate = "";
    }

    protected AbstractException(Exception e) {
        super(e);

        this.message = super.getMessage();
        this.messageTemplate = "";
    }


    protected AbstractException(String pattern, Object... args) {
        super();

        this.messageTemplate = pattern;
        this.message = MessageFormatter.arrayFormat(pattern, args).getMessage();
        this.populateMessageVars(args);
    }


    protected AbstractException(Exception e, String pattern, Object... args) {
        super(e);

        this.messageTemplate = pattern;
        this.message = MessageFormatter.arrayFormat(pattern, args).getMessage();
        this.populateMessageVars(args);
    }

    protected final void populateMessageVars(Object... args) {
        if (args.length > 0) {
            for (Object arg : args) {
                messageVars.add(MessageFormatter.format("{}", arg).getMessage());
            }
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public List<String> getMessageVars() {
        return messageVars;
    }
}
