package com.lanbing.spring.xnolscan.helper;

import com.lanbing.spring.xnolscan.model.Product;
import com.lanbing.spring.xnolscan.util.DataToDiscUtils;

import java.util.HashSet;
import java.util.Set;

public class ScanedProductIdHelper {

    private final static int MAX_PRODUCTID_COUNT = 40;

    private static Set<Integer> scanedProductIds = new HashSet<>(MAX_PRODUCTID_COUNT);

    public static synchronized boolean add(Product p) {
        if (scanedProductIds.size() >= MAX_PRODUCTID_COUNT) {
            scanedProductIds.remove(0);
        }
        Integer productId = p.getProductId();
        boolean success = scanedProductIds.add(productId);
        if (success) {
            ProductMaxIdHelper.setCurMaxProductId(productId);
        }
        DataToDiscUtils.saveToMaxId(p);
        return success;
    }

    public static boolean isScaned(Integer productId) {
        return scanedProductIds.contains(productId);
    }
}
