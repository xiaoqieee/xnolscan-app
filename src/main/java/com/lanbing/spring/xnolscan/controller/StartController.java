package com.lanbing.spring.xnolscan.controller;

import com.lanbing.spring.xnolscan.helper.*;
import com.lanbing.spring.xnolscan.model.Product;
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
import java.util.List;


@RestController
public class StartController {

    @Autowired
    private ScanStartService scanStartService;

    @Autowired
    private ProductBuyService productBuyService;

    @RequestMapping(path = {"/start/{baseProductId}"})
    public String start(@PathVariable("baseProductId") Integer baseProductId) {

        return scanStartService.start(baseProductId);

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


    @RequestMapping(path = {"/loadData"})
    public String loadConfig() {
        AppDataLoadHelper.loadData(0);
        return "处理成功";
    }

    @RequestMapping(path = {"/isLogin"})
    public String isLogin() {
        try {
            List<Product> productList = XnolHttpRequestHelper.getProductList();
            return null != productList && productList.size() > 0 ? "已经登录" : "登录失效";
        } catch (Exception e) {
        }
        return "登录异常";
    }
}
