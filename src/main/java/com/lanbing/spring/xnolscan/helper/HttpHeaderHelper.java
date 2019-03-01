package com.lanbing.spring.xnolscan.helper;

import com.lanbing.spring.xnolscan.constant.Constants;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HttpHeaderHelper {

    /**
     * 组装请求头
     *
     * @return
     */
    public static Header[] buildBuyPostHeader() {
        Map<String, String> header = new HashMap(8);
        //将浏览器的cookie复制到这里

        header.put(HttpHeaders.ACCEPT, "*/*");
        header.put(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br");
        header.put(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9");
        header.put(HttpHeaders.CONNECTION, "keep-alive");
        header.put(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
        header.put("Cookie", CookieHelper.getBuyCookie());
        header.put(HttpHeaders.HOST, "www.xiaoniu88.com");
        header.put(HttpHeaders.REFERER, "https://www.xiaoniu88.com/product/list?type=transfer");
        header.put("Origin", "https://www.xiaoniu88.com");
        header.put(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36");
        header.put("X-Requested-Wit", "XMLHttpRequest");
        return buildByMap(header);
    }

    /**
     * 组装请求头
     *
     * @return
     */
    public static Header[] buildQueryPostHeader() {
        Map<String, String> header = new HashMap(8);
        //将浏览器的cookie复制到这里

        header.put(HttpHeaders.ACCEPT, "*/*");
        header.put(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br");
        header.put(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9");
        header.put(HttpHeaders.CONNECTION, "keep-alive");
        header.put(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
        header.put("Cookie", CookieHelper.getQueryCookie());
        header.put(HttpHeaders.HOST, "www.xiaoniu88.com");
        header.put(HttpHeaders.REFERER, "https://www.xiaoniu88.com/product/list?type=transfer");
        header.put("Origin", "https://www.xiaoniu88.com");
        header.put(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36");
        header.put("X-Requested-Wit", "XMLHttpRequest");
        return buildByMap(header);
    }

    /**
     * 组装请求头
     *
     * @return
     */
    public static Map<String, String> buildGetHeader() {
        Map<String, String> header = new HashMap(8);
        //将浏览器的cookie复制到这里

        header.put(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br");
        header.put(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9");
        header.put(HttpHeaders.CONNECTION, "keep-alive");
        header.put("Cookie", CookieHelper.getQueryCookie());
        header.put(HttpHeaders.HOST, "www.xiaoniu88.com");
        // header.put(HttpHeaders.USER_AGENT, "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
        header.put(HttpHeaders.USER_AGENT, getUserAgent());
        header.put("Upgrade-Insecure-Requests", "1");

        return header;
    }

    private static String getUserAgent() {
        int size = Constants.USER_AGENT_LIST.length;
        return Constants.USER_AGENT_LIST[new Random().nextInt(size)];
    }


    private static Header[] buildByMap(Map<String, String> header) {
        Header[] headers = null;
        if (header != null && header.size() > 0) {
            headers = new BasicHeader[header.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : header.entrySet()) {
                headers[i] = new BasicHeader(entry.getKey(), entry.getValue());
                i++;
            }
        }
        return headers;
    }
}
