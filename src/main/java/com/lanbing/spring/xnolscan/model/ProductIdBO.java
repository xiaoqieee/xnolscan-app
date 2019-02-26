package com.lanbing.spring.xnolscan.model;

import com.lanbing.spring.xnolscan.helper.ProductMaxIdHelper;
import com.lanbing.spring.xnolscan.util.DateUtils;

import java.util.Date;

public class ProductIdBO {


    private Integer custProductId;
    private int interval;
    private int step;


    public ProductIdBO(Integer custProductId, int interval, int step) {
        this.custProductId = custProductId + interval;
        this.interval = interval;
        this.step = step;
    }

    public void resetCustProductId() {
        int realStep = getStep(this.step);
        this.custProductId = ProductMaxIdHelper.currentMaxProductId.get() - interval;
    }

    public int getNextProductId() {
        int realStep = getStep(this.step);
        this.custProductId += realStep;
        return this.custProductId;
    }

    public int getCurrentProductId() {
        return this.custProductId;
    }


    public Integer getCustProductId() {
        return custProductId;
    }

    public void setCustProductId(Integer custProductId) {
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
        if (custProductId < ProductMaxIdHelper.currentMaxProductId.get() - step + 1) {
            return getStep(custProductId, ProductMaxIdHelper.currentMaxProductId.get(), step);
        }
        return step;
    }

    private int getStep(int custProductId, int curMaxId, int step) {
        int result = step;
        int i = 1;
        while (custProductId < curMaxId) {
            result = step * i++;
            custProductId = custProductId + result;
        }
        return result;
    }
}
