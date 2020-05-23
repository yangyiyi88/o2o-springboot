package com.imooc.o2ospringboot.service.impl;

import com.imooc.o2ospringboot.service.ProductSellDailyService;
import org.springframework.stereotype.Service;

@Service("productSellDailyService")
public class ProductSellDailyServiceImpl implements ProductSellDailyService {
    @Override
    public void dailyCalculate() {
        System.out.println("Quartz跑起来了！");
    }
}
