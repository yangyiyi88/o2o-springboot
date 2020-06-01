package com.imooc.o2ospringboot.service.impl;

import com.imooc.o2ospringboot.dao.UserProductMapDao;
import com.imooc.o2ospringboot.dto.UserProductMapExecution;
import com.imooc.o2ospringboot.entity.UserProductMap;
import com.imooc.o2ospringboot.service.UserProductMapService;
import com.imooc.o2ospringboot.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userProductMapService")
public class UserProductMapServiceImpl implements UserProductMapService {
    @Resource
    private UserProductMapDao userProductMapDao;

    @Override
    public UserProductMapExecution listUserProductMap(UserProductMap userProductMapCondition, Integer pageIndex, Integer pageSize) {
        //空值判断
        if (userProductMapCondition != null && pageIndex != null && pageSize != null) {
            //页码转行码
            int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
            //根据查询条件分页取出列表
            List<UserProductMap> userProductMapList = userProductMapDao.queryUserProductMapList(userProductMapCondition, rowIndex, pageSize);
            //按照同等的查询条件获取总数
            int count = userProductMapDao.queryUserProductMapCount(userProductMapCondition);
            UserProductMapExecution userProductMapExecution = new UserProductMapExecution();
            userProductMapExecution.setUserProductMapList(userProductMapList);
            userProductMapExecution.setCount(count);
            return userProductMapExecution;
        } else {
            return null;
        }
    }
}
