package com.imooc.o2ospringboot.web.frontend;

import com.imooc.o2ospringboot.dto.UserProductMapExecution;
import com.imooc.o2ospringboot.entity.PersonInfo;
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
@RequestMapping("/frontend")
public class MyProductController {
    @Resource
    private UserProductMapService userProductMapService;

    @GetMapping("/listuserproductmapsbycustomer")
    @ResponseBody
    private Map<String, Object> listUserProductMapsByCustomer(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //获取分页信息
        Integer pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        Integer pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //从session里获取顾客信息
        PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
        //空值判断
        if (user != null && user.getUserId() != -1 && pageIndex > -1 && pageSize > -1) {
            UserProductMap userProductMapCondition = new UserProductMap();
            userProductMapCondition.setUser(user);
            long shopId = HttpServletRequestUtil.getLong(request, "shopId");
            if (shopId > -1) {
                //若传入店铺信息，则列出某个店铺下该顾客的消费历史
                Shop shop = new Shop();
                shop.setShopId(shopId);
                userProductMapCondition.setShop(shop);
            }
            String productName = HttpServletRequestUtil.getString(request, "productName");
            if (productName != null) {
                //若传入的商品名不为空，则按照商品名模糊查询
                Product product = new Product();
                product.setProductName(productName);
                userProductMapCondition.setProduct(product);
            }
            //根据查询条件分页返回用户消费信息
            UserProductMapExecution userProductMapExecution = userProductMapService.listUserProductMap(userProductMapCondition, pageIndex, pageSize);
            modelMap.put("userProductMapList", userProductMapExecution.getUserProductMapList());
            modelMap.put("count", userProductMapExecution.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("msg", "empty userName or pageIndex or pageSize");
        }
        return modelMap;
    }
}
