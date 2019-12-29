package com.lwt.fastmall.product.service;

import com.alibaba.fastjson.JSON;
import com.lwt.fastmall.api.bean.*;
import com.lwt.fastmall.api.constant.RedisKeyEnum;
import com.lwt.fastmall.api.constant.TimeConstant;
import com.lwt.fastmall.api.mapper.tk.*;
import com.lwt.fastmall.common.util.ObjectUtils;
import com.lwt.fastmall.service.tool.jedis.JedisFactory;
import com.lwt.fastmall.service.util.JedisUtils;
import com.lwt.fastmall.service.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lwt
 * @date 2019/12/17 20:33
 */
@Service
public class ProductService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JedisFactory jedisFactory;

    @Autowired
    private SkuService skuService;

    @Autowired
    private PmsProductInfoMapper pmsProductInfoMapper;
    @Autowired
    private PmsProductImageMapper pmsProductImageMapper;
    @Autowired
    private PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    private PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;
    @Autowired
    private PmsProductVertifyRecordMapper pmsProductVertifyRecordMapper;

    /**
     * 根据三级分类获取Product info列表
     * @param catalog3Id
     * @return
     */
    public List<PmsProductInfo> listProductInfosByCatalog3Id(long catalog3Id){
        List<PmsProductInfo> result=null;
        Jedis jedis=null;
        try {
            jedis=jedisFactory.tryGetJedis();
            String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_PRODUCT_INFO, "LIST", "catalog3Id=" + catalog3Id);
            String resultJson= null;
            if (jedis!=null){
                resultJson=jedis.get(key);
            }

            if (resultJson==null){
                PmsProductInfo pmsProductInfoParam = new PmsProductInfo();
                pmsProductInfoParam.setCatalog3Id(catalog3Id);
                result=pmsProductInfoMapper.select(pmsProductInfoParam);

                if (jedis!=null){
                    JedisUtils.setex(jedis,key, TimeConstant.S_ONE_WEEK,3,result);
                }
            }else {
                result=JSON.parseArray(resultJson,PmsProductInfo.class);
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

    /**
     * 根据product Id删除product info、image、saleAttr、saleAttrValue
     * @param productId
     * @return
     */
    @Transactional
    public boolean removeProductByProductId(long productId){
        boolean result=false;
        Jedis jedis=null;
        try {
            int deleteSpuInfo = pmsProductInfoMapper.deleteByPrimaryKey(productId);
            if (deleteSpuInfo>0) {

                PmsProductImage pmsProductImageParam = new PmsProductImage();
                pmsProductImageParam.setProductId(productId);
                int delete = pmsProductImageMapper.delete(pmsProductImageParam);

                PmsProductSaleAttr pmsProductSaleAttrParam = new PmsProductSaleAttr();
                pmsProductSaleAttrParam.setProductId(productId);
                int delete1 = pmsProductSaleAttrMapper.delete(pmsProductSaleAttrParam);

                PmsProductSaleAttrValue pmsProductSaleAttrValueParam = new PmsProductSaleAttrValue();
                pmsProductSaleAttrValueParam.setProductId(productId);
                int delete2 = pmsProductSaleAttrValueMapper.delete(pmsProductSaleAttrValueParam);

                jedis=jedisFactory.tryGetJedis();
                String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_PRODUCT_INFO, "LIST", "*");
                if (jedis!=null){
                    JedisUtils.fuzzyDelete(jedis,key);
                }

                skuService.removeSkuByProductId(productId);

                result=true;
            }
        }catch (Exception e){
            logger.error("removeSpuInfosById(long id)",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("removeSpuInfosById(long id) 事务回滚");
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

    /**
     * 保存product。productInfo productImage productSaleAttr productSaleAttrValue
     * @param product
     * @return
     */
    @Transactional
    public boolean saveProduct(PmsProductInfo product){
        boolean result=false;
        Jedis jedis=null;
        try {
            int insertSpuInfo = pmsProductInfoMapper.insertSelective(product);
            if (insertSpuInfo>0){
                Long productId = product.getId();

                List<PmsProductImage> pmsProductImageParams = product.getProductImages();
                for (PmsProductImage pmsProductImageParam : pmsProductImageParams) {
                    pmsProductImageParam.setProductId(productId);
                    pmsProductImageMapper.insertSelective(pmsProductImageParam);
                }

                List<PmsProductSaleAttr> pmsProductSaleAttrParams = product.getProductSaleAttrs();
                for (PmsProductSaleAttr pmsProductSaleAttrParam : pmsProductSaleAttrParams) {
                    pmsProductSaleAttrParam.setProductId(productId);
                    pmsProductSaleAttrMapper.insertSelective(pmsProductSaleAttrParam);

                    Long saleAttrId = pmsProductSaleAttrParam.getSaleAttrId();
                    List<PmsProductSaleAttrValue> pmsProductSaleAttrValueParams = pmsProductSaleAttrParam.getProductSaleAttrValues();
                    for (PmsProductSaleAttrValue pmsProductSaleAttrValueParam : pmsProductSaleAttrValueParams) {
                        pmsProductSaleAttrValueParam.setProductId(productId);
                        pmsProductSaleAttrValueParam.setSaleAttrId(saleAttrId);
                        pmsProductSaleAttrValueMapper.insertSelective(pmsProductSaleAttrValueParam);
                    }
                }

                jedis=jedisFactory.tryGetJedis();
                String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_PRODUCT_INFO, "LIST", "*");
                if (jedis!=null){
                    JedisUtils.fuzzyDelete(jedis,key);
                }

                result=true;
            }
        }catch (Exception e){
            logger.error("saveSpu(PmsSpuInfoDO spu)",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("saveSpu(PmsSpuInfoDO spu) 事务回滚");
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

    /**
     * 根据productId获取商品图片
     * @param productId
     * @return
     */
    public List<PmsProductImage> listProductImagesByProductId(long productId){
        List<PmsProductImage> result=null;
        PmsProductImage pmsProductImageParam = new PmsProductImage();
        pmsProductImageParam.setProductId(productId);
        result = pmsProductImageMapper.select(pmsProductImageParam);
        return result;
    }

    /**
     * 根据productId列表获取商品图片
     * @param productIds
     * @return
     */
    public List<PmsProductImage> listProductImagesByProductIds(List<Long> productIds){
        List<PmsProductImage> result=null;
        Example example = new Example(PmsProductImage.class);
        example.createCriteria().andIn("id",productIds);
        result=pmsProductImageMapper.selectByExample(example);
        return result;
    }

    /**
     * 根据productId获取PmsProductSaleAttr和PmsProductSaleAttrValue
     * @param productId
     * @return
     */
    public List<PmsProductSaleAttr> listSaleAttrsByProductId(long productId){
        List<PmsProductSaleAttr> result=null;
        PmsProductSaleAttr pmsProductSaleAttrParam = new PmsProductSaleAttr();
        pmsProductSaleAttrParam.setProductId(productId);
        result = pmsProductSaleAttrMapper.select(pmsProductSaleAttrParam);

        if (!ObjectUtils.isBlank(result)){
            for (PmsProductSaleAttr pmsProductSaleAttr : result) {
                Long saleAttrId = pmsProductSaleAttr.getSaleAttrId();
                PmsProductSaleAttrValue pmsProductSaleAttrValueParam = new PmsProductSaleAttrValue();
                pmsProductSaleAttrValueParam.setProductId(productId);
                pmsProductSaleAttrValueParam.setSaleAttrId(saleAttrId);
                List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = pmsProductSaleAttrValueMapper.select(pmsProductSaleAttrValueParam);
                if (!ObjectUtils.isBlank(pmsProductSaleAttrValues)){
                    List<PmsProductSaleAttrValue> pmsProductSaleAttrValuesParam=new ArrayList<>();
                    for (PmsProductSaleAttrValue pmsProductSaleAttrValue : pmsProductSaleAttrValues) {
                        pmsProductSaleAttrValuesParam.add(pmsProductSaleAttrValue);
                    }
                    pmsProductSaleAttr.setProductSaleAttrValues(pmsProductSaleAttrValuesParam);
                }
            }
        }
        return result;
    }

}
