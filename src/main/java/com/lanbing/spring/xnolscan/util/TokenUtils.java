package com.lanbing.spring.xnolscan.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.StringUtils;

public class TokenUtils {

    private final static String TOKEN_NAME_NAME = "ooh.token.name";
    private final static String TOKEN_VALUE_NAME = "ooh.token.value";

    public static String getTokenName(String detailPage) {
        return getValue(detailPage, TOKEN_NAME_NAME);
    }

    public static String getTokenValue(String detailPage) {
        return getValue(detailPage, TOKEN_VALUE_NAME);
    }

    private static String getValue(String detailPage, String element) {
        if (!contain(detailPage, element)) {
            return null;
        }
        Document mainDoc = Jsoup.parse(detailPage);
        return mainDoc.getElementById(element).attr("value");
    }

    private static boolean contain(String detailPage, String element) {
        if (StringUtils.isEmpty(detailPage)) {
            return false;
        }
        return detailPage.indexOf(element) > -1;
    }
}
