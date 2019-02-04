package com.lanbing.spring.xnolscan.model;

import com.lanbing.spring.xnolscan.util.DateUtils;

import java.util.Date;

public class Product {
    private Integer productId;
    private Float ratio;
    private Float targetRatio;
    private Integer productTerm;
    private Float leftAmount;
    private Date nowTime;

    public Product(Integer productId, Float ratio, Float targetRatio, Integer productTerm, Float leftAmount) {
        this.productId = productId;
        this.ratio = ratio;
        this.targetRatio = targetRatio;
        this.productTerm = productTerm;
        this.leftAmount = leftAmount;
        this.nowTime = new Date();
    }

    public Product() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Float getRatio() {
        return ratio;
    }

    public void setRatio(Float ratio) {
        this.ratio = ratio;
    }

    public Float getTargetRatio() {
        return targetRatio;
    }

    public void setTargetRatio(Float targetRatio) {
        this.targetRatio = targetRatio;
    }

    public Integer getProductTerm() {
        return productTerm;
    }

    public void setProductTerm(Integer productTerm) {
        this.productTerm = productTerm;
    }

    public Float getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(Float leftAmount) {
        this.leftAmount = leftAmount;
    }

    public Date getNowTime() {
        return nowTime;
    }

    public void setNowTime(Date nowTime) {
        this.nowTime = nowTime;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", ratio=" + ratio +
                ", targetRatio=" + targetRatio +
                ", productTerm=" + productTerm +
                ", leftAmount=" + leftAmount +
                ", nowTime=" + DateUtils.getTimeString(nowTime) +
                '}';
    }
}
