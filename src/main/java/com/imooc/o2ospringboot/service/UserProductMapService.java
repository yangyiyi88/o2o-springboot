package com.imooc.o2ospringboot.service;

import com.imooc.o2ospringboot.dto.UserProductMapExecution;
import com.imooc.o2ospringboot.entity.UserProductMap;
import com.imooc.o2ospringboot.exception.UserProductMapOperationExecution;


public interface UserProductMapService {
    /**
     * 根据传入的查询条件分页列出用户消费信息列表
     *
     * @param userProductMapCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    UserProductMapExecution listUserProductMap(UserProductMap userProductMapCondition, Integer pageIndex, Integer pageSize);
}
