package com.imooc.o2ospringboot.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PathUtil {
    public static String separator = System.getProperty("file.separator");

    private static String winBasePath;

    private static String linuxBasePath;

    private static String shopImagePath;

    @Value("${win.base.path}")
    public void setWinBasePath(String winBasePath) {
        PathUtil.winBasePath = winBasePath;
    }

    @Value("${linux.base.path}")
    public void setLinuxBasePath(String linuxBasePath) {
        PathUtil.linuxBasePath = linuxBasePath;
    }

    @Value("${shop.image.path}")
    public void setShopImagePath(String shopImagePath) {
        PathUtil.shopImagePath = shopImagePath;
    }

    public static String getImageBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = winBasePath;
        } else {
            basePath = linuxBasePath;
        }
        return basePath.replace("/", separator);
    }

    public static String getShopImagePath(Long shopId) {
        String imagePath = shopImagePath + shopId + separator;
        return imagePath.replace("/", separator);
    }
}
