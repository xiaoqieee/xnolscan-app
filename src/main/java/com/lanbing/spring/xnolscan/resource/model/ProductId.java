package com.lanbing.spring.xnolscan.resource.model;

import java.util.Date;

public class ProductId {

    private Integer id;

    private Integer productId;

    private Date firstTime;

    private Integer getCount;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    public Integer getGetCount() {
        return getCount;
    }

    public void setGetCount(Integer getCount) {
        this.getCount = getCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
