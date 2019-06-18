package com.shf.springboot.exception.advice;

import com.shf.springboot.exception.ApplicationException;
import com.shf.springboot.exception.entity.ErrorEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandles;
import java.util.Optional;
import java.util.logging.Level;

/**
 *
 * @author songhaifeng
 */
public interface ExceptionAdviceTrait {

    Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * check is Supported type
     *
     * @param error error
     * @return isSupported
     */
    boolean isSupported(Throwable error);

    /**
     * handle ex
     *
     * @param request   request
     * @param response  response
     * @param exception exception
     * @return error entity
     */
    Object handle(final HttpServletRequest request, final HttpServletResponse response, final Throwable exception);

    /**
     * Handler exception with default implementation
     *
     * @param request  http servlet request
     * @param response http servlet response
     * @param entity   response with error entity
     * @return response
     */
    default Object handle(final HttpServletRequest request, final HttpServletResponse response, final ResponseEntity<ErrorEntity> entity) {
        final String acceptValue = request.getHeader(HttpHeaders.ACCEPT);
        //  error html
        if (StringUtils.isNotBlank(acceptValue) && acceptValue.contains(MediaType.TEXT_HTML_VALUE)) {
            // set response http error status code
            response.setStatus(entity.getStatusCodeValue());

            // return error page response
            ModelAndView model = new ModelAndView("error");

            // request uri
            String path = (String) request.getAttribute("javax.servlet.error.request_uri");
            if (path != null) {
                model.addObject("path", path);
            }

            model.addObject("status", entity.getStatusCodeValue());
            model.addObject("error", entity.getBody());
            model.addObject("message", null != entity.getBody() ? entity.getBody().getErrorMessage() : ApplicationException.DEFAULT_APPLICATION_EXCEPTION_ERROR_MESSAGE);
            model.addObject("timestamp", entity.getBody().getTimestamp());
            return model;
        } else {
            // return error entity response
            return entity;
        }
    }

    /**
     * log for ex
     *
     * @param level     log level for exception
     * @param exception exception to be handle
     */
    default void log(final Level level, final Exception exception) {
        Optional.ofNullable(exception).map(e -> {
            if (Level.SEVERE == level) {
                LOGGER.error("Met error exception", e);
            } else if (Level.WARNING == level || Level.FINEST == level) {
                // treat FINEST as warn for better exp tracing
                LOGGER.warn("Met warning exception: {}", ExceptionUtils.getRootCauseMessage(e));
            } else if (Level.INFO == level) {
                LOGGER.info("Met info exception", e);
            } else {
                LOGGER.debug("Met debug exception", e);
            }
            return e;
        });
    }
}
