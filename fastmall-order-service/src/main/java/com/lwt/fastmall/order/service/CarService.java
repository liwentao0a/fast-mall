package com.lwt.fastmall.order.service;

import com.lwt.fastmall.api.bean.*;
import com.lwt.fastmall.api.client.ProductClient;
import com.lwt.fastmall.api.client.UserClient;
import com.lwt.fastmall.api.mapper.tk.OmsCartItemMapper;
import com.lwt.fastmall.common.util.ObjectUtils;
import com.lwt.fastmall.common.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author lwt
 * @date 2019/12/26 20:17
 */
@Service
public class CarService {

    @Autowired
    private ProductClient productClient;
    @Autowired
    private UserClient userClient;

    @Autowired
    private OmsCartItemMapper omsCartItemMapper;

    /**
     * 保存商品到购物车
     * @param userId
     * @param skuId
     * @param quantity
     * @return
     */
    public Long saveCartItem(long userId,long skuId,long quantity){
        Result<UmsUser> umsUserResult = userClient.getUserById(userId);
        if (!ResultUtils.isSuccess(umsUserResult)){
            return null;
        }
        Result<PmsSkuInfo> pmsSkuInfoResult = productClient.getSkuInfoById(skuId);
        if (!ResultUtils.isSuccess(pmsSkuInfoResult)) {
            return null;
        }
        UmsUser umsUser = umsUserResult.getData();
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoResult.getData();
        OmsCartItem omsCartItemParam = new OmsCartItem();
        omsCartItemParam.setProductId(pmsSkuInfo.getProductId());
        omsCartItemParam.setProductSkuId(pmsSkuInfo.getId());
        omsCartItemParam.setUserId(userId);
        omsCartItemParam.setQuantity(quantity);
        omsCartItemParam.setPrice(pmsSkuInfo.getPrice());
        List<PmsSkuSaleAttrValue> skuSaleAttrValues = pmsSkuInfo.getSkuSaleAttrValues();
        if (!ObjectUtils.isBlank(skuSaleAttrValues)){
            if (skuSaleAttrValues.size()>=1){
                omsCartItemParam.setSp1(skuSaleAttrValues.get(0).getSaleAttrValueName());
            }
            if (skuSaleAttrValues.size()>=2){
                omsCartItemParam.setSp2(skuSaleAttrValues.get(1).getSaleAttrValueName());
            }
            if (skuSaleAttrValues.size()>=3){
                omsCartItemParam.setSp3(skuSaleAttrValues.get(2).getSaleAttrValueName());
            }
            StringBuffer productAttr=new StringBuffer();
            productAttr.append("[");
            for (int i = 0; i < skuSaleAttrValues.size(); i++) {
                PmsSkuSaleAttrValue skuSaleAttrValue=skuSaleAttrValues.get(i);
                if (i>0){
                    productAttr.append(",");
                }
                productAttr.append("{\"key\":\"")
                        .append(skuSaleAttrValue.getSaleAttrName())
                        .append("\",value:\"")
                        .append(skuSaleAttrValue.getSaleAttrValueName())
                        .append("\"}");
            }
            productAttr.append("]");
            omsCartItemParam.setProductAttr(productAttr.toString());
        }
        omsCartItemParam.setProductPic(pmsSkuInfo.getSkuDefaultImg());
        omsCartItemParam.setProductName(pmsSkuInfo.getSkuName());
        omsCartItemParam.setProductSubTitle(pmsSkuInfo.getSkuDesc());
        omsCartItemParam.setProductSkuCode(null);
        omsCartItemParam.setUserNickname(umsUser.getNickname());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        omsCartItemParam.setCreateDate(timestamp);
        omsCartItemParam.setModifyDate(timestamp);
        omsCartItemParam.setDeleteStatus(0L);
        omsCartItemParam.setProductCategoryId(pmsSkuInfo.getCatalog3Id());
        int i = omsCartItemMapper.insertSelective(omsCartItemParam);
        if (i>0){
            return omsCartItemParam.getId();
        }else {
            return null;
        }
    }

    /**
     * 通过userId获取购物车列表
     * @param userId
     * @return
     */
    public List<OmsCartItem> listCartItemsByUserId(long userId){
        OmsCartItem omsCartItemParam = new OmsCartItem();
        omsCartItemParam.setUserId(userId);
        List<OmsCartItem> omsCartItems = omsCartItemMapper.select(omsCartItemParam);
        return omsCartItems;
    }

    /**
     * 根据id获取OmsCartItem
     * @param id
     * @return
     */
    public OmsCartItem getCartItemById(long id){
        OmsCartItem omsCartItem = omsCartItemMapper.selectByPrimaryKey(id);
        return omsCartItem;
    }

}
