package com.lanbing.spring.xnolscan.service;

import com.lanbing.spring.xnolscan.constant.ConfigKey;
import com.lanbing.spring.xnolscan.helper.BizConfigHelper;
import com.lanbing.spring.xnolscan.helper.ProductCanBuyHelper;
import com.lanbing.spring.xnolscan.helper.RequestTokenHelper;
import com.lanbing.spring.xnolscan.helper.XnolHttpRequestHelper;
import com.lanbing.spring.xnolscan.model.Product;
import com.lanbing.spring.xnolscan.util.DataToDiscUtils;
import com.lanbing.spring.xnolscan.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class ProductBuyService extends BaseService {

    private static ExecutorService poolExecutor = Executors.newFixedThreadPool(3);

    @Autowired
    private LoginUserService loginUserService;

    public void checkBuy(Integer type, Product t) {
        try {
            if (!ProductCanBuyHelper.canBuy(t)) {
                return;
            }
            DataToDiscUtils.saveToRecord(loginUserService.getLoginUser(), type, t);
            doBuy(t.getProductId(), t.getLeftAmount());
        } catch (Exception e) {
            logger.error("[购买]-购买异常,productId:{}", t.getProductId(), e);
        }
    }

    public void doBuy(final Integer productId, final BigDecimal amount) {
        poolExecutor.submit(() -> {
            try {
                logger.info("[购买接口]-开始进行购买.productId:{}, amount:{}", productId, amount);

                String buyResultPage = doBuyD(productId, amount);

                doBuyResult(productId, buyResultPage);
            } catch (Exception e) {
                logger.error("[购买接口]-购买异常.productId:{}", productId, e);
            }
        });
    }

    private String doBuy(Integer productId, BigDecimal amount, String detailPage) throws Exception {
        String tokenName = TokenUtils.getTokenName(detailPage);
        String tokenValue = TokenUtils.getTokenValue(detailPage);
        String result = XnolHttpRequestHelper.submitBuy(productId, amount, tokenName, tokenValue);
        logger.info("[购买接口]-获取到购买结果.productId:{},result:{}", productId, result);
        return result;
    }

    private String doBuyD(Integer productId, BigDecimal amount) throws Exception {
        String[] token = RequestTokenHelper.getToken();
        String tokenName = token[0];
        String tokenValue = token[1];
        String result = XnolHttpRequestHelper.submitBuy(productId, amount, tokenName, tokenValue);
        logger.info("[购买接口]-获取到购买结果.productId:{},result:{}", productId, result);
        return result;
    }

    private void doBuyResult(Integer productId, String resultPage) {
        String[] result = ProductBuyService.getResult(resultPage);
        String loginUser = loginUserService.getLoginUser();
        if (null == result) {
            DataToDiscUtils.saveByResult(loginUser, productId, new String[]{"-1", "结果异常"});
        } else {
            DataToDiscUtils.saveByResult(loginUser, productId, result);
        }
    }

    public static String[] getResult(String resultPage) {
        if (!isValidResult(resultPage)) {
            return null;
        }
        return parseResult(resultPage);
    }

    private static String[] parseResult(String resultPage) {
        int statusLine = Integer.valueOf(BizConfigHelper.get(ConfigKey.BUY_RESULT_STATUS_LINE, "375"));
        int descLine = Integer.valueOf(BizConfigHelper.get(ConfigKey.BUY_RESULT_DESC_LINE, "380"));
        String[] lines = resultPage.split("\r\n");
        String statusStr = lines[statusLine - 1];
        String messageStr = lines[descLine - 1];
        statusStr = StringUtils.trimAllWhitespace(statusStr);
        String status = statusStr.split("\"")[1];
        messageStr = StringUtils.trimAllWhitespace(messageStr);
        String message = messageStr.split("\"")[1];
        return new String[]{status, message};
    }

    private static boolean isValidResult(String resultPage) {
        if (StringUtils.isEmpty(resultPage)) {
            return false;
        }
        return resultPage.indexOf("页面一定被任性的小牛君藏起来了，真是拿它没办法") < 0
                && resultPage.indexOf("errorDetails:") > -1;
    }
}
