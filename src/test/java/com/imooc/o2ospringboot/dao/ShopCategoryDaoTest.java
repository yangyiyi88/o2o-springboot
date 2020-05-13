package com.imooc.o2ospringboot.dao;

import com.imooc.o2ospringboot.entity.ShopCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCategoryDaoTest {
    @Resource
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory() {
//        List<ShopCategory> list = shopCategoryDao.queryShopCategory(new ShopCategory());
//        assertEquals(13, list.size());

        ShopCategory shopCategory = new ShopCategory();
        ShopCategory parentshopCategory = new ShopCategory();
        parentshopCategory.setShopCategoryId(12L);
        shopCategory.setParent(parentshopCategory);
        System.out.println("shopCategory parent id :" + shopCategory.getParent().getShopCategoryId());
        List<ShopCategory> list2 = shopCategoryDao.queryShopCategoryList(shopCategory);
        assertEquals(3, list2.size());

        List<ShopCategory> list3 = shopCategoryDao.queryShopCategoryList(null);
        assertEquals(6, list3.size());
    }
}
