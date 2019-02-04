package com.lanbing.spring.xnolscan.model;

import com.lanbing.spring.xnolscan.helper.ProductMaxIdHelper;
import com.lanbing.spring.xnolscan.util.DateUtils;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductIdBO {

    public static int forceStop = 10;


    private AtomicInteger custProductId;
    private int interval;
    private int step;


    public ProductIdBO(Integer custProductId, int interval, int step) {
        this.custProductId = new AtomicInteger(custProductId + interval);
        this.interval = interval;
        this.step = step;
    }

    public void resetCustProductId() {
        this.custProductId = new AtomicInteger(ProductMaxIdHelper.currentMaxProductId.get() - interval - 1);
    }

    public int getNextProductId() {
        int realStep = getStep(this.step);
        return this.custProductId.addAndGet(realStep);
    }


    public AtomicInteger getCustProductId() {
        return custProductId;
    }

    public void setCustProductId(AtomicInteger custProductId) {
        this.custProductId = custProductId;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "ProductIdBO{" +
                "custProductId=" + custProductId +
                ", step=" + step +
                '}';
    }

    private int getStep(int step) {
        int hour = DateUtils.getHour(new Date());
        if (hour >= 0 && hour <= 4) {
            return 100;
        }
        if (forceStop > step) {
            return forceStop;
        }
        return step;
    }
}
