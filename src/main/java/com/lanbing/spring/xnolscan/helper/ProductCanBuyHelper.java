package com.lanbing.spring.xnolscan.helper;

import com.lanbing.spring.xnolscan.model.Product;
import com.lanbing.spring.xnolscan.util.DataToDiscUtils;
import com.lanbing.spring.xnolscan.util.DecimalUtil;

import java.math.BigDecimal;
import java.util.List;

public class ProductCanBuyHelper {

    public static boolean canBuy(Product t) {
        if (DecimalUtil.le(t.getLeftAmount(), BigDecimal.ZERO)) {
            return false;
        }

        if (t.getProductTerm() < 40 && t.getRatio() > 0.08) {
            return true;
        }

        if (t.getProductTerm() < 60 && t.getRatio() > 0.12) {
            return true;
        }

        return false;
    }

    public static boolean canBuyD(Product t) {
        if (DecimalUtil.le(t.getLeftAmount(), BigDecimal.ZERO)) {
            return false;
        }

        String[] conditions = getCondition();
        for (String str : conditions) {
            String[] ss = str.split(":");
            Integer maxTerm = Integer.valueOf(ss[0]);
            Float minRatio = Float.valueOf(ss[1]);
            if (t.getProductTerm() <= maxTerm && t.getRatio() >= minRatio) {
                return true;
            }
        }
        return false;
    }

    private static String[] getCondition() {
        List<String> strings = DataToDiscUtils.getConditionStr();
        String[] result = new String[]{"40:0.08", "60:0.12"};
        if (null != strings & strings.size() > 0) {
            int i = 0;
            result = new String[strings.size()];
            for (String str : strings) {
                if (null != str && str.length() > 0 && str.contains(":")) {
                    result[i++] = str;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getCondition());
    }
}
