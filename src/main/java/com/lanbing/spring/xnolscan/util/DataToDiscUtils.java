package com.lanbing.spring.xnolscan.util;

import com.lanbing.spring.xnolscan.model.Product;
import com.lanbing.spring.xnolscan.thread.ThreadPoolManager;
import org.apache.commons.io.FileUtils;
import org.apache.kafka.common.protocol.types.Field;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataToDiscUtils {

    public final static int TYPE_LIST = 1;
    public final static int TYPE_DETAIL = 2;

    public static void saveToRecord(Integer type, Product t) {
        ThreadPoolManager.addTask(() -> {
            try {
                DataToDiscUtils.appendToFile("/data/record.txt", type + ":" + t.toString());
            } catch (Exception e) {
            }
        });
    }

    public static void saveToMaxId(Integer maxProductId) {
        ThreadPoolManager.addTask(() -> {
            try {
                DataToDiscUtils.appendToFile("/data/maxid.txt", maxProductId.toString() + "[" + DateUtils.getTimeString() + "]");
            } catch (Exception e) {
            }
        });

    }

    public static void saveToProduct(Product t) {
        ThreadPoolManager.addTask(() -> {
            try {
                DataToDiscUtils.appendToFile("/data/product.txt", t.getProductId() + "[" + DateUtils.getTimeString() + "]");
            } catch (Exception e) {
            }
        });

    }

    public static void saveByResult(Integer productId, String[] result) {
        ThreadPoolManager.addTask(() -> {
            try {
                DataToDiscUtils.appendToFile("/data/result.txt", productId + "[" + result[0] + "][" + result[1] + "][" + DateUtils.getTimeString() + "]");
            } catch (Exception e) {
            }
        });

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

    public static String getCookie() {
        try {
            return getFirstLine("/data/cookie.txt");
        } catch (Exception e) {

        }
        return null;
    }

    public static List<String> getConfigStr() {
        try {
            return getLines("/data/config.properties");
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private static String getFirstLine(String filePath) throws Exception {
        List<String> dataArr = FileUtils.readLines(new File(filePath), "UTF-8");
        if (dataArr != null && dataArr.size() > 0) {
            return dataArr.get(0);
        }
        return null;
    }

    private static List<String> getLines(String filePath) throws Exception {
        List<String> dataArr = FileUtils.readLines(new File(filePath), "UTF-8");
        return dataArr;
    }
}
