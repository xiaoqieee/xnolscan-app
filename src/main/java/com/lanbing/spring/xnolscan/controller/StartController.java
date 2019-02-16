package com.lanbing.spring.xnolscan.controller;

import com.lanbing.spring.xnolscan.constant.ConfigKey;
import com.lanbing.spring.xnolscan.constant.Constants;
import com.lanbing.spring.xnolscan.helper.*;
import com.lanbing.spring.xnolscan.model.ProductIdBO;
import com.lanbing.spring.xnolscan.service.ProductBuyService;
import com.lanbing.spring.xnolscan.service.XnolDetailScanService;
import com.lanbing.spring.xnolscan.service.XnolListScanService;
import com.lanbing.spring.xnolscan.thread.DetailScanTask;
import com.lanbing.spring.xnolscan.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@RestController
public class StartController {

    @Autowired
    private XnolListScanService xnolListScanService;

    @Autowired
    private XnolDetailScanService xnolDetailScanService;

    @Autowired
    private ProductBuyService productBuyService;

    private final Logger logger = LoggerFactory.getLogger(StartController.class);


    @RequestMapping(path = {"/start/{baseProductId}"})
    public String start(@PathVariable("baseProductId") Integer baseProductId) {

        if (StatusHelper.canStart()) {

            // Token
            RequestTokenHelper.producerAsync();

            // 设置当前初始ID
            ProductMaxIdHelper.init(baseProductId);

            // 刷新cookie
            HttpHeaderHelper.reSetCookie();

//            xnolListScanService.scanIdListAsync();

            // 详情页处理
            startDetail();

            return "Started by " + ProductMaxIdHelper.currentMaxProductId.get();
        } else {
            return "Is Running ";
        }
    }

    private void startDetail() {
        // 根据累加ID搜索
        final int currentMaxId = ProductMaxIdHelper.currentMaxProductId.get();
        int threadCountPerProductId = Integer.valueOf(BizConfigHelper.get(ConfigKey.DETAIL_THREAD_COUNT, "3"));
        int step = Integer.valueOf(BizConfigHelper.get(ConfigKey.DETAIL_SCAN_STEP, "20"));
        List<Thread> threads = new ArrayList<>();
        for (int interval = 0; interval < step; interval++) {
            for (int i = 0; i < threadCountPerProductId; i++) {
                threads.add(new Thread(new DetailScanTask(xnolDetailScanService, currentMaxId, interval, step), "Thread-detail-scan-" + interval + "-" + i));
            }
        }
        for (Thread t : threads) {
            t.start();
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
            String detailPage = XnolHttpRequestHelper.detailPage(productId);
            return detailPage;
        } catch (Exception e) {
            return "处理异常";
        }
    }

    @RequestMapping(path = {"/buy/{productId}/{amount}"})
    public String buy(@PathVariable("productId") Integer productId, @PathVariable("amount") BigDecimal amount) {
        try {
            productBuyService.doBuy(productId, amount);
            return "购买成功";
        } catch (Exception e) {
            return "处理异常";
        }
    }

    @RequestMapping(path = {"/reSetCookie"})
    public String reSetCookie() {
        try {
            // 刷新cookie
            HttpHeaderHelper.reSetCookie();
            return "处理成功";
        } catch (Exception e) {
            return "处理异常";
        }
    }

    @RequestMapping(path = {"/testLog"})
    public String testLog() {
        int i = 1;
        while (true) {
            DateUtils.sleep(1000);
            logger.info("测试日志》》》" + i++);
        }
    }

    @RequestMapping(path = {"/loadConfig"})
    public String loadConfig() {
        BizConfigHelper.load();
        return "处理成功";
    }
}
