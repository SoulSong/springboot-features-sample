package com.shf.springboot.resttemplate;

import org.springframework.validation.annotation.Validated;

import java.util.concurrent.TimeUnit;

import lombok.Data;

/**
 * Description:
 *
 * @author: songhaifeng
 * @date: 2019/9/30 13:59
 */
@Validated
@Data
public class RestTemplateProperties {

    /**
     * Assigns maximum total connection value.
     * {@link org.apache.http.impl.client.HttpClientBuilder}
     */
    private int maxConnTotal = 10;

    /**
     * Assigns maximum connection per route value.
     * {@link org.apache.http.impl.client.HttpClientBuilder}
     */
    private int maxConnPerRoute = 5;

    /**
     * Set the connection timeout for the underlying HttpClient.
     * A timeout value of 0 specifies an infinite timeout.
     * {@link org.springframework.http.client.HttpComponentsClientHttpRequestFactory}
     */
    private int connectTimeout = 5000;

    /**
     * Set the socket read timeout for the underlying HttpClient.
     * A timeout value of 0 specifies an infinite timeout.
     * {@link org.springframework.http.client.HttpComponentsClientHttpRequestFactory}
     */
    private int readTimeout = 10000;

    /**
     * Set the timeout in milliseconds used when requesting a connection from the connection
     * manager using the underlying HttpClient.
     * A timeout value of 0 specifies an infinite timeout.
     * {@link org.springframework.http.client.HttpComponentsClientHttpRequestFactory}
     */
    private int connRequestTimeout = 2000;

    /**
     * the number of times a method will be retried
     * {@link org.apache.http.impl.client.DefaultHttpRequestRetryHandler}
     */
    private int retryCount = 3;

    /**
     * Whether or not methods that have successfully sent their request will be retried
     * {@link org.apache.http.impl.client.DefaultHttpRequestRetryHandler}
     */
    private boolean requestSentRetryEnabled = false;

    /**
     * ref https://hc.apache.org/httpcomponents-client-4.5.x/tutorial/html/connmgmt.html#d5e418
     */
    private boolean evictExpiredConnections = false;

    private long maxIdleTime = 10L;

    private TimeUnit maxIdleTimeUnit = TimeUnit.SECONDS;

}
