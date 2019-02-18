package com.lanbing.spring.xnolscan.helper;

import com.lanbing.spring.xnolscan.constant.Constants;
import com.lanbing.spring.xnolscan.model.Product;
import com.lanbing.spring.xnolscan.util.DataToDiscUtils;
import com.lanbing.spring.xnolscan.util.DecimalUtil;
import org.apache.kafka.common.protocol.types.Field;

import java.math.BigDecimal;
import java.text.Bidi;
import java.util.ArrayList;
import java.util.List;

public class ProductCanBuyHelper {

    public static boolean canBuy_(Product t) {
        if (DecimalUtil.le(t.getLeftAmount(), BigDecimal.ZERO)) {
            return false;
        }

        if (DecimalUtil.gt(t.getLeftAmount(), BigDecimal.valueOf(5000))) {
            if (t.getProductTerm() < 90 && t.getTargetRatio() > 0.5) {
                return true;
            } else {
                return false;
            }
        }

        if (t.getProductTerm() < 60 && t.getTargetRatio() > 0.25) {
            return true;
        }

        if (t.getProductTerm() < 90 && t.getTargetRatio() > 0.27) {
            return true;
        }

        if (t.getProductTerm() < 120 && t.getTargetRatio() > 0.33) {
            return true;
        }

        if (t.getProductTerm() < 180 && t.getTargetRatio() > 0.38) {
            return true;
        }

        if (t.getProductTerm() < 240 && t.getTargetRatio() > 0.42) {
            return true;
        }
        return false;
    }

    public static boolean canBuy(Product t) {
        if (DecimalUtil.le(t.getLeftAmount(), BigDecimal.ZERO)) {
            return false;
        }

        if (DecimalUtil.gt(t.getLeftAmount(), BigDecimal.valueOf(5000))) {
            return false;
        }
        if (t.getProductTerm() < 40 && t.getRatio() > 0.04) {
            return true;
        }

        if (t.getProductTerm() < 60 && t.getRatio() > 0.06) {
            return true;
        }

        return false;
    }

    public static boolean canBuyD(Product t) {
        if (DecimalUtil.le(t.getLeftAmount(), BigDecimal.ZERO)) {
            return false;
        }

        if (DecimalUtil.gt(t.getLeftAmount(), BigDecimal.valueOf(Constants.MAX_PRODUCT_AMOUNT))) {
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
        String[] result = new String[]{"40:0.04", "60:0.045"};
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
