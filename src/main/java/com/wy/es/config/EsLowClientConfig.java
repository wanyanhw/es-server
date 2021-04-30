package com.wy.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 搜索客户端
 * @author wanyanhw
 * @date 2021/4/30 15:59
 */
@Configuration
public class EsLowClientConfig {

    private final HttpHost[] HTTP_HOSTS = new HttpHost[] {
            new HttpHost("localhost", 9200, "http"),
            new HttpHost("localhost", 9201, "http")
    };

    @Bean
    public RestClientBuilder restClientBuilder() {
        return RestClient.builder(HTTP_HOSTS);
    }

    @Bean
    public RestClient restClient(@Autowired RestClientBuilder restClientBuilder) {
        return restClientBuilder.build();
    }
}
