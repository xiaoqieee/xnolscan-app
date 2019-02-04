package com.lanbing.spring.xnolscan.service;


import com.lanbing.spring.xnolscan.helper.ProductMaxIdHelper;
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
                boolean isEnd = doDetailLoop(productIdBO);
                if (isEnd) {
                    productIdBO.resetCustProductId();
                }
            } catch (Exception e) {
                logger.error("处理详情异常", e);
            }
        }
    }


    private boolean doDetailLoop(ProductIdBO productIdBO) {
        while (true) {
            if (!StatusHelper.isStarting()) {
                return false;
            }
            try {
                Integer productId = productIdBO.getNextProductId();
                boolean flag = doDetail(productId);
                if (!flag) {
                    return true;
                }
                ProductMaxIdHelper.setCurMaxProductId(productId);
                DateUtils.sleep(500);
            } catch (Exception e) {
                logger.error("循环处理详情异常", e);
            }
        }
    }
}
