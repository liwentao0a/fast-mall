package com.lwt.fastmall.product.controller;

import com.lwt.fastmall.api.bean.PmsBaseAttrInfo;
import com.lwt.fastmall.api.bean.PmsBaseAttrValue;
import com.lwt.fastmall.api.bean.PmsBaseSaleAttr;
import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.constant.CodeEnum;
import com.lwt.fastmall.api.group.PmsBaseAttrInfoGroup;
import com.lwt.fastmall.api.group.PmsBaseAttrValueGroup;
import com.lwt.fastmall.common.util.ObjectUtils;
import com.lwt.fastmall.common.util.ResultUtils;
import com.lwt.fastmall.product.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lwt
 * @date 2019/12/17 22:31
 */
@RestController
@Validated
public class AttrController {

    @Autowired
    private AttrService attrService;

    @RequestMapping(value = "/listBaseAttrInfosByCatalog3Id/{catalog3Id}",method = RequestMethod.GET)
    public Result<List<PmsBaseAttrInfo>> listBaseAttrInfosByCatalog3Id(@PathVariable("catalog3Id") @NotNull Long catalog3Id){
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.listBaseAttrInfosByCatalog3Id(catalog3Id);
        if (ObjectUtils.isBlank(pmsBaseAttrInfos)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(pmsBaseAttrInfos);
    }

    @RequestMapping(value = "/logicRemoveBaseAttrInfoById/{id}",method = RequestMethod.DELETE)
    public Result logicRemoveBaseAttrInfoById(@PathVariable("id") @NotNull Long id){
        boolean logicRemoveBaseAttrInfoById = attrService.logicRemoveBaseAttrInfoById(id);
        if (logicRemoveBaseAttrInfoById){
            return ResultUtils.success();
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

    @RequestMapping(value = "/listBaseAttrValuesByAttrId/{attrId}",method = RequestMethod.GET)
    public Result<List<PmsBaseAttrValue>> listBaseAttrValuesByAttrId(@PathVariable("attrId") @NotNull Long attrId){
        List<PmsBaseAttrValue> pmsBaseAttrValues = attrService.listBaseAttrValuesByAttrId(attrId);
        if (ObjectUtils.isBlank(pmsBaseAttrValues)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(pmsBaseAttrValues);
    }

    @RequestMapping(value = "/logicRemoveBaseAttrValueById/{id}",method = RequestMethod.DELETE)
    public Result logicRemoveBaseAttrValueById(@PathVariable("id") @NotNull Long id){
        boolean logicRemoveBaseAttrValueById = attrService.logicRemoveBaseAttrValueById(id);
        if (logicRemoveBaseAttrValueById){
            return ResultUtils.success();
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

    @RequestMapping(value = "/saveBaseAttrValue",method = RequestMethod.PUT)
    public Result saveBaseAttrValue(@RequestBody @Validated(PmsBaseAttrValueGroup.SaveBaseAttrValue.class) PmsBaseAttrValue pmsBaseAttrValue){
        boolean b = attrService.saveBaseAttrValue(pmsBaseAttrValue);
        if (b){
            return ResultUtils.success();
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

    @RequestMapping(value = "/saveBaseAttrInfo",method = RequestMethod.PUT)
    public Result saveBaseAttrInfo(@RequestBody @Validated(PmsBaseAttrInfoGroup.SaveBaseAttrInfo.class) PmsBaseAttrInfo pmsBaseAttrInfo){
        boolean b = attrService.saveBaseAttrInfo(pmsBaseAttrInfo);
        if (b){
            return ResultUtils.success();
        }
        return ResultUtils.result(CodeEnum.RETURN_FALSE);
    }

    @RequestMapping(value = "/listBaseSaleAttrs",method = RequestMethod.GET)
    public Result<List<PmsBaseSaleAttr>> listBaseSaleAttrs(){
        List<PmsBaseSaleAttr> pmsBaseSaleAttrs = attrService.listBaseSaleAttrs();
        if (ObjectUtils.isBlank(pmsBaseSaleAttrs)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(pmsBaseSaleAttrs);
    }

    @RequestMapping(value = "/listBaseAttrsByCatalog3Id/{catalog3Id}",method = RequestMethod.GET)
    public Result<List<PmsBaseAttrInfo>> listBaseAttrsByCatalog3Id(@PathVariable("catalog3Id") @NotNull Long catalog3Id){
        List<PmsBaseAttrInfo> pmsBaseAttrs = attrService.listBaseAttrsByCatalog3Id(catalog3Id);
        if (ObjectUtils.isBlank(pmsBaseAttrs)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(pmsBaseAttrs);
    }
}
