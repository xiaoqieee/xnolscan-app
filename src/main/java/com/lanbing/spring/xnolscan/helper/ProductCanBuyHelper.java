package com.lanbing.spring.xnolscan.helper;

import com.lanbing.spring.xnolscan.model.Product;

public class ProductCanBuyHelper {

    public static boolean canBuy_(Product t) {
        if (t.getLeftAmount() <= 0) {
            return false;
        }

        if (t.getLeftAmount() > 3000) {
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
        if (t.getLeftAmount() <= 0) {
            return false;
        }
        if (t.getLeftAmount() > 3000) {
            return false;
        }
        if (t.getProductTerm() < 60 && t.getTargetRatio() > 0.4) {
            return true;
        }

        return false;
    }
}
