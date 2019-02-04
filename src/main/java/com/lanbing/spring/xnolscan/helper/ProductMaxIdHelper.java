package com.lanbing.spring.xnolscan.helper;

import com.lanbing.spring.xnolscan.model.Product;
import com.lanbing.spring.xnolscan.util.DataToDiscUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class ProductMaxIdHelper {

    private static Object lock = new Object();

    public static AtomicInteger currentMaxProductId = null;

    public static void init(int maxProductId) {
        currentMaxProductId = new AtomicInteger(maxProductId);
    }


    public static boolean setCurMaxProductId(Integer targetProductId) {
        if (targetProductId > currentMaxProductId.get()) {
            synchronized (lock) {
                if (targetProductId > currentMaxProductId.get()) {
                    currentMaxProductId.set(targetProductId);
                    DataToDiscUtils.saveToMaxId(targetProductId);
                    return true;
                }
            }
        }
        return false;
    }

    public static Integer intelligenceGetMaxId() throws Exception {
        int initId = 42629563;
        int interval = 10000;
        int step = 1;
        int maxProductId = initId;
        while (true) {
            int nextProductId = maxProductId + interval * step++;
            Product p = XnolHttpRequestHelper.getProductById2(nextProductId, false);
            if (null == p) {
                interval = interval / 2;
                step = 1;
            } else {
                maxProductId = nextProductId;
                if (interval < 50) {
                    return p.getProductId();
                }
            }
        }
    }
}
