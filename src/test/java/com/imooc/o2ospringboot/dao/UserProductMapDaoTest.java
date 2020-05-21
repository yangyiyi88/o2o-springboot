package com.imooc.o2ospringboot.dao;

import com.imooc.o2ospringboot.entity.PersonInfo;
import com.imooc.o2ospringboot.entity.Product;
import com.imooc.o2ospringboot.entity.Shop;
import com.imooc.o2ospringboot.entity.UserProductMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProductMapDaoTest {
    @Resource
    private UserProductMapDao userProductMapDao;

    @Test
    public void testInsertUserProductMap() {
        //创建用户商品映射信息1
        UserProductMap userProductMap = new UserProductMap();
        PersonInfo customer = new PersonInfo();
        customer.setUserId(8l);
        Product product = new Product();
        product.setProductId(3l);
        Shop shop = new Shop();
        shop.setShopId(29l);
        PersonInfo operator = new PersonInfo();
        operator.setUserId(1l);
        userProductMap.setCreateTime(new Date());
        userProductMap.setPoint(2);
        userProductMap.setUser(customer);
        userProductMap.setProduct(product);
        userProductMap.setShop(shop);
        userProductMap.setOperator(operator);
        int effectedNum = userProductMapDao.insertUserProductMap(userProductMap);
        assertEquals(1, effectedNum);
        //创建用户商品映射信息2
        UserProductMap userProductMap2 = new UserProductMap();
        PersonInfo customer2 = new PersonInfo();
        customer2.setUserId(8l);
        Product product2 = new Product();
        product2.setProductId(3l);
        Shop shop2 = new Shop();
        shop2.setShopId(29l);
        PersonInfo operator2 = new PersonInfo();
        operator2.setUserId(1l);
        userProductMap2.setCreateTime(new Date());
        userProductMap2.setPoint(2);
        userProductMap2.setUser(customer2);
        userProductMap2.setProduct(product2);
        userProductMap2.setShop(shop2);
        userProductMap2.setOperator(operator2);
        effectedNum = userProductMapDao.insertUserProductMap(userProductMap2);
        assertEquals(1, effectedNum);
        //创建用户商品映射信息3
        UserProductMap userProductMap3 = new UserProductMap();
        PersonInfo customer3 = new PersonInfo();
        customer3.setUserId(8l);
        Product product3 = new Product();
        product3.setProductId(1l);
        Shop shop3 = new Shop();
        shop3.setShopId(29l);
        PersonInfo operator3 = new PersonInfo();
        operator3.setUserId(1l);
        userProductMap3.setCreateTime(new Date());
        userProductMap3.setPoint(2);
        userProductMap3.setUser(customer3);
        userProductMap3.setProduct(product3);
        userProductMap3.setShop(shop3);
        userProductMap3.setOperator(operator3);
        effectedNum = userProductMapDao.insertUserProductMap(userProductMap3);
        assertEquals(1, effectedNum);
        //创建用户商品映射信息4
        UserProductMap userProductMap4 = new UserProductMap();
        PersonInfo customer4 = new PersonInfo();
        customer4.setUserId(8l);
        Product product4 = new Product();
        product4.setProductId(5l);
        Shop shop4 = new Shop();
        shop4.setShopId(28l);
        PersonInfo operator4 = new PersonInfo();
        operator4.setUserId(1l);
        userProductMap4.setCreateTime(new Date());
        userProductMap4.setPoint(2);
        userProductMap4.setUser(customer4);
        userProductMap4.setProduct(product4);
        userProductMap4.setShop(shop4);
        userProductMap4.setOperator(operator4);
        effectedNum = userProductMapDao.insertUserProductMap(userProductMap4);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testQueryUserProductMapList() {
        UserProductMap userProductMapCondition = new UserProductMap();
        PersonInfo customer = new PersonInfo();
        //按顾客名字模糊查询
        customer.setName("李翔");
        userProductMapCondition.setUser(customer);
        List<UserProductMap> userProductMapList = userProductMapDao.queryUserProductMapList(userProductMapCondition, 0, 2);
        assertEquals(2, userProductMapList.size());
        int count = userProductMapDao.queryUserProductMapCount(userProductMapCondition);
        assertEquals(6, count);
        //叠加店铺去查询
        Shop shop = new Shop();
        shop.setShopId(29l);
        userProductMapCondition.setShop(shop);
        userProductMapList = userProductMapDao.queryUserProductMapList(userProductMapCondition, 0, 3);
        assertEquals(3, userProductMapList.size());
        count = userProductMapDao.queryUserProductMapCount(userProductMapCondition);
        assertEquals(4, count);
        //叠加商品名称模糊查询
        Product product = new Product();
        product.setProductName("暴漫人");
        userProductMapCondition.setProduct(product);
        userProductMapList = userProductMapDao.queryUserProductMapList(userProductMapCondition, 0, 1);
        assertEquals(1, userProductMapList.size());
        count = userProductMapDao.queryUserProductMapCount(userProductMapCondition);
        assertEquals(3, count);
    }
}
