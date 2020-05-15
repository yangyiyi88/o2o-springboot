package com.imooc.o2ospringboot.service;

import com.imooc.o2ospringboot.entity.Area;
import java.util.List;

public interface AreaService {
    public static final String AREALISTKEY = "arealist";
    List<Area> getAreaList();
}
