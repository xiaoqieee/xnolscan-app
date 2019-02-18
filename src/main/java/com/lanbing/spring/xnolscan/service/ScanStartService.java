package com.lanbing.spring.xnolscan.service;

import com.lanbing.spring.xnolscan.constant.Constants;
import com.lanbing.spring.xnolscan.helper.HttpHeaderHelper;
import com.lanbing.spring.xnolscan.helper.ProductMaxIdHelper;
import com.lanbing.spring.xnolscan.helper.RequestTokenHelper;
import com.lanbing.spring.xnolscan.helper.StatusHelper;
import com.lanbing.spring.xnolscan.thread.DetailScanTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScanStartService extends BaseService {


    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private XnolListScanService xnolListScanService;

    @Autowired
    private XnolDetailScanService xnolDetailScanService;

    public String start(Integer baseProductId){

        if (StatusHelper.canStart()) {

            // 获取token
            RequestTokenHelper.producerAsync();

            // 设置当前初始ID
            ProductMaxIdHelper.init(baseProductId);

            // 刷新cookie
            HttpHeaderHelper.reSetCookie(loginUserService.getLoginUser());

            xnolListScanService.scanIdListAsync();

            // 详情页处理
            startDetail();

            return "Started by " + ProductMaxIdHelper.currentMaxProductId.get();
        } else {
            return "Is Running ";
        }
    }


    private void startDetail() {
        // 根据累加ID搜索
        final int currentMaxId = ProductMaxIdHelper.currentMaxProductId.get() - 1;
        int threadCountPerProductId = Constants.SCAN_DETAIL_THREAD_COUNT_PER_ID;
        int step = Constants.SCAN_DETAIL_STOP;

        for (int interval = 0; interval < step; interval++) {
            for (int i = 0; i < threadCountPerProductId; i++) {
                new Thread(new DetailScanTask(xnolDetailScanService, currentMaxId, interval, step), "Thread-detail-scan-" + interval + "-" + i).start();
            }
        }
    }
}
