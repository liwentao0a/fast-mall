package com.lwt.fastmall.api.client;

import com.lwt.fastmall.api.bean.OmsCartItem;
import com.lwt.fastmall.api.bean.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lwt
 * @date 2019/12/26 20:36
 */
@FeignClient(name = "fastmall-order-service")
public interface OrderClient {

    /*
    com.lwt.fastmall.order.controller.CarController
     */

    @RequestMapping(value = "/saveCartItem",method = RequestMethod.PUT)
    public Result<Long> saveCartItem(@RequestParam("userId") @NotNull Long userId,
                               @RequestParam("skuId") @NotNull Long skuId,
                               @RequestParam("quantity") @NotNull Long quantity);

    @RequestMapping(value = "/listCartItemsByUserId/{userId}",method = RequestMethod.GET)
    public Result<List<OmsCartItem>> listCartItemsByUserId(@PathVariable("userId") @NotNull Long userId);

    @RequestMapping(value = "/getCartItemById/{id}",method = RequestMethod.GET)
    public Result<OmsCartItem> getCartItemById(@PathVariable("id") @NotNull Long id);

}
