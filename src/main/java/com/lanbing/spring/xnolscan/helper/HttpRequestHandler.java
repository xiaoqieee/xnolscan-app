package com.lanbing.spring.xnolscan.helper;


import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


/**
 * This example demonstrates the use of the {@link ResponseHandler} to simplify
 * the process of processing the HTTP response and releasing associated resources.
 */
public class HttpRequestHandler {

    public static String doOptimizRequest(HttpUriRequest httpRequest) throws Exception {
        String body = "";

        //创建自定义的httpclient对象
        CloseableHttpClient client = (CloseableHttpClient) HttpClientHelper.generateHttpClient();
//        client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
//        CloseableHttpClient client = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = client.execute(httpRequest);
            if (200 == response.getStatusLine().getStatusCode()) {
                //获取结果实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    //按指定编码转换结果实体为String类型
                    body = EntityUtils.toString(entity, "UTF-8");
                }
                EntityUtils.consume(entity);
            }
            //释放链接
            response.close();
        } finally {
            client.close();
        }
        return body;
    }

    public static String doRequest(HttpGet httpGet) throws Exception {
        String body = "";

        //创建自定义的httpclient对象
        CloseableHttpClient client = (CloseableHttpClient) HttpClientHelper.generateHttpClient();
//        client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
//        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpContext httpContext = new BasicHttpContext();
            CloseableHttpResponse response = client.execute(httpGet,httpContext);
            if (200 == response.getStatusLine().getStatusCode()) {
                //获取结果实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    //按指定编码转换结果实体为String类型
                    body = EntityUtils.toString(entity, "UTF-8");
                }
                EntityUtils.consume(entity);
            }
            //释放链接
            response.close();
        } finally {
            client.close();
        }
        return body;
    }


    private static String defaultGet() throws Exception {
        String responseBody = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet("http://www.baidu.com/");

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            responseBody = httpclient.execute(httpget, responseHandler);
        } finally {
            httpclient.close();
        }
        return responseBody;
    }


    private static String submitPost(HttpPost httpPost) throws Exception {
        String content = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResponse http_response = null;
        try {
            http_response = httpClient.execute(httpPost);
            HttpEntity entity = http_response.getEntity();
            content = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != http_response) {
                EntityUtils.consume(http_response.getEntity());
            }
        }
        return content;
    }

    private static String submitGet(HttpGet httpGet) throws Exception {
        String content = null;
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        HttpResponse http_response = null;
        try {
            http_response = httpCilent.execute(httpGet);
            HttpEntity entity = http_response.getEntity();
            content = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != http_response) {
                EntityUtils.consume(http_response.getEntity());
            }
        }
        return content;
    }


}

