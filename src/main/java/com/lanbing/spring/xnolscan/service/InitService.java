package com.lanbing.spring.xnolscan.service;

import com.lanbing.spring.xnolscan.helper.ProductMaxIdHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class InitService extends BaseService implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Max productId initial beginning...");
//        ProductMaxIdHelper.init(45400000);
        logger.info("Max productId initial completed.");
    }
}
