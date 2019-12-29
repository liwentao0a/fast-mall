package com.lwt.fastmall.order.controller;

import com.lwt.fastmall.api.bean.OmsCartItem;
import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.constant.CodeEnum;
import com.lwt.fastmall.common.util.ObjectUtils;
import com.lwt.fastmall.common.util.ResultUtils;
import com.lwt.fastmall.order.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lwt
 * @date 2019/12/26 20:24
 */
@RestController
@Validated
public class CarController {

    @Autowired
    private CarService carService;

    @RequestMapping(value = "/saveCartItem",method = RequestMethod.PUT)
    public Result<Long> saveCartItem(@RequestParam("userId") @NotNull Long userId,
                               @RequestParam("skuId") @NotNull Long skuId,
                               @RequestParam("quantity") @NotNull Long quantity){
        Long saveCartItem = carService.saveCartItem(userId,skuId,quantity);
        if (saveCartItem==null){
            return ResultUtils.result(CodeEnum.RETURN_FALSE);
        }
        return ResultUtils.success(saveCartItem);
    }

    @RequestMapping(value = "/listCartItemsByUserId/{userId}",method = RequestMethod.GET)
    public Result<List<OmsCartItem>> listCartItemsByUserId(@PathVariable("userId") @NotNull Long userId){
        List<OmsCartItem> omsCartItems = carService.listCartItemsByUserId(userId);
        if (ObjectUtils.isBlank(omsCartItems)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(omsCartItems);
    }

    @RequestMapping(value = "/getCartItemById/{id}",method = RequestMethod.GET)
    public Result<OmsCartItem> getCartItemById(@PathVariable("id") @NotNull Long id){
        OmsCartItem omsCartItem = carService.getCartItemById(id);
        if (ObjectUtils.isBlank(omsCartItem)){
            return ResultUtils.result(CodeEnum.DATA_NOT_FOUND);
        }
        return ResultUtils.success(omsCartItem);
    }
}
