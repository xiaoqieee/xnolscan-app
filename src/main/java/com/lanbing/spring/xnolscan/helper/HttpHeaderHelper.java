package com.lanbing.spring.xnolscan.helper;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaderHelper {


    static String cookie = "_ga=GA1.2.1600595210.1541356001; _gid=GA1.2.306530267.1548810468; Hm_lvt_7226b8c48cd07619c7a9ebd471d9d589=1548979884,1548983224,1548987293,1549000872; JSESSIONID=DEFA4707121745ACD52519895675DFEA.t-9003; sr=331271.43.11.3.220.152.148.74.0.33.20.15.07; _gat=1; lcksid=5c578641fd66ff007c3bf03f; SESSIONID=669ede03-8596-4b74-9a35-7d60a74902ab; referer=\"https://www.xiaoniu88.com/user/_2019-02-04\"; Hm_lpvt_7226b8c48cd07619c7a9ebd471d9d589=1549239968";

    /**
     * 组装请求头
     *
     * @return
     */
    public static Header[] buildPostHeader() {
        Map<String, String> header = new HashMap(8);
        //将浏览器的cookie复制到这里

        header.put(HttpHeaders.ACCEPT, "*/*");
        header.put(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br");
        header.put(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9");
        header.put(HttpHeaders.CONNECTION, "keep-alive");
        header.put(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
        header.put("Cookie", cookie);
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
        header.put("Cookie", cookie);
        header.put(HttpHeaders.HOST, "www.xiaoniu88.com");
        // header.put(HttpHeaders.USER_AGENT, "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
        header.put(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        header.put("Upgrade-Insecure-Requests", "1");

        return header;
    }

    /**
     * 组装请求头
     *
     * @return
     */
    public static Map<String, String> buildDetailPageHeader() {
        Map<String, String> header = new HashMap(8);
        //将浏览器的cookie复制到这里

        header.put(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        header.put(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br");
        header.put(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh;q=0.9,en;q=0.8");
        header.put(HttpHeaders.CACHE_CONTROL, "max-age=0");
        header.put(HttpHeaders.CONNECTION, "keep-alive");
        header.put("Cookie", cookie);
        header.put(HttpHeaders.HOST, "www.xiaoniu88.com");
        header.put("Upgrade-Insecure-Requests", "1");
        // header.put(HttpHeaders.USER_AGENT, "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
        header.put(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");

        return header;
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
