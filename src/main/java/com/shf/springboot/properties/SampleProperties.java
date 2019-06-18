package com.shf.springboot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/6/17 17:22
 */
@Data
public class SampleProperties {
    /**
     * Sample host.
     */
    @NotEmpty
    private String host;

    /**
     * Sample port.
     */
    private Integer port;

    /**
     * Nested properties will also be validated when bound,
     * it’s good practice to also annotate the associated field as @Valid.
     */
    @Valid
    @NestedConfigurationProperty
    private final Security security = new Security();

    @Data
    public static class Security {

        @NotEmpty
        public String username;

    }
}
