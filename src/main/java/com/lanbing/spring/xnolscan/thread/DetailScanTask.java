package com.lanbing.spring.xnolscan.thread;

import com.lanbing.spring.xnolscan.model.ProductIdBO;
import com.lanbing.spring.xnolscan.service.XnolDetailScanService;

public class DetailScanTask implements Runnable {

    private XnolDetailScanService xnolDetailScanService;
    private Integer baseProductId;
    private int interval;
    private int step;

    public DetailScanTask(XnolDetailScanService xnolDetailScanService, Integer baseProductId, int interval, int step) {
        this.xnolDetailScanService = xnolDetailScanService;
        this.baseProductId = baseProductId;
        this.interval = interval;
        this.step = step;
    }

    @Override
    public void run() {
        ProductIdBO productIdBO = new ProductIdBO(baseProductId, interval, step);
        xnolDetailScanService.doDetail(productIdBO);
    }
}
