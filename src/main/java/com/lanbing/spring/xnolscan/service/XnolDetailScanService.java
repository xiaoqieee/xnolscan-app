package com.lanbing.spring.xnolscan.service;


import com.lanbing.spring.xnolscan.constant.ConfigKey;
import com.lanbing.spring.xnolscan.helper.BizConfigHelper;
import com.lanbing.spring.xnolscan.helper.ProductMaxIdHelper;
import com.lanbing.spring.xnolscan.helper.ScanedProductIdHelper;
import com.lanbing.spring.xnolscan.helper.StatusHelper;
import com.lanbing.spring.xnolscan.model.ProductIdBO;
import com.lanbing.spring.xnolscan.util.DateUtils;
import org.springframework.stereotype.Service;

@Service
public class XnolDetailScanService extends XnolProductScanHelper {

    public void doDetail(ProductIdBO productIdBO) {
        while (true) {
            if (!StatusHelper.isStarting()) {
                logger.info("已经手动关闭");
                break;
            }
            try {
                Integer productId = productIdBO.getNextProductId();
                doDetailLoop(productId, productIdBO.getStep());
            } catch (Exception e) {
                logger.error("循环处理详情异常", e.getMessage());
            }
        }
    }


    public boolean doDetailLoop(Integer productId, int step) {
        boolean hasTheProduct = false;
        while (!hasTheProduct) {
            if (productId < ProductMaxIdHelper.currentMaxProductId.get() - step) {
                return true;
            }
            if (!ScanedProductIdHelper.isScaned(productId)) {
                hasTheProduct = doDetail(productId);
                DateUtils.sleep(Integer.valueOf(BizConfigHelper.get(ConfigKey.DETAIL_LOOP_INTERVAL, "500")));
            }
        }
        return true;
    }

    private boolean breakRetern(long currentProductIdStart) {
        if (!StatusHelper.isStarting()) {
            return true;
        }

        if (System.currentTimeMillis() - currentProductIdStart > 10 * 60 * 1000) {
            return true;
        }
        return false;
    }

    private Integer getMohuProductId(Integer productId, int count, long currentProductIdStart) {
        if (System.currentTimeMillis() - currentProductIdStart > 60 * 1000) {
            return productId + getInterval(count);
        }
        return productId;
    }

    public int getInterval(int i) {
        return i % 3 - 1;
    }
}
