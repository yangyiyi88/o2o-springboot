package com.imooc.o2ospringboot.dao;

import com.imooc.o2ospringboot.entity.UserProductMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserProductMapDao {
    /**
     * 添加一条用户购买商品的记录
     *
     * @param userProductMap
     * @return
     */
    int insertUserProductMap(UserProductMap userProductMap);

    /**
     * 根据查询条件分页返回用户购买商品的记录列表
     *
     * @param userProductMapCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<UserProductMap> queryUserProductMapList(@Param("userProductMapCondition") UserProductMap userProductMapCondition,
                                                 @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 配合queryUserProductMapList根据相同查询条件返回用户购买商品的记录总数
     *
     * @param userProductMapCondition
     * @return
     */
    int queryUserProductMapCount(@Param("userProductMapCondition") UserProductMap userProductMapCondition);
}
