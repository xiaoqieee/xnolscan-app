package com.lanbing.spring.xnolscan.helper;

import com.lanbing.spring.xnolscan.util.DateUtils;
import com.lanbing.spring.xnolscan.util.TokenUtils;

import java.util.ArrayList;
import java.util.List;

public class RequestTokenHelper {

    private final static int POOL_SIZE = 100;

    private static List<String> tokenPool = new ArrayList<>(POOL_SIZE);

    public static void producer() {
        while (true) {
            try {
                String detailPage = XnolHttpRequestHelper.detailPage(ProductMaxIdHelper.currentMaxProductId.get());
                if (null != detailPage && detailPage.length() > 0) {
                    String tokenName = TokenUtils.getTokenName(detailPage);
                    String tokenValue = TokenUtils.getTokenValue(detailPage);

                    if (null != tokenName && null != tokenValue) {
                        add(tokenName + "," + tokenValue);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            DateUtils.sleep(200);
        }
    }

    public synchronized static void add(String token) {
        if (tokenPool.size() > POOL_SIZE) {
            removeAndGetToken();
        }
        tokenPool.add(token);
    }

    public static String[] removeAndGetToken() {
        if (tokenPool.size() > 0) {
            return tokenPool.remove(0).split(",");
        }
        return null;
    }

    public static void main(String[] args) {
        producer();
    }
}
