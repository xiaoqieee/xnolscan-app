package com.lanbing.spring.xnolscan.resource.dao;

import com.lanbing.spring.xnolscan.resource.model.ProductId;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductIdDAO {

    int insert(ProductId productId);

    ProductId getByProductId(Integer productId);

    int increaseCount(Integer productId);
}
