package com.wy.es.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * 搜索客户端
 * @author wanyanhw
 * @date 2021/4/30 15:59
 */
@Configuration
@Slf4j
public class EsLowClientConfig {

    private final HttpHost[] HTTP_HOSTS = new HttpHost[] {
            new HttpHost("localhost", 9200, "http"),
            new HttpHost("localhost", 9201, "http")
    };

    private final RestClient restClient;

    @Autowired
    public EsLowClientConfig(RestClient restClient) {
        this.restClient = restClient;
    }

    @Bean
    public RestClientBuilder restClientBuilder() {
        return RestClient.builder(HTTP_HOSTS);
    }

    @Bean
    public RestClient restClient(@Autowired RestClientBuilder restClientBuilder) {
        return restClientBuilder.build();
    }

    public Response doGetRequest(String url, JSONObject params, String body) throws Exception {
        if (!StringUtils.hasLength(url)) {
            throw new Exception("url can not be null");
        }
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        Request request = new Request("GET", url);
        params.forEach((k, v) -> request.addParameter(k, String.valueOf(v)));
        if (StringUtils.hasLength(body)) {
            request.setJsonEntity(body);
        }
        Response response = this.restClient.performRequest(request);
        if (log.isDebugEnabled()) {
            log.info("request: {}, response : {}", request.getMethod(), response.getStatusLine().getStatusCode());
        }
        return response;
    }
}
