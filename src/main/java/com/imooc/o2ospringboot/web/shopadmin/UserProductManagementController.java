package com.imooc.o2ospringboot.web.shopadmin;

import com.imooc.o2ospringboot.dto.UserProductMapExecution;
import com.imooc.o2ospringboot.entity.Product;
import com.imooc.o2ospringboot.entity.Shop;
import com.imooc.o2ospringboot.entity.UserProductMap;
import com.imooc.o2ospringboot.service.UserProductMapService;
import com.imooc.o2ospringboot.util.HttpServletRequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class UserProductManagementController {
    @Resource
    private UserProductMapService userProductMapService;

    @GetMapping("/listuserproductmapsbyshop")
    @ResponseBody
    private Map<String, Object> listUserProductMapsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //从request获取pageIndex和pageSize
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //从session获取当前店铺
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        if (pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
            //拼接查询条件
            UserProductMap userProductMapCondition = new UserProductMap();
            userProductMapCondition.setShop(currentShop);
            //如果前端想按照商品名模糊查询，则传入productNames
            String productName = HttpServletRequestUtil.getString(request, "productName");
            if (productName != null) {
                Product product = new Product();
                product.setProductName(productName);
                userProductMapCondition.setProduct(product);
            }
            //根据传入的查询条件获取该店铺的商品销售情况
            UserProductMapExecution userProductMapExecution = userProductMapService.listUserProductMap(userProductMapCondition, pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("userProductMapList", userProductMapExecution.getUserProductMapList());
            modelMap.put("count", userProductMapExecution.getCount());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageIndex or paseSize or shopId");
        }
        return modelMap;
    }
}
