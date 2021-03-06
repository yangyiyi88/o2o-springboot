package com.imooc.o2ospringboot.service.impl;

import com.imooc.o2ospringboot.dao.ProductCategoryDao;
import com.imooc.o2ospringboot.dao.ProductDao;
import com.imooc.o2ospringboot.dto.ProductCategoryExecution;
import com.imooc.o2ospringboot.entity.ProductCategory;
import com.imooc.o2ospringboot.enums.ProductCategoryStateEnum;
import com.imooc.o2ospringboot.exception.ProductCategoryOperationException;
import com.imooc.o2ospringboot.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("productCategoryService")
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Resource
    private ProductCategoryDao productCategoryDao;
    @Resource
    private ProductDao productDao;

    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try{
                int effected = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effected <= 0) {
                    throw new ProductCategoryOperationException("商品类别批量添加失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }catch (Exception e) {
                throw new ProductCategoryOperationException("batchAddProductCategory error " + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Transactional
    public ProductCategoryExecution removeProductCategory(long productCategoryId, long  shopId) throws ProductCategoryOperationException {
        //将此商品类别下的商品的类别id置为空
        try {
            int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
            if (effectedNum <= 0) {
                throw new ProductCategoryOperationException("商品的类别id置为空失败");
            }
        } catch (ProductCategoryOperationException e) {
            throw new ProductCategoryOperationException("updateProductCategoryToNull error:" + e.getMessage());
        }
        //删除该productCategory
        try {
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectedNum <= 0) {
                throw new ProductCategoryOperationException("商品类别删除失败");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (ProductCategoryOperationException e){
            throw new ProductCategoryOperationException("removeProductCategory error:" + e.getMessage());
        }
    }
}
