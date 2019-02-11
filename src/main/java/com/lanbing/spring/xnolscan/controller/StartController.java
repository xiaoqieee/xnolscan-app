package com.lanbing.spring.xnolscan.controller;

import com.lanbing.spring.xnolscan.helper.ProductMaxIdHelper;
import com.lanbing.spring.xnolscan.helper.StatusHelper;
import com.lanbing.spring.xnolscan.helper.XnolHttpRequestHelper;
import com.lanbing.spring.xnolscan.model.ProductIdBO;
import com.lanbing.spring.xnolscan.service.ProductBuyService;
import com.lanbing.spring.xnolscan.service.XnolDetailScanService;
import com.lanbing.spring.xnolscan.service.XnolListScanService;
import com.lanbing.spring.xnolscan.thread.DetailScanTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StartController {

    @Autowired
    private XnolListScanService xnolListScanService;

    @Autowired
    private XnolDetailScanService xnolDetailScanService;

    @Autowired
    private ProductBuyService productBuyService;


    @RequestMapping(path = {"/start/{baseProductId}"})
    public String start(@PathVariable("baseProductId") Integer baseProductId) {

        if (StatusHelper.canStart()) {
            // 设置当前初始ID
            ProductMaxIdHelper.init(baseProductId);

            // 列表搜索
            xnolListScanService.scanListAsync();

            // 根据累加ID搜索
            final int currentMaxId = ProductMaxIdHelper.currentMaxProductId.get() - 1;
            int threadCountPerProductId = 5;
            int step = 10;

            for (int interval = 0; interval < step; interval++) {
                for (int i = 0; i < threadCountPerProductId; i++) {
                    new Thread(new DetailScanTask(xnolDetailScanService, currentMaxId, interval, step), "Thread-detail-scan-" + interval + "-" + i).start();
                }
            }
            return "Started by " + ProductMaxIdHelper.currentMaxProductId.get();
        } else {
            return "Is Running ";
        }
    }

    @RequestMapping(path = {"/stop"})
    public String stop() {
        StatusHelper.stop();
        return "Stoped";
    }

    @RequestMapping(path = {"/detailPage/{productId}"})
    public String detailPage(@PathVariable("productId") Integer productId) {
        try {
            String detailPage = XnolHttpRequestHelper.detailPage(42992383);
            return detailPage;
        } catch (Exception e) {
            return "处理异常";
        }
    }

    @RequestMapping(path = {"/buy/{productId}/{amount}"})
    public String buy(@PathVariable("productId") Integer productId, @PathVariable("amount") Float amount) {
        try {
            productBuyService.doBuy(productId, amount);
            return "购买成功";
        } catch (Exception e) {
            return "处理异常";
        }
    }

    @RequestMapping(path = {"/setStop/{forceStop}"})
    public String setForceStop(@PathVariable("forceStop") Integer forceStop) {
        try {
            ProductIdBO.forceStop = forceStop;
            return "购买成功";
        } catch (Exception e) {
            return "处理异常";
        }
    }
}
