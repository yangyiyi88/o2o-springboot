package com.imooc.o2ospringboot.dao;

import com.imooc.o2ospringboot.entity.Product;
import com.imooc.o2ospringboot.entity.ProductSellDaily;
import com.imooc.o2ospringboot.entity.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductSellDailyDaoTest {
    @Resource
    private ProductSellDailyDao productSellDailyDao;

    @Test
    public void testInsertProductSellDaily() {
        //创建商品日销量统计
        int effectedNum = productSellDailyDao.insertProductSellDaily();
        assertEquals(5, effectedNum);
    }

    @Test
    public void testQueryProductSellDailyList() {
        ProductSellDaily productSellDailyCondition = new ProductSellDaily();
        //根据店铺查询
        Shop shop = new Shop();
        shop.setShopId(29l);
        productSellDailyCondition.setShop(shop);
        List<ProductSellDaily> productSellDailyList = productSellDailyDao.queryProductSellDailyList(productSellDailyCondition, null, null);
        assertEquals(3, productSellDailyList.size());
        //叠加商品名模糊查询
        Product product = new Product();
        product.setProductName("优质");
        productSellDailyCondition.setProduct(product);
        productSellDailyList = productSellDailyDao.queryProductSellDailyList(productSellDailyCondition, null, null);
        assertEquals(1, productSellDailyList.size());
    }
}
