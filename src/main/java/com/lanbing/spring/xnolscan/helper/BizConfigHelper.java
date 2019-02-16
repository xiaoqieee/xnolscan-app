package com.lanbing.spring.xnolscan.helper;

import com.lanbing.spring.xnolscan.util.DataToDiscUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Properties;

/**
 * @author xn025665
 * @date Create on 2019/2/16 13:16
 */
public class BizConfigHelper {

    private static Properties properties = new Properties();

    static {
        load();
    }

    public static void load() {
        List<String> strings = DataToDiscUtils.getConfigStr();
        if (null != strings && strings.size() > 0) {
            for (String stringValue : strings) {
                if (null == stringValue || stringValue.length() < 3 || !stringValue.contains("=")) {
                    continue;
                }
                String key = StringUtils.trimAllWhitespace(stringValue.split("=")[0]);
                String value = StringUtils.trimAllWhitespace(stringValue.split("=")[1]);
                properties.put(key, value);
            }
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        String value = properties.getProperty(key);
        return value != null ? value : defaultValue;
    }

    public static void main(String[] args) {
        load();
    }

}
