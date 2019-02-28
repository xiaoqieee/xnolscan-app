package com.lanbing.spring.xnolscan.helper;

import java.util.concurrent.atomic.AtomicInteger;

public class CookieHelper {

    private static String buyCookie = "_ga=GA1.2.1600595210.1541356001; Hm_lvt_7226b8c48cd07619c7a9ebd471d9d589=1548987293,1549000872,1549791152,1550672073; sr=331271.43.11.3.14.103.231.82.0.33.20.15.07; JSESSIONID=37031DA619B8B0C040FC4CD4304DD2E6.t-9003; lcksid=5c73f4b1164e6a006db47bab; SESSIONID=df395d86-d58c-499e-befa-4312de6a35d6; referer=\"https://www.xiaoniu88.com/user/_2019-02-25\"; _gid=GA1.2.1186361148.1551310433; Hm_lpvt_7226b8c48cd07619c7a9ebd471d9d589=1551362356; _gat=1";

    private static String[] queryCookies = {
            "_ga=GA1.2.1600595210.1541356001; Hm_lvt_7226b8c48cd07619c7a9ebd471d9d589=1548987293,1549000872,1549791152,1550672073; sr=331271.43.11.3.14.103.231.82.0.33.20.15.07; JSESSIONID=37031DA619B8B0C040FC4CD4304DD2E6.t-9003; lcksid=5c73f4b1164e6a006db47bab; SESSIONID=df395d86-d58c-499e-befa-4312de6a35d6; referer=\"https://www.xiaoniu88.com/user/_2019-02-25\"; _gid=GA1.2.1186361148.1551310433; Hm_lpvt_7226b8c48cd07619c7a9ebd471d9d589=1551362356; _gat=1"
    };

    private static AtomicInteger count = new AtomicInteger(0);

    public static String getBuyCookie(){
        return buyCookie;
    }

    public static String getQueryCookie(){
        return queryCookies[count.incrementAndGet()%queryCookies.length];
    }
}
