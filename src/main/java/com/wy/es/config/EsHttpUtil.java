package com.wy.es.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author wanyanhw
 * @date 2021/4/30 17:47
 */
@Component
@Slf4j
public class EsHttpUtil {

    @Autowired
    private RestClient restClient;

    public Response doGetRequest(String url, JSONObject params, JSONObject body) throws Exception {
        return doRequest("GET", url, params, body);
    }

    public Response doPostRequest(String url, JSONObject params, JSONObject body) throws Exception {
        return doRequest("POST", url, params, body);
    }

    private Response doRequest(String method, String url, JSONObject params, JSONObject body) throws Exception {
        if (!StringUtils.hasLength(url)) {
            throw new Exception("url can not be null");
        }
        Request request = new Request(method, url);
        if (params != null) {
            params.forEach((k, v) -> request.addParameter(k, String.valueOf(v)));
        }
        request.setJsonEntity(body.toJSONString());
        Response response = restClient.performRequest(request);
        if (log.isDebugEnabled()) {
            log.info("request: {}, response : {}", request.getMethod(), response.getStatusLine().getStatusCode());
        }
        return response;
    }
}
