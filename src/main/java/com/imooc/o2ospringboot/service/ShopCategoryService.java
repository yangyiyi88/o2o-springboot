package com.imooc.o2ospringboot.service;

import com.imooc.o2ospringboot.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    public static final String SCLISTKEY = "shopcategorylist";
    /**
     * 根据查询条件获取shopCategory列表
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
