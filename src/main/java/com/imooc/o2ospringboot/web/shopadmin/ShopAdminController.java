package com.imooc.o2ospringboot.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController {
    @GetMapping("/shopoperation")
    public String shopOperation(){
        return "shop/shopoperation";
    }

    @GetMapping("/shoplist")
    public String shopList(){
        return "shop/shoplist";
    }

    @GetMapping("/shopmanagement")
    public String shopManagement(){
        return "shop/shopmanagement";
    }

    @GetMapping("/productcategorymanagement")
    public String productCategoryManagement(){
        return "shop/productcategorymanagement";
    }

    @GetMapping("/productoperation")
    public String productOperation(){
        return "shop/productoperation";
    }

    @GetMapping("/productmanagement")
    public String productManagement(){
        return "shop/productmanagement";
    }

    @GetMapping("/productbuycheck")
    public String productBuyCheck(){
        return "shop/productbuycheck";
    }
}

