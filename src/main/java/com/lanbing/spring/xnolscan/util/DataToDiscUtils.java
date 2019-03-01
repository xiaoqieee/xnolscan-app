package com.lanbing.spring.xnolscan.util;

import com.lanbing.spring.xnolscan.model.Product;
import com.lanbing.spring.xnolscan.thread.ThreadPoolManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static void saveToMaxId(Product p) {
        ThreadPoolManager.addTask(() -> {
            try {
                DataToDiscUtils.appendToFile("/data/maxid.txt", p.getProductId().toString() + "[" + DateUtils.getTimeString() + "][" + p.toString() + "]");
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

    public static String getCookie(String loginUser) {
        try {
            return getFirstLine("/data/" + loginUser + "/cookie.txt");
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

    public static List<String> getConditionStr() {
        try {
            return getLines("/data/condition.txt");
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

    public static Map<String, String> getCookieMap() {
        try {
            Map<String, String> map = getDataMap("/data/cookie.txt", "::");
            return map;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static Map<String, String> getDataMap(String filePath, String sep) throws Exception {
        List<String> dataArr = FileUtils.readLines(new File(filePath), "UTF-8");
        Map<String, String> map = new HashMap<>();
        for (String line : dataArr) {
            map.put(line.split("::")[0], line.split("::")[1]);
        }
        return map;
    }

    public static Integer getMaxProductId() {
        Integer maxProductId = 49479530;
        try {
            List<String> lines = getLines("/data/maxid.txt");
            if (lines != null && lines.size() > 0) {
                String line = lines.get(lines.size() - 2);
                String idStr = line.split("\\[")[0];
                maxProductId = null != idStr ? Integer.valueOf(idStr) : maxProductId;
            }
        } catch (Exception e) {
        }
        return maxProductId;
    }

    public static void main(String[] args) {
        System.out.println(getMaxProductId());
    }
}
