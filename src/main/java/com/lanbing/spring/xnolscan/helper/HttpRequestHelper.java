package com.lanbing.spring.xnolscan.helper;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.util.List;
import java.util.Map;

public class HttpRequestHelper {

    public static HttpGet getDefaultHttpGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        return httpGet;
    }

    public static HttpGet getHttpGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        // 设置 headers
        Map<String, String> headers = HttpHeaderHelper.buildGetHeader();
        for (Map.Entry entry : headers.entrySet()) {
            httpGet.setHeader(entry.getKey().toString(), entry.getValue().toString());
        }
        return httpGet;
    }


    public static HttpPost getHttpPost(String url, List<NameValuePair> params, Header[] headers) throws Exception {
        HttpPost httpPost = new HttpPost(url);

        if (null != params) {
            // 使用URL实体转换工具
            UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(entityParam);
        }
        // 设置 header
        if (headers != null && headers.length > 0) {
            httpPost.setHeaders(headers);
        }
        return httpPost;
    }

    public static HttpGet getHttpGet(String url, Header[] headers) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        // 设置 header
        if (headers != null && headers.length > 0) {
            httpGet.setHeaders(headers);
        }
        return httpGet;
    }


}
