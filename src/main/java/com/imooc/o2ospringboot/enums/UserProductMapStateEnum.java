package com.imooc.o2ospringboot.enums;

public enum UserProductMapStateEnum {
    SUCCESS(1, "操作成功"), INNER_ERROR(-1001, "操作失败"), NULL_USERPRODUCTMAP_ID(-1002, "userProductMapId为空"),
    NULL_USERPRODUCTMAP_INFO(-1003, "userproductmap信息为空");

    private int state;
    private String stateInfo;

    UserProductMapStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /**
     * 依据传入的state返回相应的enum值
     */
    public static UserProductMapStateEnum stateOf(int state) {
        for (UserProductMapStateEnum userProductMapStateEnum : values()) {
            if (userProductMapStateEnum.getState() == state)
                return userProductMapStateEnum;
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
