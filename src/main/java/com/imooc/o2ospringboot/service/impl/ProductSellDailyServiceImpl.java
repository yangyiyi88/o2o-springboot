package com.imooc.o2ospringboot.service.impl;

import com.imooc.o2ospringboot.dao.ProductSellDailyDao;
import com.imooc.o2ospringboot.entity.ProductSellDaily;
import com.imooc.o2ospringboot.service.ProductSellDailyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("productSellDailyService")
public class ProductSellDailyServiceImpl implements ProductSellDailyService {
    private static final Logger logger = LoggerFactory.getLogger(ProductSellDailyServiceImpl.class);
    @Resource
    private ProductSellDailyDao productSellDailyDao;

    @Override
    public void dailyCalculate() {
        logger.info("Quartz启动了！");
        //统计在tb_user_product_map里面产生销量的每个店铺的各件商品的日销量
        productSellDailyDao.insertProductSellDaily();
    }

    @Override
    public List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDailyCondition, Date beginTime, Date endTime) {
        return productSellDailyDao.queryProductSellDailyList(productSellDailyCondition, beginTime, endTime);
    }
}
