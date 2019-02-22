package com.lanbing.spring.xnolscan.util;

import java.util.List;

/**
 * @author xn025665
 * @date Create on 2019/2/22 19:15
 */
public class ListUtils {

    public static <T> String toString(List<T> list) {
        if (null == list) {
            return "null";
        }
        StringBuffer sb = new StringBuffer();
        for (Object o : list) {
            sb.append(o.toString() + ",");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
