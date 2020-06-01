package com.imooc.o2ospringboot.dto;

import com.imooc.o2ospringboot.entity.UserProductMap;
import com.imooc.o2ospringboot.enums.UserProductMapStateEnum;

import java.util.List;

public class UserProductMapExecution {
    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //userproductmap数量
    private int count;

    private UserProductMap userProductMap;

    protected List<UserProductMap> userProductMapList;

    public UserProductMapExecution(){

    }
    //操作失败时候使用的构造器
    public UserProductMapExecution(UserProductMapStateEnum userProductMapStateEnum){
        this.state = userProductMapStateEnum.getState();
        this.stateInfo = userProductMapStateEnum.getStateInfo();
    }
    //店铺操作成功时候使用的构造器
    public UserProductMapExecution(UserProductMapStateEnum userProductMapStateEnum, UserProductMap userProductMap){
        this.state = userProductMapStateEnum.getState();
        this.stateInfo = userProductMapStateEnum.getStateInfo();
        this.userProductMap = userProductMap;
    }
    //店铺操作成功时候使用的构造器
    public UserProductMapExecution(UserProductMapStateEnum userProductMapStateEnum, List<UserProductMap> userProductMapList){
        this.state = userProductMapStateEnum.getState();
        this.stateInfo = userProductMapStateEnum.getStateInfo();
        this.userProductMapList = userProductMapList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public UserProductMap getUserProductMap() {
        return userProductMap;
    }

    public void setUserProductMap(UserProductMap userProductMap) {
        this.userProductMap = userProductMap;
    }

    public List<UserProductMap> getUserProductMapList() {
        return userProductMapList;
    }

    public void setUserProductMapList(List<UserProductMap> userProductMapList) {
        this.userProductMapList = userProductMapList;
    }
}
