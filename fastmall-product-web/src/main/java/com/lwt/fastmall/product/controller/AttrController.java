package com.lwt.fastmall.product.controller;

import com.lwt.fastmall.api.bean.PmsBaseAttrInfo;
import com.lwt.fastmall.api.bean.PmsBaseAttrValue;
import com.lwt.fastmall.api.bean.PmsBaseSaleAttr;
import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.client.ProductClient;
import com.lwt.fastmall.api.group.PmsBaseAttrInfoGroup;
import com.lwt.fastmall.api.group.PmsBaseAttrValueGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lwt
 * @date 2019/12/18 10:17
 */
@RestController
@Validated
@CrossOrigin
public class AttrController {

    @Autowired
    private ProductClient productClient;

    @RequestMapping(value = "/listBaseAttrInfosByCatalog3Id/{catalog3Id}",method = RequestMethod.GET)
    public Result<List<PmsBaseAttrInfo>> listBaseAttrInfosByCatalog3Id(@PathVariable("catalog3Id") @NotNull Long catalog3Id){
        return productClient.listBaseAttrInfosByCatalog3Id(catalog3Id);
    }

    @RequestMapping(value = "/logicRemoveBaseAttrInfoById/{id}",method = RequestMethod.DELETE)
    public Result logicRemoveBaseAttrInfoById(@PathVariable("id") @NotNull Long id){
        return productClient.logicRemoveBaseAttrInfoById(id);
    }

    @RequestMapping(value = "/listBaseAttrValuesByAttrId/{attrId}",method = RequestMethod.GET)
    public Result<List<PmsBaseAttrValue>> listBaseAttrValuesByAttrId(@PathVariable("attrId") @NotNull Long attrId){
        return productClient.listBaseAttrValuesByAttrId(attrId);
    }

    @RequestMapping(value = "/logicRemoveBaseAttrValueById/{id}",method = RequestMethod.DELETE)
    public Result logicRemoveBaseAttrValueById(@PathVariable("id") @NotNull Long id){
        return productClient.logicRemoveBaseAttrValueById(id);
    }

    @RequestMapping(value = "/saveBaseAttrValue",method = RequestMethod.PUT)
    public Result saveBaseAttrValue(@RequestBody @Validated(PmsBaseAttrValueGroup.SaveBaseAttrValue.class) PmsBaseAttrValue pmsBaseAttrValue){
        return productClient.saveBaseAttrValue(pmsBaseAttrValue);
    }

    @RequestMapping(value = "/saveBaseAttrInfo",method = RequestMethod.PUT)
    public Result saveBaseAttrInfo(@RequestBody @Validated(PmsBaseAttrInfoGroup.SaveBaseAttrInfo.class) PmsBaseAttrInfo pmsBaseAttrInfo){
        return productClient.saveBaseAttrInfo(pmsBaseAttrInfo);
    }

    @RequestMapping(value = "/listBaseSaleAttrs",method = RequestMethod.GET)
    public Result<List<PmsBaseSaleAttr>> listBaseSaleAttrs(){
        return productClient.listBaseSaleAttrs();
    }

    @RequestMapping(value = "/listBaseAttrsByCatalog3Id/{catalog3Id}",method = RequestMethod.GET)
    public Result<List<PmsBaseAttrInfo>> listBaseAttrsByCatalog3Id(@PathVariable("catalog3Id") @NotNull Long catalog3Id){
        return productClient.listBaseAttrsByCatalog3Id(catalog3Id);
    }
}
