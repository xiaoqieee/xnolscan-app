package com.lanbing.spring.xnolscan.helper;

import com.lanbing.spring.xnolscan.model.Product;
import com.lanbing.spring.xnolscan.util.DataToDiscUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class ProductMaxIdHelper {

    private static Object lock = new Object();

    public static AtomicInteger currentMaxProductId = new AtomicInteger(46638407);

    public static void init(int baseProductId) {
        int maxProductId = intelligenceGetMaxId(baseProductId);
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

    public static int intelligenceGetMaxId(int baseId) {
        int start = baseId;
        int step = 100000;

        int productId = start;

        while (step > 1) {
            int failTimes = 1;
            productId += step;
            while (true) {
                try {
                    Product p = XnolHttpRequestHelper.getProductById2(productId, true);
                    System.out.println(productId + ":>>>>>:" + p);
                    if (p == null) {
                        failTimes++;
                    } else {
                        productId += step;
                    }
                    if (failTimes > 2) {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("智能获取最大ID异常，异常信息:" + e);
                }
            }
            productId -= step;
            step = step / 10;
        }
        return productId;
    }
}
