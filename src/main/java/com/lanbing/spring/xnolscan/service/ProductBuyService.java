package com.lanbing.spring.xnolscan.service;

import com.lanbing.spring.xnolscan.helper.ProductCanBuyHelper;
import com.lanbing.spring.xnolscan.helper.XnolHttpRequestHelper;
import com.lanbing.spring.xnolscan.model.Product;
import com.lanbing.spring.xnolscan.util.DataToDiscUtils;
import com.lanbing.spring.xnolscan.util.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class ProductBuyService extends BaseService {

    private static ExecutorService poolExecutor = Executors.newFixedThreadPool(3);


    public void checkBuy(Integer type, Product t) {
        try {
            if (!ProductCanBuyHelper.canBuy(t)) {
                return;
            }
            DataToDiscUtils.saveToRecord(type, t);
            doBuy(t.getProductId(), t.getLeftAmount());
        } catch (Exception e) {
            logger.error("[购买]-购买异常,productId:{}", t.getProductId(), e);
        }
    }

    public void doBuy(final Integer productId, final Float amount) {
        poolExecutor.submit(() -> {
            try {
                String detailPage = XnolHttpRequestHelper.detailPage(productId);
                logger.info("[购买接口]-获取Token.productId:{}, detailPage:{}", productId, detailPage);
                String buyResultPage = doBuy(productId, amount, detailPage);

                doBuyResult(productId, buyResultPage);
            } catch (Exception e) {
                logger.error("[购买接口]-购买异常.productId:{}", productId, e);
            }
        });
    }

    private String doBuy(Integer productId, Float amount, String detailPage) throws Exception {
        String tokenName = TokenUtils.getTokenName(detailPage);
        String tokenValue = TokenUtils.getTokenValue(detailPage);
        String result = XnolHttpRequestHelper.submitBuy(productId, amount, tokenName, tokenValue);
        logger.info("[购买接口]-获取到购买结果.productId:{},result:{}", productId, result);
        return result;
    }

    private void doBuyResult(Integer productId, String resultPage) {
        String[] result = ProductBuyService.getResult(resultPage);
        if (null == result) {
            DataToDiscUtils.saveByResult(productId, new String[]{"-1", "结果异常"});
        } else {
            DataToDiscUtils.saveByResult(productId, result);
        }
    }

    public static String[] getResult(String resultPage) {
        if (!isValidResult(resultPage)) {
            return null;
        }
        return parseResult(resultPage);
    }

    private static String[] parseResult(String resultPage) {
        String lines[] = resultPage.split("\r\n");
        String statusStr = lines[376];
        String messageStr = lines[381];
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
