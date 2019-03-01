package com.lanbing.spring.xnolscan.service;

import com.lanbing.spring.xnolscan.constant.ConfigKey;
import com.lanbing.spring.xnolscan.helper.*;
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

    public String start(Integer baseProductId) {

        if (StatusHelper.canStart()) {

            AppDataLoadHelper.loadData(baseProductId);

            // 获取token
            RequestTokenHelper.producerAsync();


//            xnolListScanService.scanIdListAsync();

            // 详情页处理
            startDetail();

            return "Started by " + ProductMaxIdHelper.currentMaxProductId.get();
        } else {
            return "Is Running ";
        }
    }


    private void startDetail() {
        // 根据累加ID搜索
        final int currentMaxId = ProductMaxIdHelper.currentMaxProductId.get();
        int threadCountPerProductId = Integer.valueOf(BizConfigHelper.get(ConfigKey.DETAIL_THREAD_COUNT, "2"));
        int step = Integer.valueOf(BizConfigHelper.get(ConfigKey.DETAIL_SCAN_STEP, "20"));

        for (int interval = 0; interval < step; interval++) {
            for (int i = 0; i < threadCountPerProductId; i++) {
                new Thread(new DetailScanTask(xnolDetailScanService, currentMaxId, interval, step), "Thread-detail-scan-" + interval + "-" + i).start();
            }
        }
    }
}
