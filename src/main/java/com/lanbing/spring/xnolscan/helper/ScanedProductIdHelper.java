package com.lanbing.spring.xnolscan.helper;

import java.util.HashSet;
import java.util.Set;

public class ScanedProductIdHelper {

    private final static int MAX_PRODUCTID_COUNT = 40;

    private static Set<Integer> scanedProductIds = new HashSet<>(MAX_PRODUCTID_COUNT);

    public static synchronized boolean add(Integer productId) {
        if (scanedProductIds.size() >= MAX_PRODUCTID_COUNT) {
            scanedProductIds.remove(0);
        }
        return scanedProductIds.add(productId);
    }

    public static boolean isScaned(Integer productId) {
        return scanedProductIds.contains(productId);
    }
}
