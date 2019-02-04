package com.lanbing.spring.xnolscan.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lanbing.spring.xnolscan.model.Product;
import org.springframework.util.StringUtils;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HttpResponseParseHelper {

    public static List<Product> parseListJson(String oriListData) {
        if (StringUtils.isEmpty(oriListData)) {
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(oriListData);
        if (null == jsonObject) {
            return null;
        }
        JSONObject data = (JSONObject) jsonObject.get("data");
        if (null == data) {
            return null;
        }
        JSONArray array = (JSONArray) ((JSONObject) jsonObject.get("data")).get("data");
        if (null == array) {
            return null;
        }
        List<Product> productList = new ArrayList<>(10);
        for (Object o : array) {
            JSONObject product = (JSONObject) o;
            productList.add(parseProduct(product));
        }
        productList.sort(Comparator.comparing((product -> -product.getTargetRatio())));
        return productList;
    }

    public static Product parseDetailJson(String oriDetailData) {
        if (StringUtils.isEmpty(oriDetailData)) {
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(oriDetailData);
        if (null == jsonObject) {
            return null;
        }
        JSONArray array = (JSONArray) (jsonObject.get("data"));
        if (null == array || array.size() <= 0) {
            return null;
        }
        JSONObject product = (JSONObject) array.get(0);
        return parseProduct(product);
    }

    public static Product parseDetailJson2(String oriDetailData) {
        if (StringUtils.isEmpty(oriDetailData)) {
            return null;
        }
        if (oriDetailData.indexOf("小牛家开party，来的朋友太多，有点忙不过来了") > -1) {
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(oriDetailData);
        if (null == jsonObject) {
            return null;
        }
        JSONObject product = (JSONObject) (jsonObject.get("product"));
        if (null == product) {
            return null;
        }
        return parseProduct(product);
    }


    private static Product parseProduct(JSONObject product) {
        Float ratio = Float.valueOf(product.get("tsfProfitAmountRatio").toString());
        Integer productId = Integer.valueOf(product.get("productId").toString());
        Integer productTerm = Integer.valueOf(product.get("productTerm").toString());
        Object leftAmountStr = product.get("leftAmount");
        Float leftAmount;
        if (null != leftAmountStr) {
            leftAmount = Float.valueOf(leftAmountStr.toString());
        } else {
            leftAmount = Float.valueOf(product.get("productAmount").toString()) - Float.valueOf(product.get("raisedAmount").toString());
        }

        float targetRatio = calculate(ratio, productTerm);
        Product temp = new Product(productId, ratio, targetRatio, productTerm, leftAmount);
        return temp;
    }

    private static float calculate(Float ratio, Integer productTerm) {
        if (productTerm > 360) {
            return 0;
        }
        float rate = ratio * 365 / productTerm;
        return rate;
    }


}
