package com.lwt.fastmall.product.controller;

import com.lwt.fastmall.api.bean.PmsProductImage;
import com.lwt.fastmall.api.bean.PmsProductInfo;
import com.lwt.fastmall.api.bean.PmsProductSaleAttr;
import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.client.ProductClient;
import com.lwt.fastmall.api.group.PmsProductInfoGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lwt
 * @date 2019/12/18 10:20
 */
@RestController
@Validated
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductClient productClient;

    @RequestMapping(value = "/listProductInfosByCatalog3Id/{catalog3Id}",method = RequestMethod.GET)
    public Result<List<PmsProductInfo>> listProductInfosByCatalog3Id(@PathVariable("catalog3Id") @NotNull Long catalog3Id){
        return productClient.listProductInfosByCatalog3Id(catalog3Id);
    }

    @RequestMapping(value = "/removeProductByProductId/{productId}",method = RequestMethod.DELETE)
    public Result removeProductByProductId(@PathVariable("productId") @NotNull Long productId){
        return productClient.removeProductByProductId(productId);
    }

    @RequestMapping(value = "/saveProduct",method = RequestMethod.PUT)
    public Result saveProduct(@RequestBody @Validated(PmsProductInfoGroup.SaveProduct.class) PmsProductInfo product){
        return productClient.saveProduct(product);
    }

    @RequestMapping(value = "/listProductImagesByProductId/{productId}",method = RequestMethod.GET)
    public Result<List<PmsProductImage>> listProductImagesByProductId(@PathVariable("productId") @NotNull Long productId){
        return productClient.listProductImagesByProductId(productId);
    }

    @RequestMapping(value = "/listSaleAttrsByProductId/{productId}",method = RequestMethod.GET)
    public Result<List<PmsProductSaleAttr>> listSaleAttrsByProductId(@PathVariable("productId") @NotNull Long productId){
        return productClient.listSaleAttrsByProductId(productId);
    }
}
