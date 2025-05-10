package com.ling.utils;

import com.ling.enums.ResponseCodeEnum;
import com.ling.exception.BusinessException;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp工具类
 */
public class OkHttpUtil {
    private static Logger log = LoggerFactory.getLogger(OkHttpUtil.class);
    private static final int TIMEOUT_SECS = 5;  // 超时时间
    private static final OkHttpClient CLIENT = getClientBuilder().build();


    /**
     * 获取OkHttpClient.Builder
     *
     * @return
     */
    private static OkHttpClient.Builder getClientBuilder() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .followRedirects(false)
                .retryOnConnectionFailure(false);
        clientBuilder.connectTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECS, TimeUnit.SECONDS);
        return clientBuilder;
    }

    /**
     * 获取Request.Builder
     *
     * @param header
     * @return
     */
    private static Request.Builder getRequestBuilder(Map<String, String> header) {
        Request.Builder requestBuilder = new Request.Builder();
        if (header != null) {
            header.forEach(requestBuilder::addHeader);
        }
        return requestBuilder;
    }

    /**
     * GET请求
     *
     * @param url
     * @return
     */
    public static String getRequest(String url) {
        try {
            Request.Builder requestBuilder = getRequestBuilder(null);
            Request request = requestBuilder.url(url).build();
            Response response = CLIENT.newCall(request).execute();
            try (ResponseBody body = response.body()) {
                return body != null ? body.string() : "";
            }
        } catch (SocketTimeoutException | ConnectException e) {
            log.error("GET请求超时, url: {}", url, e);
            throw new BusinessException(ResponseCodeEnum.CODE_900, e);
        } catch (Exception e) {
            log.error("GET请求异常", e);
            throw new BusinessException(ResponseCodeEnum.CODE_500);
        }
    }

    /**
     * PUT请求
     * @param url     请求地址
     * @param jsonBody 请求体JSON字符串
     * @param header  请求头（可选）
     * @return 响应内容
     */
    public static String putRequest(String url, String jsonBody, Map<String, String> header) {
        try {
            RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
            Request.Builder requestBuilder = getRequestBuilder(header).url(url).put(body);
            Request request = requestBuilder.build();

            Response response = CLIENT.newCall(request).execute();
            try (ResponseBody responseBody = response.body()) {
                return responseBody != null ? responseBody.string() : "";
            }
        } catch (SocketTimeoutException | ConnectException e) {
            log.error("OkHttp PUT 请求超时, url: {}, body: {}", url, jsonBody, e);
            throw new BusinessException(ResponseCodeEnum.CODE_900, e);
        } catch (Exception e) {
            log.error("OkHttp PUT 请求异常, url: {}, body: {}", url, jsonBody, e);
            throw new BusinessException(ResponseCodeEnum.CODE_500, e);
        }
    }
}
