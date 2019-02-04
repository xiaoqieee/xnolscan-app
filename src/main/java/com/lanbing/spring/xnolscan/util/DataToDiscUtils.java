package com.lanbing.spring.xnolscan.util;

import com.lanbing.spring.xnolscan.model.Product;

import java.io.File;
import java.io.FileOutputStream;

public class DataToDiscUtils {

    public final static int TYPE_LIST = 1;
    public final static int TYPE_DETAIL = 2;

    public static void saveToRecord(Integer type, Product t) {
        try {
            DataToDiscUtils.appendToFile("D:/data/record.txt", type + ":" + t.toString());
        } catch (Exception e) {

        }
    }

    public static void saveToMaxId(Integer maxProductId) {
        try {
            DataToDiscUtils.appendToFile("D:/data/maxid.txt", maxProductId.toString() + "[" + DateUtils.getTimeString() + "]");
        } catch (Exception e) {
        }
    }

    public static void saveToProduct(Product t) {
        try {
            DataToDiscUtils.appendToFile("D:/data/product.txt", t.getProductId() + "[" + DateUtils.getTimeString() + "]");
        } catch (Exception e) {
        }
    }

    public static void saveByResult(Integer productId, String[] result) {
        try {
            DataToDiscUtils.appendToFile("D:/data/result.txt", productId + "[" + result[0] + "][" + result[1] + "][" + DateUtils.getTimeString() + "]");
        } catch (Exception e) {
        }
    }

    private static void appendToFile(String filePath, String dataStr) throws Exception {
        if (null == filePath || null == dataStr) {
            return;
        }
        FileOutputStream out = new FileOutputStream(new File(filePath), true);
        out.write(dataStr.getBytes("utf-8"));
        out.write("\r\n".getBytes());
        out.flush();
        out.close();
    }
}
