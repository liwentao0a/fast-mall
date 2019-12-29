package com.lwt.fastmall.product.controller;

import com.lwt.fastmall.api.bean.PmsBaseCatalog1;
import com.lwt.fastmall.api.bean.PmsBaseCatalog2;
import com.lwt.fastmall.api.bean.PmsBaseCatalog3;
import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.constant.CodeEnum;
import com.lwt.fastmall.common.util.ObjectUtils;
import com.lwt.fastmall.common.util.ResultUtils;
import com.lwt.fastmall.product.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lwt
 * @date 2019/12/17 15:54
 */
@RestController
@Validated
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @RequestMapping(value = "/listBaseCatalog1s",method = RequestMethod.GET)
    public Result<List<PmsBaseCatalog1>> listBaseCatalog1s(){
        List<PmsBaseCatalog1> pmsBaseCatalog1s = catalogService.listBaseCatalog1s();
        if (ObjectUtils.isBlank(pmsBaseCatalog1s)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(pmsBaseCatalog1s);
    }

    @RequestMapping(value = "/listBaseCatalog2sByCatalog1Id/{catalog1Id}",method = RequestMethod.GET)
    public Result<List<PmsBaseCatalog2>> listBaseCatalog2sByCatalog1Id(@PathVariable("catalog1Id") @NotNull Long catalog1Id){
        List<PmsBaseCatalog2> pmsBaseCatalog2s = catalogService.listBaseCatalog2sByCatalog1Id(catalog1Id);
        if (ObjectUtils.isBlank(pmsBaseCatalog2s)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(pmsBaseCatalog2s);
    }

    @RequestMapping(value = "/listBaseCatalog3sByCatalog2Id/{catalog2Id}",method = RequestMethod.GET)
    public Result<List<PmsBaseCatalog3>> listBaseCatalog3sByCatalog2Id(@PathVariable("catalog2Id") @NotNull Long catalog2Id){
        List<PmsBaseCatalog3> pmsBaseCatalog3s = catalogService.listBaseCatalog3sByCatalog2Id(catalog2Id);
        if (ObjectUtils.isBlank(pmsBaseCatalog3s)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(pmsBaseCatalog3s);
    }
}
