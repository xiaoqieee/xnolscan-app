package com.lanbing.spring.xnolscan.service;

import com.lanbing.spring.xnolscan.constant.ConfigKey;
import com.lanbing.spring.xnolscan.helper.BizConfigHelper;
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
                if (ScanedProductIdHelper.add(p)) {
                    logger.info(p.getProductId() + ":<<<<<:" + p);
                    productBuyService.checkBuy(DataToDiscUtils.TYPE_LIST, p);
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
        ScanedProductIdHelper.add(p);
        return true;
    }

    private Product getRandomProduct(Integer productId) {
        String queryType = BizConfigHelper.get(ConfigKey.DETAIL_QUERY_TYPE, "common,transfer");
        if ("common".equals(queryType)) {
            return queryByCommon(productId);
        } else if ("transfer".equals(queryType)) {
            return queryByTransfer(productId);
        } else {
            if (detailCount.get() == null) {
                detailCount.set(0);
            } else {
                detailCount.set(detailCount.get() + 1);
            }
            if (detailCount.get() % 2 == 0) {
                return queryByCommon(productId);
            } else {
                return queryByTransfer(productId);
            }
        }
    }

    private Product queryByCommon(Integer productId) {
        try {
            return XnolHttpRequestHelper.getProductById2(productId);
        } catch (Exception e) {
            logger.error("获取产品数据异常2。{}", e.getCause().toString());
            return null;
        }
    }

    private Product queryByTransfer(Integer productId) {
        try {
            return XnolHttpRequestHelper.getProductById(productId);
        } catch (Exception e) {
            logger.error("获取产品数据异常1。{}", e.getCause().toString());
            return null;
        }
    }

}
