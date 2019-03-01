package com.lanbing.spring.xnolscan.helper;

/**
 * @author xn025665
 * @date Create on 2019/3/1 13:57
 */
public class AppDataLoadHelper {

    public static void loadData(Integer baseId) {

        BizConfigHelper.load();

        CookieHelper.initCookie();

        if (baseId > 0) {
            ProductMaxIdHelper.init(baseId, false);
        }
    }
}
