package com.lwt.fastmall.product.controller;

import com.lwt.fastmall.api.bean.PmsBaseCatalog1;
import com.lwt.fastmall.api.bean.PmsBaseCatalog2;
import com.lwt.fastmall.api.bean.PmsBaseCatalog3;
import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.client.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lwt
 * @date 2019/12/17 16:07
 */
@RestController
@Validated
@CrossOrigin
public class CatalogController {

    @Autowired
    private ProductClient productClient;

    @RequestMapping(value = "/listBaseCatalog1s",method = RequestMethod.GET)
    public Result<List<PmsBaseCatalog1>> listBaseCatalog1s(){
        return productClient.listBaseCatalog1s();
    }

    @RequestMapping(value = "/listBaseCatalog2sByCatalog1Id/{catalog1Id}",method = RequestMethod.GET)
    public Result<List<PmsBaseCatalog2>> listBaseCatalog2sByCatalog1Id(@PathVariable("catalog1Id") @NotNull Long catalog1Id){
        return productClient.listBaseCatalog2sByCatalog1Id(catalog1Id);
    }

    @RequestMapping(value = "/listBaseCatalog3sByCatalog2Id/{catalog2Id}",method = RequestMethod.GET)
    public Result<List<PmsBaseCatalog3>> listBaseCatalog3sByCatalog2Id(@PathVariable("catalog2Id") @NotNull Long catalog2Id){
        return productClient.listBaseCatalog3sByCatalog2Id(catalog2Id);
    }

}
