package com.nabrothers.psl.proxy.utils;

import com.alibaba.fastjson.JSONObject;
import com.nabrothers.psl.proxy.dto.HttpResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

@Log4j2
public class HttpUtils {
    public static HttpResponse doGet(String url) {
        HttpResponse response = new HttpResponse();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("DataEncoding", "UTF-8");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            if(httpResponse.getStatusLine().getStatusCode() != 200){
                response.setCode(httpResponse.getStatusLine().getStatusCode());
            }
            response.setData(EntityUtils.toString(entity));
            return response;
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            log.error(e);
            response.setMessage(e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e);
            response.setMessage(e.getMessage());
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e);
                    response.setMessage(e.getMessage());
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error(e);
                    response.setMessage(e.getMessage());
                }
            }
        }
        return response;
    }

    public static HttpResponse doPost(String url, JSONObject body, JSONObject header) {
        HttpResponse response = new HttpResponse();

        String jsonStr = body == null ? "" : body.toJSONString();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setStaleConnectionCheckEnabled(true)
                .setConnectTimeout(3000)
                .setConnectionRequestTimeout(35000)
                .setSocketTimeout(120000)
                .build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("DataEncoding", "UTF-8");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1");

        if (header != null) {
            for (Map.Entry<String, Object> entry : header.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        CloseableHttpResponse httpResponse = null;
        try {
            httpPost.setEntity(new StringEntity(jsonStr, ContentType.APPLICATION_JSON));
            httpResponse = httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode() != 200){
                response.setCode(httpResponse.getStatusLine().getStatusCode());
                return response;
            }
            HttpEntity entity = httpResponse.getEntity();
            response.setData(EntityUtils.toString(entity));
            return response;
        } catch (ClientProtocolException e) {
            log.error(e);
            response.setMessage(e.getMessage());
        } catch (IOException e) {
            log.error(e);
            response.setMessage(e.getMessage());
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    log.error(e);
                    response.setMessage(e.getMessage());
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error(e);
                    response.setMessage(e.getMessage());
                }
            }
        }
        return null;
    }
}
