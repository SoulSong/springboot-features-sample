package com.shf.springboot.resttemplate;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * description :
 * 重构URI，此示例主要模拟在灰度发布的场景下，如果基于httpClient拦截器实现动态路由
 *
 * @author songhaifeng
 * @date 2022/3/6 13:22
 */
@Slf4j
public class ReconstructURIClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    public static final String ROUTE = "route";
    public static final String ORIGIN = "origin";
    public static final String GREY = "grey";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpRequest requestWrapper = new ReconstructURIHttpRequest(request);
        return execution.execute(requestWrapper, body);
    }

    class ReconstructURIHttpRequest extends HttpRequestWrapper {

        ReconstructURIHttpRequest(HttpRequest request) {
            super(request);
        }

        @SneakyThrows
        @Override
        public URI getURI() {
            URI uri = getRequest().getURI();
            String originUri = uri.toString();
            HttpHeaders headers = getHeaders();
            String route = ORIGIN;
            List<String> routes = headers.get(ROUTE);
            if (CollectionUtils.isNotEmpty(routes)) {
                route = routes.get(0);
            }
            // 此处mock修改的是完整的uri，在灰度发布场景下通常仅需要修改host信息，故可以使用示例如下
            //  new URI(uri.getScheme(),uri.getUserInfo(),newHost,uri.getPort(),uri.getPath(),uri.getQuery(),uri.getFragment())
            switch (route) {
                case GREY:
                    uri = new URI(originUri + "/" + GREY);
                    break;
                case ORIGIN:
                default:
                    uri = new URI(originUri + "/" + ORIGIN);
            }
            log.info("origin uri is {} ; new origin uri is {}", originUri, uri.toString());
            return uri;
        }
    }
}
