package com.lanbing.spring.xnolscan.service;


import com.lanbing.spring.xnolscan.helper.ProductCanBuyHelper;
import com.lanbing.spring.xnolscan.helper.XnolHttpRequestHelper;
import com.lanbing.spring.xnolscan.model.Product;
import com.lanbing.spring.xnolscan.util.DataToDiscUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class XnolProductScanHelper extends BaseService {

    @Autowired
    private ProductBuyService productBuyService;

    protected void doPageList() throws Exception {
        List<Product> productList = XnolHttpRequestHelper.getProductList();
        if (null == productList) {
            return;
        }
        for (Product p : productList) {
            logger.info(p.getProductId() + ":<<<<<:" + p);
            productBuyService.checkBuy(DataToDiscUtils.TYPE_LIST, p);
        }
    }


    protected boolean doDetail(Integer productId) throws Exception {
        Product p = XnolHttpRequestHelper.getProductById2(productId, false);
        logger.info(productId + ":>>>>>:" + p);
        if (null == p) {
            return false;
        }
//        DataToDiscUtils.saveToProduct(p);
        productBuyService.checkBuy(DataToDiscUtils.TYPE_DETAIL, p);
        return true;
    }


}
