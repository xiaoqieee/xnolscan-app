package com.lanbing.spring.xnolscan.service;

import com.lanbing.spring.xnolscan.helper.ProductMaxIdHelper;
import com.lanbing.spring.xnolscan.helper.ScanedProductIdHelper;
import com.lanbing.spring.xnolscan.helper.XnolHttpRequestHelper;
import com.lanbing.spring.xnolscan.model.Product;
import com.lanbing.spring.xnolscan.thread.ThreadPoolManager;
import com.lanbing.spring.xnolscan.util.DataToDiscUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class XnolProductScanHelper extends BaseService {

    @Autowired
    private ProductBuyService productBuyService;


    private ThreadLocal<Integer> detailCount = new ThreadLocal<>();

    protected void doPageList() throws Exception {
        List<Product> productList = XnolHttpRequestHelper.getProductList();
        if (null == productList) {
            return;
        }
        logger.info("productList:" + productList.size());
        for (Product p : productList) {
            try {
                if (ScanedProductIdHelper.add(p.getProductId())) {
                    logger.info(p.getProductId() + ":<<<<<:" + p);
                    productBuyService.checkBuy(DataToDiscUtils.TYPE_LIST, p);
                    ProductMaxIdHelper.setCurMaxProductId(p.getProductId());
                }
            } catch (Exception e) {
                logger.error("循环处理产品ID列表异常", e);
            }
        }
    }
//
//    protected void doPageList2() throws Exception {
//        List<Integer> productIdList = XnolHttpRequestHelper.getProductMap()
//        if (null == productIdList) {
//            return;
//        }
//        for (Integer productId : productIdList) {
//            try {
//                if (ScanedProductIdHelper.add(productId)) {
//                    doDetailAsync(productId);
//                }
//            } catch (Exception e) {
//                logger.error("循环处理产品ID列表异常", e);
//            }
//        }
//    }

    protected void doDetailAsync(final Integer productId) {
        ThreadPoolManager.addTask(() -> {
            try {
                doDetail(productId);
                ProductMaxIdHelper.setCurMaxProductId(productId);
            } catch (Exception e) {
                logger.error("异步处理单个产品ID异常", e);
            }
        });
    }


    protected boolean doDetail(Integer productId) {
        Product p = getRandomProduct(productId);
        logger.info(productId + "[" + ProductMaxIdHelper.currentMaxProductId.get() + "]:>>>>>:" + p);
        if (null == p) {
            return false;
        }
        productBuyService.checkBuy(DataToDiscUtils.TYPE_DETAIL, p);
        ProductMaxIdHelper.setCurMaxProductId(productId);
        return true;
    }

    private Product getRandomProduct(Integer productId) {
        if (detailCount.get() == null) {
            detailCount.set(0);
        } else {
            detailCount.set(detailCount.get() + 1);
        }
        try {
            if (detailCount.get() % 2 == 0) {
                return XnolHttpRequestHelper.getProductById2(productId);
            } else {
                return XnolHttpRequestHelper.getProductById(productId);
            }
        } catch (Exception e) {
            logger.error("获取产品数据异常。", e);
            return null;
        }
    }


}
