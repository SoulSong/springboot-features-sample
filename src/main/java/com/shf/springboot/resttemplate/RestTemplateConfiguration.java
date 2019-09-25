package com.shf.springboot.resttemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description:
 * Customized the rest-template
 *
 * @author: songhaifeng
 * @date: 2019/9/30 10:52
 */
@Configuration
@ConditionalOnClass(RestTemplate.class)
public class RestTemplateConfiguration {

    protected static final String REST_TEMPLATE_BEAN_PREFIX = "com.shf.resttemplate";

    @Autowired(required = false)
    private List<RestTemplate> restTemplateList = Collections.emptyList();

    @Bean
    @ConfigurationProperties(prefix = REST_TEMPLATE_BEAN_PREFIX)
    public RestTemplateProperties restTemplateProperties() {
        return new RestTemplateProperties();
    }

    @Bean
    public DefaultHttpRequestRetryHandler defaultHttpRequestRetryHandler(final RestTemplateProperties restTemplateProperties) {
        return new DefaultHttpRequestRetryHandler(restTemplateProperties.getRetryCount(), restTemplateProperties.isRequestSentRetryEnabled());
    }

    @Bean
    public HttpClientBuilder httpClientBuilder() {
        return HttpClients.custom();
    }

    @Bean
    public HttpClient httpClient(final HttpClientBuilder httpClientBuilder,
                                 final HttpRequestRetryHandler httpRequestRetryHandler,
                                 final RestTemplateProperties restTemplateProperties) {
        httpClientBuilder.setMaxConnTotal(restTemplateProperties.getMaxConnTotal())
                .setMaxConnPerRoute(restTemplateProperties.getMaxConnPerRoute())
                .setRetryHandler(httpRequestRetryHandler);

        if (restTemplateProperties.isEvictExpiredConnections()) {
            httpClientBuilder.evictExpiredConnections()
                    .evictIdleConnections(restTemplateProperties.getMaxIdleTime(), restTemplateProperties.getMaxIdleTimeUnit());
        }

        return httpClientBuilder.build();
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(final RestTemplateProperties restTemplateProperties,
                                                             final HttpClient httpClient) {
        final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory(httpClient);
        clientHttpRequestFactory.setConnectTimeout(restTemplateProperties.getConnectTimeout());
        clientHttpRequestFactory.setReadTimeout(restTemplateProperties.getReadTimeout());
        clientHttpRequestFactory.setConnectionRequestTimeout(restTemplateProperties.getConnRequestTimeout());

        return new BufferingClientHttpRequestFactory(clientHttpRequestFactory);
    }

    @Bean
    public RestTemplate restTemplate(final ObjectProvider<List<RestTemplateCustomizer>> restTemplateCustomizers,
                                     final ClientHttpRequestFactory clientHttpRequestFactory) {
        RestTemplateBuilder builder = new RestTemplateBuilder();

        // RestTemplateCustomizer
        List<RestTemplateCustomizer> customizers = restTemplateCustomizers.getIfAvailable();
        if (CollectionUtils.isNotEmpty(customizers)) {
            customizers = new ArrayList<>(customizers);
            AnnotationAwareOrderComparator.sort(customizers);
            builder = builder.customizers(customizers);
        }

        RestTemplate restTemplate = builder.build();
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        return restTemplate;
    }

    /**
     * Enhance all rest-template beans, here add a request-interceptor and a response-error-handler.
     *
     * @param responseErrorHandlerProvider responseErrorHandlerProvider
     * @return SmartInitializingSingleton
     */
    @Bean
    public SmartInitializingSingleton smartInitializingSingleton(final ObjectProvider<ResponseErrorHandler> responseErrorHandlerProvider) {
        return () -> {
            restTemplateList.forEach(restTemplate -> {
                restTemplate.getInterceptors().add(new LoggingClientHttpRequestInterceptor());
                ResponseErrorHandler responseErrorHandler = responseErrorHandlerProvider.getIfAvailable();
                if (null != responseErrorHandler) {
                    restTemplate.setErrorHandler(responseErrorHandler);
                }
            });
        };
    }

    @Bean
    public ResponseErrorHandler responseErrorHandler(final ObjectProvider<ObjectMapper> provider) {
        ObjectMapper objectMapper = provider.getIfAvailable();
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        return new LoggingResponseErrorHandler(objectMapper);
    }
}
