package com.lwt.fastmall.product.controller;

import com.lwt.fastmall.api.bean.PmsProductImage;
import com.lwt.fastmall.api.bean.PmsProductInfo;
import com.lwt.fastmall.api.bean.PmsProductSaleAttr;
import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.constant.CodeEnum;
import com.lwt.fastmall.api.group.PmsProductInfoGroup;
import com.lwt.fastmall.common.util.ObjectUtils;
import com.lwt.fastmall.common.util.ResultUtils;
import com.lwt.fastmall.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lwt
 * @date 2019/12/17 23:00
 */
@RestController
@Validated
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/listProductInfosByCatalog3Id/{catalog3Id}",method = RequestMethod.GET)
    public Result<List<PmsProductInfo>> listProductInfosByCatalog3Id(@PathVariable("catalog3Id") @NotNull Long catalog3Id){
        List<PmsProductInfo> pmsProductInfos = productService.listProductInfosByCatalog3Id(catalog3Id);
        if (ObjectUtils.isBlank(pmsProductInfos)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(pmsProductInfos);
    }

    @RequestMapping(value = "/removeProductByProductId/{productId}",method = RequestMethod.DELETE)
    public Result removeProductByProductId(@PathVariable("productId") @NotNull Long productId){
        boolean b = productService.removeProductByProductId(productId);
        if (b){
            return ResultUtils.success();
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

    @RequestMapping(value = "/saveProduct",method = RequestMethod.PUT)
    public Result saveProduct(@RequestBody @Validated(PmsProductInfoGroup.SaveProduct.class) PmsProductInfo product){
        boolean b = productService.saveProduct(product);
        if (b){
            return ResultUtils.success();
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

    @RequestMapping(value = "/listProductImagesByProductId/{productId}",method = RequestMethod.GET)
    public Result<List<PmsProductImage>> listProductImagesByProductId(@PathVariable("productId") @NotNull Long productId){
        List<PmsProductImage> pmsProductImages = productService.listProductImagesByProductId(productId);
        if (ObjectUtils.isBlank(pmsProductImages)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(pmsProductImages);
    }

    @RequestMapping(value = "/listSaleAttrsByProductId/{productId}",method = RequestMethod.GET)
    public Result<List<PmsProductSaleAttr>> listSaleAttrsByProductId(@PathVariable("productId") @NotNull Long productId){
        List<PmsProductSaleAttr> SaleAttrs = productService.listSaleAttrsByProductId(productId);
        if (ObjectUtils.isBlank(SaleAttrs)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(SaleAttrs);
    }

}
