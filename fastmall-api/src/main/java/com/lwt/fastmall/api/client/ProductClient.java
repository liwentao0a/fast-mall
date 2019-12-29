package com.lwt.fastmall.api.client;

import com.lwt.fastmall.api.bean.*;
import com.lwt.fastmall.api.group.PmsBaseAttrInfoGroup;
import com.lwt.fastmall.api.group.PmsBaseAttrValueGroup;
import com.lwt.fastmall.api.group.PmsProductInfoGroup;
import com.lwt.fastmall.api.group.PmsSkuInfoGroup;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lwt
 * @date 2019/12/17 16:02
 */
@FeignClient(name = "fastmall-product-service")
public interface ProductClient {

    /*
    com.lwt.fastmall.product.controller.CatalogController
     */

    @RequestMapping(value = "/listBaseCatalog1s",method = RequestMethod.GET)
    public Result<List<PmsBaseCatalog1>> listBaseCatalog1s();

    @RequestMapping(value = "/listBaseCatalog2sByCatalog1Id/{catalog1Id}",method = RequestMethod.GET)
    public Result<List<PmsBaseCatalog2>> listBaseCatalog2sByCatalog1Id(@PathVariable("catalog1Id") @NotNull Long catalog1Id);

    @RequestMapping(value = "/listBaseCatalog3sByCatalog2Id/{catalog2Id}",method = RequestMethod.GET)
    public Result<List<PmsBaseCatalog3>> listBaseCatalog3sByCatalog2Id(@PathVariable("catalog2Id") @NotNull Long catalog2Id);

    /*
    com.lwt.fastmall.product.controller.AttrController
     */

    @RequestMapping(value = "/listBaseAttrInfosByCatalog3Id/{catalog3Id}",method = RequestMethod.GET)
    public Result<List<PmsBaseAttrInfo>> listBaseAttrInfosByCatalog3Id(@PathVariable("catalog3Id") @NotNull Long catalog3Id);

    @RequestMapping(value = "/logicRemoveBaseAttrInfoById/{id}",method = RequestMethod.DELETE)
    public Result logicRemoveBaseAttrInfoById(@PathVariable("id") @NotNull Long id);

    @RequestMapping(value = "/listBaseAttrValuesByAttrId/{attrId}",method = RequestMethod.GET)
    public Result<List<PmsBaseAttrValue>> listBaseAttrValuesByAttrId(@PathVariable("attrId") @NotNull Long attrId);

    @RequestMapping(value = "/logicRemoveBaseAttrValueById/{id}",method = RequestMethod.DELETE)
    public Result logicRemoveBaseAttrValueById(@PathVariable("id") @NotNull Long id);

    @RequestMapping(value = "/saveBaseAttrValue",method = RequestMethod.PUT)
    public Result saveBaseAttrValue(@RequestBody @Validated(PmsBaseAttrValueGroup.SaveBaseAttrValue.class) PmsBaseAttrValue pmsBaseAttrValue);

    @RequestMapping(value = "/saveBaseAttrInfo",method = RequestMethod.PUT)
    public Result saveBaseAttrInfo(@RequestBody @Validated(PmsBaseAttrInfoGroup.SaveBaseAttrInfo.class) PmsBaseAttrInfo pmsBaseAttrInfo);

    @RequestMapping(value = "/listBaseSaleAttrs",method = RequestMethod.GET)
    public Result<List<PmsBaseSaleAttr>> listBaseSaleAttrs();

    @RequestMapping(value = "/listBaseAttrsByCatalog3Id/{catalog3Id}",method = RequestMethod.GET)
    public Result<List<PmsBaseAttrInfo>> listBaseAttrsByCatalog3Id(@PathVariable("catalog3Id") @NotNull Long catalog3Id);

    /*
    com.lwt.fastmall.product.controller.ProductController
     */

    @RequestMapping(value = "/listProductInfosByCatalog3Id/{catalog3Id}",method = RequestMethod.GET)
    public Result<List<PmsProductInfo>> listProductInfosByCatalog3Id(@PathVariable("catalog3Id") @NotNull Long catalog3Id);

    @RequestMapping(value = "/removeProductByProductId/{productId}",method = RequestMethod.DELETE)
    public Result removeProductByProductId(@PathVariable("productId") @NotNull Long productId);

    @RequestMapping(value = "/saveProduct",method = RequestMethod.PUT)
    public Result saveProduct(@RequestBody @Validated(PmsProductInfoGroup.SaveProduct.class) PmsProductInfo product);

    @RequestMapping(value = "/listProductImagesByProductId/{productId}",method = RequestMethod.GET)
    public Result<List<PmsProductImage>> listProductImagesByProductId(@PathVariable("productId") @NotNull Long productId);

    @RequestMapping(value = "/listSaleAttrsByProductId/{productId}",method = RequestMethod.GET)
    public Result<List<PmsProductSaleAttr>> listSaleAttrsByProductId(@PathVariable("productId") @NotNull Long productId);

    /*
    com.lwt.fastmall.product.controller.SkuController
     */

    @RequestMapping(value = "/listSkuInfosByProductId/{productId}",method = RequestMethod.GET)
    public Result<List<PmsSkuInfo>> listSkuInfosByProductId(@PathVariable("productId") @NotNull Long productId);

    @RequestMapping(value = "/removeSkuBySkuId/{skuId}",method = RequestMethod.DELETE)
    public Result removeSkuBySkuId(@PathVariable("skuId") @NotNull Long skuId);

    @RequestMapping(value = "/saveSku",method = RequestMethod.PUT)
    public Result saveSku(@RequestBody @Validated({PmsSkuInfoGroup.SaveSku.class}) PmsSkuInfo sku);

    @RequestMapping(value = "/listSkuInfosGroupByProductId",method = RequestMethod.GET)
    public Result<List<PmsSkuInfo>> listSkuInfosGroupByProductId();

    @RequestMapping(value = "/listSkusByProductId/{productId}",method = RequestMethod.GET)
    public Result<List<PmsSkuInfo>> listSkusByProductId(@PathVariable("productId") @NotNull Long productId);

    @RequestMapping(value = "/getSkuInfoById/{skuId}",method = RequestMethod.GET)
    public Result<PmsSkuInfo> getSkuInfoById(@PathVariable("skuId") @NotNull Long skuId);

}
