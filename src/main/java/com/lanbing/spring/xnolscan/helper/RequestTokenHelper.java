package com.lanbing.spring.xnolscan.helper;

import com.lanbing.spring.xnolscan.util.DateUtils;
import com.lanbing.spring.xnolscan.util.TokenUtils;

import java.util.concurrent.ConcurrentLinkedDeque;

public class RequestTokenHelper{

    private final static int POOL_SIZE = 100;


    static ConcurrentLinkedDeque<String> tokenPool = new ConcurrentLinkedDeque();


    public static void producerAsync() {
        new Thread(() -> {
            producer();
        }).start();
    }

    private static void producer() {
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
                System.out.println(e.getMessage());
            }
            DateUtils.sleep(1000);
        }
    }

    public synchronized static void add(String token) {
        if (tokenPool.size() > POOL_SIZE) {
            tokenPool.removeFirst();
        }
        tokenPool.addLast(token);
    }

    public static String[] getToken() {
        if (tokenPool.size() > 0) {
            return tokenPool.removeLast().split(",");
        }
        return null;
    }

    public static void clear(){
        tokenPool.clear();
    }

    public static void main(String[] args) {
        producer();
    }
}
