package com.lanbing.spring.xnolscan.service;

import com.lanbing.spring.xnolscan.constant.ConfigKey;
import com.lanbing.spring.xnolscan.helper.BizConfigHelper;
import com.lanbing.spring.xnolscan.helper.ProductMaxIdHelper;
import com.lanbing.spring.xnolscan.util.DataToDiscUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ScanStartRunner implements CommandLineRunner {

    @Autowired
    private ScanStartService scanStartService;

    @Override
    public void run(String... args) throws Exception {

        Integer baseProductId = getBaseProductId();
        scanStartService.start(baseProductId);

    }

    private Integer getBaseProductId() {
        return DataToDiscUtils.getMaxProductId();
    }
}
