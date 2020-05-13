package com.imooc.o2ospringboot.dao;

import com.imooc.o2ospringboot.entity.LocalAuth;
import com.imooc.o2ospringboot.entity.PersonInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalAuthDaoTest {
    @Resource
    private LocalAuthDao localAuthDao;

    @Test
    public void testQueryLocalByUserNameAndPwd() {
        LocalAuth auth = localAuthDao.queryLocalByUserNameAndPwd("testbind", "789");
        assertEquals(auth.getPersonInfo().getName(), "测试");
    }

    @Test
    public void testQueryLocalByUserId() {
        LocalAuth auth = localAuthDao.queryLocalByUserId(1l);
        assertEquals(auth.getPersonInfo().getName(), "测试");
    }

    @Test
    public void testInsertLocalAuth() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(8l);
        LocalAuth localAuth = new LocalAuth();
        localAuth.setPersonInfo(personInfo);
        localAuth.setUsername("yk");
        localAuth.setPassword("123");
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());
        int effectedNum = localAuthDao.insertLocalAuth(localAuth);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testUpdateLocalAuth() {
        int effectedNum = localAuthDao.updateLocalAuth(8l, "lx", "789", "123", new Date());
        assertEquals(1, effectedNum);
    }
}
