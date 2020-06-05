package com.imooc.o2ospringboot.web.shopadmin;

import com.imooc.o2ospringboot.dto.EchartSeries;
import com.imooc.o2ospringboot.dto.EchartXAxis;
import com.imooc.o2ospringboot.dto.UserProductMapExecution;
import com.imooc.o2ospringboot.entity.Product;
import com.imooc.o2ospringboot.entity.ProductSellDaily;
import com.imooc.o2ospringboot.entity.Shop;
import com.imooc.o2ospringboot.entity.UserProductMap;
import com.imooc.o2ospringboot.service.ProductSellDailyService;
import com.imooc.o2ospringboot.service.UserProductMapService;
import com.imooc.o2ospringboot.util.HttpServletRequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/shopadmin")
public class UserProductManagementController {
    @Resource
    private UserProductMapService userProductMapService;
    @Resource
    private ProductSellDailyService productSellDailyService;

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
            modelMap.put("errMsg", "empty pageIndex or pageSize or shopId");
        }
        return modelMap;
    }

    @GetMapping("/listproductselldailyinfobyshop")
    @ResponseBody
    private Map<String, Object> listProductSellDailyInfoByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //从session获取当前店铺
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        //空值校验，主要确保shopId不为空
        if (currentShop != null && currentShop.getShopId() != null) {
            //添加查询调价
            ProductSellDaily productSellDailyCondition = new ProductSellDaily();
            productSellDailyCondition.setShop(currentShop);
            //获取查询时间范围
            Calendar calendar  = Calendar.getInstance();
            //获取昨天的日期
            calendar.add(Calendar.DATE, -1);
            Date endTime = calendar.getTime();
            //获取七天前的日期
            calendar.add(Calendar.DATE, -6);
            Date beginTime = calendar.getTime();
            //根据传入的查询条件获取该店铺的商品销售情况
            List<ProductSellDaily> productSellDailyList = productSellDailyService.listProductSellDaily(productSellDailyCondition, beginTime, endTime);
            //指定日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //商品名列表，保证唯一性
            LinkedHashSet<String> legendData = new LinkedHashSet<String>();
            //x轴数据
            LinkedHashSet<String> xData = new LinkedHashSet<String>();
            //定义series
            List<EchartSeries> series = new ArrayList<EchartSeries>();
            //每种商品的日销量列表
            List<Integer> totalList = new ArrayList<Integer>();
            //当前商品名，默认为空
            String currentProductName = "";
            for (int i = 0; i < productSellDailyList.size(); i++) {
                ProductSellDaily productSellDaily = productSellDailyList.get(i);
                //自动去重
                legendData.add(productSellDaily.getProduct().getProductName());
                xData.add(sdf.format(productSellDaily.getCreateTime()));
                if (!productSellDaily.getProduct().getProductName().equals(currentProductName) && !currentProductName.isEmpty()) {
                    //如果currentProductName不为空，且新获取的商品名不等于currentProductName(上一条记录的商品名),且未遍历到列表的末尾
                    //则是遍历到下一个商品的日销售信息了，将前一轮遍历的信息放入series当中，包括了商品名以及与商品名对应的统计日期以及当日销量
                    EchartSeries echartSeries = new EchartSeries();
                    echartSeries.setName(currentProductName);
                    echartSeries.setData(totalList.subList(0, totalList.size()));
                    series.add(echartSeries);
                    //重置totalList，准备添加新一个商品的销量列表
                    totalList = new ArrayList<Integer>();
                    //变换currentProductName为当前获取的新的productName
                    currentProductName = productSellDaily.getProduct().getProductName();
                    //添加新一个商品的销量列表
                    totalList.add(productSellDaily.getTotal());
                } else {
                    //第一次循环currentProductName为空,每循环一次，记录当前的currentProductId
                    currentProductName = productSellDaily.getProduct().getProductName();
                    //如果还是当前的productId则继续添加新值
                    totalList.add(productSellDaily.getTotal());
                }
                //队列之末，需要将最后的一个商品销量信息也添加上
                if (i == productSellDailyList.size() - 1) {
                    EchartSeries echartSeries = new EchartSeries();
                    echartSeries.setName(currentProductName);
                    echartSeries.setData(totalList.subList(0, totalList.size()));
                    series.add(echartSeries);
                }
            }
            modelMap.put("series", series);
            modelMap.put("legendData", legendData);
            //拼接出xAxis
            List<EchartXAxis> xAxis = new ArrayList<EchartXAxis>();
            EchartXAxis echartXAxis = new EchartXAxis();
            echartXAxis.setData(xData);
            xAxis.add(echartXAxis);
            modelMap.put("xAxis", xAxis);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }
}
