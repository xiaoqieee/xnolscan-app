package com.lanbing.spring.xnolscan.helper;

import com.lanbing.spring.xnolscan.model.Product;
import com.lanbing.spring.xnolscan.service.BaseService;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class XnolHttpRequestHelper extends BaseService {

    public static Product getProductById(Integer productId) throws Exception {
        String url = "https://www.xiaoniu88.com/product/detail/5/" + productId;
        HttpPost httpPost = HttpRequestHelper.getHttpPost(url, null);
        String data = HttpRequestHandler.doOptimizRequest(httpPost);
        return HttpResponseParseHelper.parseDetailJson(data);
    }

    public static Product getProductById2(Integer productId, boolean login) throws Exception {
        String url = "https://www.xiaoniu88.com/product/common/" + productId + ".json?_=" + System.currentTimeMillis();
        HttpGet httpGet;
        if (login) {
            httpGet = HttpRequestHelper.getHttpGet(url);
        } else {
            httpGet = HttpRequestHelper.getDefaultHttpGet(url);
        }
        String data = HttpRequestHandler.doOptimizRequest(httpGet);
        return HttpResponseParseHelper.parseDetailJson2(data);
    }


    public static List<Product> getProductList() throws Exception {
        String url = "https://www.xiaoniu88.com/product/list?" + System.currentTimeMillis();
        HttpPost httpPost = HttpRequestHelper.getHttpPost(url, HttpParamHelper.buildListParam());
        String data = HttpRequestHandler.doOptimizRequest(httpPost);
        if (null == data) {
            return null;
        }
        return HttpResponseParseHelper.parseListJson(data);
    }

    public static List<Integer> getProductIdList() throws Exception {
        List<Product> productList = getProductList();
        List<Integer> productIds = new ArrayList<>();
        if (null != productList && productList.size() > 0) {
            for (Product product : productList) {
                productIds.add(product.getProductId());
            }
        }
        return productIds;
    }


    public static String detailPage(Integer productId) throws Exception {
        // https://www.xiaoniu88.com/product/bid/detail/42991677        转让标详情页
        // https://www.xiaoniu88.com/product/common/42991677            散标详情页
        HttpGet httpGet = HttpRequestHelper.getHttpGet("https://www.xiaoniu88.com/product/bid/detail/" + productId);
//        httpGet.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
//        httpGet.getParams().setParameter("http.protocol.max-redirects", 110);
        String content = HttpRequestHandler.doOptimizRequest(httpGet);
        return content;
    }

    public static void main(String[] args) throws Exception {
        try {
            String result = detailPage(46343001);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String submitBuy(Integer productId, BigDecimal amount, String tokenName, String tokenValue) throws Exception {
        String url = "https://www.xiaoniu88.com/product/trade/new/buy?productName=%E5%AE%89%E9%80%B8%E4%BF%A1Z20190127-241261&productTerm=120&productType=5";
        List<NameValuePair> params = HttpParamHelper.buildBuyParam(productId, amount, tokenName, tokenValue);
        HttpPost httpPost = HttpRequestHelper.getHttpPost(url, params);
        String result = HttpRequestHandler.doOptimizRequest(httpPost);
        return result;
    }
}
