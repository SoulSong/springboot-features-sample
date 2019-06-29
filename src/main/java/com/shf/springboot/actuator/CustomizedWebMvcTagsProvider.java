package com.shf.springboot.actuator;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTags;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Description:
 * Add mock_user_id in webmvc_tags provides aggregation with userId.
 *
 * @Author: songhaifeng
 * @Date: 2019/6/30 02:20
 */
public class CustomizedWebMvcTagsProvider implements WebMvcTagsProvider {

    @Override
    public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response, Object handler, Throwable exception) {
        return Tags.of(WebMvcTags.method(request), WebMvcTags.uri(request, response),
                Tag.of("userId", Optional.ofNullable(request.getHeader("userId")).orElse("sample_user")));
    }

    @Override
    public Iterable<Tag> getLongRequestTags(HttpServletRequest request, Object handler) {
        return Tags.of(WebMvcTags.method(request), WebMvcTags.uri(request, null),
                Tag.of("userId", Optional.ofNullable(request.getHeader("userId")).orElse("sample_user")));
    }

}
