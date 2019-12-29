package com.lwt.fastmall.order.controller;

import com.lwt.fastmall.api.bean.OmsCartItem;
import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.client.OrderClient;
import com.lwt.fastmall.web.tool.login.LoginAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lwt
 * @date 2019/12/26 20:40
 */
@RestController
@Validated
@CrossOrigin
public class CarController {

    @Autowired
    private OrderClient orderClient;

    @LoginAuth
    @RequestMapping(value = "/saveCartItem",method = RequestMethod.PUT)
    public Result<Long> saveCartItem(@RequestParam("skuId") @NotNull Long skuId,
                               @RequestParam("quantity") @NotNull Long quantity,
                               HttpServletRequest request){
        long userId = (int) request.getAttribute("userId");
        return orderClient.saveCartItem(userId,skuId,quantity);
    }

    @LoginAuth
    @RequestMapping(value = "/listCartItemsByUserId",method = RequestMethod.GET)
    public Result<List<OmsCartItem>> listCartItemsByUserId(HttpServletRequest request){
        long userId = (int) request.getAttribute("userId");
        return orderClient.listCartItemsByUserId(userId);
    }

    @RequestMapping(value = "/getCartItemById/{id}",method = RequestMethod.GET)
    public Result<OmsCartItem> getCartItemById(@PathVariable("id") @NotNull Long id){
        return orderClient.getCartItemById(id);
    }

}
