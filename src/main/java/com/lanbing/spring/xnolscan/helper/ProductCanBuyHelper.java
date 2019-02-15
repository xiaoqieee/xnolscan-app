package com.lanbing.spring.xnolscan.helper;

import com.lanbing.spring.xnolscan.model.Product;
import com.lanbing.spring.xnolscan.util.DecimalUtil;

import java.math.BigDecimal;
import java.text.Bidi;

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

        return false;
    }
}
