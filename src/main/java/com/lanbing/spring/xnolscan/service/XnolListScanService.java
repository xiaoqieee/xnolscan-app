package com.lanbing.spring.xnolscan.service;


import com.lanbing.spring.xnolscan.helper.StatusHelper;
import com.lanbing.spring.xnolscan.util.DateUtils;
import org.springframework.stereotype.Service;


@Service
public class XnolListScanService extends XnolProductScanHelper {

    public void scanListAsync() {
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                scanList();
            }, "Thread-list-scan").start();
            DateUtils.sleep(250);
        }
    }

    public void scanIdListAsync() {
        new Thread(() -> {
            scanIdList();
        }, "Thread-list-scan").start();
    }

    public void scanList() {
        while (true) {
            if (!StatusHelper.isStarting()) {
                logger.info("已经手动关闭");
                break;
            }
            try {
                doPageList();
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }

    public void scanIdList() {
        while (true) {
            if (!StatusHelper.isStarting()) {
                logger.info("已经手动关闭");
                break;
            }
            try {
                doPageList();
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }
    }
}
