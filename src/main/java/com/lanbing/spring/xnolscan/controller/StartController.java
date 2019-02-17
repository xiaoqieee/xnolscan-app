package com.lanbing.spring.xnolscan.controller;

import com.lanbing.spring.xnolscan.helper.*;
import com.lanbing.spring.xnolscan.model.ProductIdBO;
import com.lanbing.spring.xnolscan.service.ProductBuyService;
import com.lanbing.spring.xnolscan.service.ScanStartService;
import com.lanbing.spring.xnolscan.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
public class StartController {

    @Autowired
    private ScanStartService scanStartService;

    @Autowired
    private ProductBuyService productBuyService;

    private final Logger logger = LoggerFactory.getLogger(StartController.class);


    @RequestMapping(path = {"/start/{baseProductId}"})
    public String start(@PathVariable("baseProductId") Integer baseProductId) {

        if (StatusHelper.canStart()) {

            scanStartService.start(baseProductId);

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

    @RequestMapping(path = {"/setStop/{forceStop}"})
    public String setForceStop(@PathVariable("forceStop") Integer forceStop) {
        try {
            ProductIdBO.forceStop = forceStop;
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
}
