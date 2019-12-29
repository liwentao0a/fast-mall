package com.lwt.fastmall.product.service;

import com.alibaba.fastjson.JSON;
import com.lwt.fastmall.api.bean.*;
import com.lwt.fastmall.api.constant.RedisKeyEnum;
import com.lwt.fastmall.api.constant.TimeConstant;
import com.lwt.fastmall.api.mapper.tk.PmsSkuAttrValueMapper;
import com.lwt.fastmall.api.mapper.tk.PmsSkuImageMapper;
import com.lwt.fastmall.api.mapper.tk.PmsSkuInfoMapper;
import com.lwt.fastmall.api.mapper.tk.PmsSkuSaleAttrValueMapper;
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
 * @date 2019/12/17 22:06
 */
@Service
public class SkuService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JedisFactory jedisFactory;

    @Autowired
    private ProductService productService;

    @Autowired
    private PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    private PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    /**
     * 根据spu id获取sku info列表
     * @param productId
     * @return
     */
    public List<PmsSkuInfo> listSkuInfosByProductId(long productId){
        List<PmsSkuInfo> result =null;
        Jedis jedis=null;
        try {
            jedis=jedisFactory.tryGetJedis();
            String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_SKU_INFO, "LIST", "productId=" + productId);
            String resultJson = null;
            if (jedis!=null){
                resultJson=jedis.get(key);
            }
            if (resultJson==null){
                PmsSkuInfo pmsSkuInfoParam = new PmsSkuInfo();
                pmsSkuInfoParam.setProductId(productId);
                result = pmsSkuInfoMapper.select(pmsSkuInfoParam);

                if (jedis!=null){
                    JedisUtils.setex(jedis,key, TimeConstant.S_ONE_WEEK,3,result);
                }
            }else {
                result = JSON.parseArray(resultJson, PmsSkuInfo.class);
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

    /**
     * 根据skuId删除sku info,image,attrValue,saleAttrValue
     * @param skuId
     * @return
     */
    @Transactional
    public boolean removeSkuBySkuId(long skuId){
        Jedis jedis=null;
        try {
            int deleteSkuInfo = pmsSkuInfoMapper.deleteByPrimaryKey(skuId);
            if (deleteSkuInfo>0){
                PmsSkuImage pmsSkuImageParam = new PmsSkuImage();
                pmsSkuImageParam.setSkuId(skuId);
                int deleteSkuImage = pmsSkuImageMapper.delete(pmsSkuImageParam);

                PmsSkuAttrValue pmsSkuAttrValueParam = new PmsSkuAttrValue();
                pmsSkuAttrValueParam.setSkuId(skuId);
                int deleteSkuAttrValue = pmsSkuAttrValueMapper.delete(pmsSkuAttrValueParam);

                PmsSkuSaleAttrValue pmsSkuSaleAttrValueParam = new PmsSkuSaleAttrValue();
                pmsSkuSaleAttrValueParam.setSkuId(skuId);
                int deleteSkuSaleAttrValue = pmsSkuSaleAttrValueMapper.delete(pmsSkuSaleAttrValueParam);

                jedis=jedisFactory.tryGetJedis();
                String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_SKU_INFO, "*", null);
                if (jedis!=null){
                    JedisUtils.fuzzyDelete(jedis,key);
                }

                return true;
            }
        }catch (Exception e){
            logger.error("removeSkuById(long id)",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("removeSkuById(long id) 事务回滚");
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return false;
    }

    /**
     * 根据ProductId删除sku info,image,attrValue,saleAttrValue
     * @param productId
     * @return
     */
    @Transactional
    public boolean removeSkuByProductId(long productId){
        Jedis jedis=null;
        try {
            List<PmsSkuInfo> pmsSkuInfos = listSkuInfosByProductId(productId);
            if (!ObjectUtils.isBlank(pmsSkuInfos)){
                List<Long> skuIds=new ArrayList<>();
                for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
                    skuIds.add(pmsSkuInfo.getId());
                }
                removeSkuInSkuId(skuIds);
            }
            return true;
        }catch (Exception e){
            logger.error("removeSkuById(long id)",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("removeSkuById(long id) 事务回滚");
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return false;
    }

    /**
     * 根据skuIds删除sku info,image,attrValue,saleAttrValue
     * @param skuIds
     * @return
     */
    @Transactional
    public boolean removeSkuInSkuId(List skuIds){
        if (skuIds!=null&&skuIds.size()>0) {
            Jedis jedis = null;
            try {
                Example pmsSkuInfoExample = new Example(PmsSkuInfo.class);
                pmsSkuInfoExample.createCriteria().andIn("id",skuIds);
                int deleteSkuInfo = pmsSkuInfoMapper.deleteByExample(pmsSkuInfoExample);
                if (deleteSkuInfo > 0) {
                    Example pmsSkuImageExample = new Example(PmsSkuImage.class);
                    pmsSkuImageExample.createCriteria().andIn("skuId",skuIds);
                    int deleteSkuImage = pmsSkuImageMapper.deleteByExample(pmsSkuImageExample);

                    Example pmsSkuAttrValueExample = new Example(PmsSkuAttrValue.class);
                    pmsSkuAttrValueExample.createCriteria().andIn("skuId",skuIds);
                    int deleteSkuAttrValue = pmsSkuAttrValueMapper.deleteByExample(pmsSkuAttrValueExample);

                    Example pmsSkuSaleAttrValueExample = new Example(PmsSkuSaleAttrValue.class);
                    pmsSkuSaleAttrValueExample.createCriteria().andIn("skuId",skuIds);
                    int deleteSkuSaleAttrValue = pmsSkuSaleAttrValueMapper.deleteByExample(pmsSkuSaleAttrValueExample);

                    jedis = jedisFactory.tryGetJedis();
                    String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_SKU_INFO, "*", null);
                    if (jedis != null) {
                        JedisUtils.fuzzyDelete(jedis, key);
                    }

                    return true;
                }
            } catch (Exception e) {
                logger.error("removeSkuById(long id)", e);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                logger.error("removeSkuById(long id) 事务回滚");
            } finally {
                JedisUtils.tryClose(jedis);
            }
        }
        return false;
    }

    /**
     * 保存sku（PmsSkuInfo，PmsSkuImage，PmsSkuAttrValue，PmsSkuSaleAttrValue）
     * @param sku
     * @return
     */
    @Transactional
    public boolean saveSku(PmsSkuInfo sku){
        boolean result=false;
        Jedis jedis=null;
        try {
            int insertPmsSkuInfo = pmsSkuInfoMapper.insertSelective(sku);
            if (insertPmsSkuInfo>0){
                Long skuId = sku.getId();

                List<PmsSkuImage> skuImages = sku.getSkuImages();
                if (!ObjectUtils.isBlank(skuImages)){
                    List<Long> productIds=new ArrayList<>();
                    for (PmsSkuImage skuImage : skuImages) {
                        skuImage.setSkuId(skuId);
                        productIds.add(skuImage.getProductImgId());
                    }
                    List<PmsProductImage> pmsProductImages = productService.listProductImagesByProductIds(productIds);
                    if (!ObjectUtils.isBlank(pmsProductImages)&&skuImages.size()==pmsProductImages.size()){
                        for (PmsSkuImage skuImage : skuImages) {
                            long productImgId = skuImage.getProductImgId();
                            for (PmsProductImage pmsProductImage : pmsProductImages) {
                                long pmsProductImageId = pmsProductImage.getId();
                                if (productImgId==pmsProductImageId){
                                    skuImage.setImgName(pmsProductImage.getImgName());
                                    skuImage.setImgUrl(pmsProductImage.getImgUrl());
                                }
                            }
                        }
                        pmsSkuImageMapper.insertList(skuImages);
                    }else {
                        throw new RuntimeException();
                    }
                }

                List<PmsSkuAttrValue> skuAttrValues = sku.getSkuAttrValues();
                if (!ObjectUtils.isBlank(skuAttrValues)){
                    for (PmsSkuAttrValue skuAttrValue : skuAttrValues) {
                        skuAttrValue.setSkuId(skuId);
                    }
                    pmsSkuAttrValueMapper.insertList(skuAttrValues);
                }

                List<PmsSkuSaleAttrValue> skuSaleAttrValues = sku.getSkuSaleAttrValues();
                if (!ObjectUtils.isBlank(skuSaleAttrValues)){
                    for (PmsSkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValues) {
                        skuSaleAttrValue.setSkuId(skuId);
                    }
                    pmsSkuSaleAttrValueMapper.insertList(skuSaleAttrValues);
                }

                jedis=jedisFactory.tryGetJedis();
                if (jedis!=null){
                    String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_SKU_INFO, "*", null);
                    JedisUtils.fuzzyDelete(jedis,key);
                }

                result=true;
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

    /**
     * 获取根据product_id分组的PmsSkuInfo列表
     * @return
     */
    public List<PmsSkuInfo> listSkuInfosGroupByProductId(){
        List<PmsSkuInfo> result = listSkuInfosGroupBy("product_id");
        return result;
    }

    /**
     * 获取根据字段分组的PmsSkuInfo列表
     * @param fieldName
     * @return
     */
    public List<PmsSkuInfo> listSkuInfosGroupBy(String fieldName) {
        List<PmsSkuInfo> result=null;
        Jedis jedis=null;
        try {
            jedis=jedisFactory.tryGetJedis();
            String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_SKU_INFO, "LIST", "groupBy=" + fieldName);
            String resultJson=null;
            if (jedis!=null){
                resultJson=jedis.get(key);
            }

            if (resultJson==null){
                result=pmsSkuInfoMapper.selectGroupBy("product_id");

                if (jedis!=null){
                    JedisUtils.setex(jedis,key,TimeConstant.S_ONE_WEEK,3,result);
                }
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

    /**
     * 根据productId获取PmsSkuInfo,PmsSkuImage,PmsSkuAttrValue,PmsSkuSaleAttrValue
     * @param productId
     * @return
     */
    @Transactional
    public List<PmsSkuInfo> listSkusByProductId(long productId){
        List<PmsSkuInfo> result=null;
        PmsSkuInfo pmsSkuInfoParam = new PmsSkuInfo();
        pmsSkuInfoParam.setProductId(productId);
        result = pmsSkuInfoMapper.select(pmsSkuInfoParam);
        if (!ObjectUtils.isBlank(result)){
            for (PmsSkuInfo pmsSkuInfo : result) {
                long skuId = pmsSkuInfo.getId();

                PmsSkuImage pmsSkuImageParam = new PmsSkuImage();
                pmsSkuImageParam.setSkuId(skuId);
                List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.select(pmsSkuImageParam);
                pmsSkuInfo.setSkuImages(pmsSkuImages);

                PmsSkuAttrValue pmsSkuAttrValueParam = new PmsSkuAttrValue();
                pmsSkuAttrValueParam.setSkuId(skuId);
                List<PmsSkuAttrValue> pmsSkuAttrValues = pmsSkuAttrValueMapper.select(pmsSkuAttrValueParam);
                pmsSkuInfo.setSkuAttrValues(pmsSkuAttrValues);

                PmsSkuSaleAttrValue pmsSkuSaleAttrValueParam = new PmsSkuSaleAttrValue();
                pmsSkuSaleAttrValueParam.setSkuId(skuId);
                List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues = pmsSkuSaleAttrValueMapper.select(pmsSkuSaleAttrValueParam);
                pmsSkuInfo.setSkuSaleAttrValues(pmsSkuSaleAttrValues);
            }
        }
        return result;
    }

    /**
     * 根据skuId获取PmsSkuInfo
     * @param skuId
     * @return
     */
    public PmsSkuInfo getSkuInfoById(long skuId){
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectByPrimaryKey(skuId);
        return pmsSkuInfo;
    }
}
