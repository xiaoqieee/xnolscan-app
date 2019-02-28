package com.lanbing.spring.xnolscan.helper;

import java.util.concurrent.atomic.AtomicInteger;

public class CookieHelper {

    private static String buyCookie = "_ga=GA1.2.1600595210.1541356001; _gid=GA1.2.1186361148.1551310433; sr=331271.43.11.3.14.103.231.82.0.33.20.15.07; _gat=1; JSESSIONID=99757E3D39B581CC7D895D0A69E93CA1.t-9003; Hm_lvt_7226b8c48cd07619c7a9ebd471d9d589=1549000872,1549791152,1550672073,1551365017; lcksid=5c77f3a2e95072007c2d7d8f; SESSIONID=0b36f023-6120-4548-99f3-6c5be76668e7; referer=\"https://www.xiaoniu88.com/user/_2019-02-28\"; Hm_lpvt_7226b8c48cd07619c7a9ebd471d9d589=1551365030";

    private static String[] queryCookies = {
            "_ga=GA1.2.1600595210.1541356001; _gid=GA1.2.1186361148.1551310433; sr=331271.43.11.3.14.103.231.82.0.33.20.15.07; _gat=1; JSESSIONID=99757E3D39B581CC7D895D0A69E93CA1.t-9003; Hm_lvt_7226b8c48cd07619c7a9ebd471d9d589=1549000872,1549791152,1550672073,1551365017; lcksid=5c77f3a2e95072007c2d7d8f; SESSIONID=0b36f023-6120-4548-99f3-6c5be76668e7; referer=\"https://www.xiaoniu88.com/user/_2019-02-28\"; Hm_lpvt_7226b8c48cd07619c7a9ebd471d9d589=1551365030"
            ,
            "_ga=GA1.2.1522612021.1507704380; Hm_lvt_bfadffd6cb8f795e488eccaeb28cab61=1532349774; _gid=GA1.2.1244477587.1551062952; JSESSIONID=AD4C097DF0F10BA5A438C449B240E141.t-9003; Hm_lvt_7226b8c48cd07619c7a9ebd471d9d589=1549953491,1550106840,1550727295,1551229344; sr=331271.43.11.3.116.7.225.146.0.33.20.15.07; _gat=1; lcksid=5c786c86561272007cfd2d14; SESSIONID=23740fce-80b7-496b-80c3-8ce81e14cdd3; referer=\"https://www.xiaoniu88.com/user/_2019-03-01\"; Hm_lpvt_7226b8c48cd07619c7a9ebd471d9d589=1551395976"
            ,
            "__guid=236620295.4115162701599605000.1550487244546.7854; _ga=GA1.2.2118731476.1550487246; sr=331271.43.11.3.116.7.225.146.0.33.20.15.07; JSESSIONID=5400A335016B1DCBC2C948FC1715FCE9.t-9003; Hm_lvt_7226b8c48cd07619c7a9ebd471d9d589=1550728320,1550730422,1550791533,1551396055; _gid=GA1.2.1107040496.1551396055; _gat=1; lcksid=5c786cf0561272007cfd2d15; SESSIONID=31a7e39b-2dc5-4bd9-949a-5d9ab1e948ba; referer=\"https://www.xiaoniu88.com/user/_2019-03-01\"; Hm_lpvt_7226b8c48cd07619c7a9ebd471d9d589=1551396082; monitor_count=4"
    };

    private static AtomicInteger count = new AtomicInteger(0);

    public static String getBuyCookie(){
        return buyCookie;
    }

    public static String getQueryCookie(){
        return queryCookies[count.incrementAndGet()%queryCookies.length];
    }
}
