package com.lwt.fastmall.product.controller;

import com.lwt.fastmall.api.bean.PmsSkuInfo;
import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.constant.CodeEnum;
import com.lwt.fastmall.api.group.PmsSkuInfoGroup;
import com.lwt.fastmall.common.util.ObjectUtils;
import com.lwt.fastmall.common.util.ResultUtils;
import com.lwt.fastmall.product.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lwt
 * @date 2019/12/17 23:09
 */
@RestController
@Validated
public class SkuController {

    @Autowired
    private SkuService skuService;

    @RequestMapping(value = "/listSkuInfosByProductId/{productId}",method = RequestMethod.GET)
    public Result<List<PmsSkuInfo>> listSkuInfosByProductId(@PathVariable("productId") @NotNull Long productId){
        List<PmsSkuInfo> pmsSkuInfos = skuService.listSkuInfosByProductId(productId);
        if (ObjectUtils.isBlank(pmsSkuInfos)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(pmsSkuInfos);
    }

    @RequestMapping(value = "/removeSkuBySkuId/{skuId}",method = RequestMethod.DELETE)
    public Result removeSkuBySkuId(@PathVariable("skuId") @NotNull Long skuId){
        boolean b = skuService.removeSkuBySkuId(skuId);
        if (b){
            return ResultUtils.success();
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

    @RequestMapping(value = "/saveSku",method = RequestMethod.PUT)
    public Result saveSku(@RequestBody @Validated({PmsSkuInfoGroup.SaveSku.class}) PmsSkuInfo sku){
        boolean saveSku = skuService.saveSku(sku);
        if (saveSku){
            return ResultUtils.success();
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

    @RequestMapping(value = "/listSkuInfosGroupByProductId",method = RequestMethod.GET)
    public Result<List<PmsSkuInfo>> listSkuInfosGroupByProductId(){
        List<PmsSkuInfo> pmsSkuInfos = skuService.listSkuInfosGroupByProductId();
        if (ObjectUtils.isBlank(pmsSkuInfos)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(pmsSkuInfos);
    }

    @RequestMapping(value = "/listSkusByProductId/{productId}",method = RequestMethod.GET)
    public Result<List<PmsSkuInfo>> listSkusByProductId(@PathVariable("productId") @NotNull Long productId){
        List<PmsSkuInfo> pmsSkuInfos = skuService.listSkusByProductId(productId);
        if (ObjectUtils.isBlank(pmsSkuInfos)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(pmsSkuInfos);
    }

    @RequestMapping(value = "/getSkuInfoById/{skuId}",method = RequestMethod.GET)
    public Result<PmsSkuInfo> getSkuInfoById(@PathVariable("skuId") @NotNull Long skuId){
        PmsSkuInfo pmsSkuInfo = skuService.getSkuInfoById(skuId);
        if (ObjectUtils.isBlank(pmsSkuInfo)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(pmsSkuInfo);
    }
}
