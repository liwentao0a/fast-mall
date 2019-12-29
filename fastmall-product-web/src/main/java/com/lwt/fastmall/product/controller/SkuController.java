package com.lwt.fastmall.product.controller;

import com.lwt.fastmall.api.bean.PmsSkuInfo;
import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.client.ProductClient;
import com.lwt.fastmall.api.group.PmsSkuInfoGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lwt
 * @date 2019/12/18 10:22
 */
@RestController
@Validated
@CrossOrigin
public class SkuController {

    @Autowired
    private ProductClient productClient;

    @RequestMapping(value = "/listSkuInfosByProductId/{productId}",method = RequestMethod.GET)
    public Result<List<PmsSkuInfo>> listSkuInfosByProductId(@PathVariable("productId") @NotNull Long productId){
        return productClient.listSkuInfosByProductId(productId);
    }

    @RequestMapping(value = "/removeSkuBySkuId/{skuId}",method = RequestMethod.DELETE)
    public Result removeSkuBySkuId(@PathVariable("skuId") @NotNull Long skuId){
        return productClient.removeSkuBySkuId(skuId);
    }

    @RequestMapping(value = "/saveSku",method = RequestMethod.PUT)
    public Result saveSku(@RequestBody @Validated({PmsSkuInfoGroup.SaveSku.class}) PmsSkuInfo sku){
        return productClient.saveSku(sku);
    }

    @RequestMapping(value = "/listSkuInfosGroupByProductId",method = RequestMethod.GET)
    public Result<List<PmsSkuInfo>> listSkuInfosGroupByProductId(){
        return productClient.listSkuInfosGroupByProductId();
    }

    @RequestMapping(value = "/listSkusByProductId/{productId}",method = RequestMethod.GET)
    public Result<List<PmsSkuInfo>> listSkusByProductId(@PathVariable("productId") @NotNull Long productId){
        return productClient.listSkusByProductId(productId);
    }
}
