package com.lanbing.spring.xnolscan.service;


import com.lanbing.spring.xnolscan.helper.StatusHelper;
import org.springframework.stereotype.Service;


@Service
public class XnolListScanService extends XnolProductScanHelper {

    public void scanListAsync() {
        new Thread(() -> {
            scanList();
        },"Thread-list-scan").start();
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
}
