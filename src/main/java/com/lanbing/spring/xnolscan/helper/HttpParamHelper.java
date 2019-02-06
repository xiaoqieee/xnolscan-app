package com.lanbing.spring.xnolscan.helper;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;

public class HttpParamHelper {

    public static List<NameValuePair> buildListParam() {
        List<NameValuePair> list = new LinkedList<>();
        list.add(new BasicNameValuePair("type", "5"));
        list.add(new BasicNameValuePair("termMode", "2"));
        list.add(new BasicNameValuePair("pageNum", "1"));
        list.add(new BasicNameValuePair("pageSize", "20"));
        list.add(new BasicNameValuePair("minTerm", "0"));
        list.add(new BasicNameValuePair("maxTerm", "3"));
        list.add(new BasicNameValuePair("maxAmount", "5000"));
        list.add(new BasicNameValuePair("tsfProfitSort", "0"));
        return list;
    }

    public static List<NameValuePair> buildBuyParam(Integer productId, Float amount, String tokenName, String tokenValue) {
        List<NameValuePair> list = new LinkedList<>();
        list.add(new BasicNameValuePair("productId", String.valueOf(productId)));
        list.add(new BasicNameValuePair("buyAmount", String.valueOf(amount)));
        list.add(new BasicNameValuePair("platform", "1"));
        list.add(new BasicNameValuePair("bonusId", ""));
        list.add(new BasicNameValuePair("addRateId", ""));
        list.add(new BasicNameValuePair("ooh.token.name", tokenName));
        list.add(new BasicNameValuePair("ooh.token.value", tokenValue));
        return list;
    }
}
