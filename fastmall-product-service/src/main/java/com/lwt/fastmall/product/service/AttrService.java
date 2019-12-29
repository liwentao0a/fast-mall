package com.lwt.fastmall.product.service;

import com.alibaba.fastjson.JSON;
import com.lwt.fastmall.api.bean.PmsBaseAttrInfo;
import com.lwt.fastmall.api.bean.PmsBaseAttrValue;
import com.lwt.fastmall.api.bean.PmsBaseSaleAttr;
import com.lwt.fastmall.api.constant.RedisKeyEnum;
import com.lwt.fastmall.api.constant.TimeConstant;
import com.lwt.fastmall.api.mapper.tk.PmsBaseAttrInfoMapper;
import com.lwt.fastmall.api.mapper.tk.PmsBaseAttrValueMapper;
import com.lwt.fastmall.api.mapper.tk.PmsBaseSaleAttrMapper;
import com.lwt.fastmall.common.util.ObjectUtils;
import com.lwt.fastmall.service.tool.jedis.JedisFactory;
import com.lwt.fastmall.service.util.JedisUtils;
import com.lwt.fastmall.service.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lwt
 * @date 2019/12/17 16:10
 */
@Service
public class AttrService {

    @Autowired
    private JedisFactory jedisFactory;

    @Autowired
    private PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Autowired
    private PmsBaseAttrValueMapper pmsBaseAttrValueMapper;
    @Autowired
    private PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;

    /**
     * 根据三级分类查询平台属性
     * @param catalog3Id
     * @return
     */
    public List<PmsBaseAttrInfo> listBaseAttrInfosByCatalog3Id(long catalog3Id){
        List<PmsBaseAttrInfo> result=null;
        Jedis jedis=null;
        try {
            jedis=jedisFactory.tryGetJedis();
            String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_BASE_ATTR_INFO, "LIST", "catalog3Id=" + catalog3Id);
            String resultJson = null;
            if (jedis!=null){
                resultJson=jedis.get(key);
            }

            if (resultJson==null){
                PmsBaseAttrInfo pmsBaseAttrInfoParam = new PmsBaseAttrInfo();
                pmsBaseAttrInfoParam.setCatalog3Id(catalog3Id);
                pmsBaseAttrInfoParam.setIsEnabled("1");
                result=pmsBaseAttrInfoMapper.select(pmsBaseAttrInfoParam);

                if (jedis!=null){
                    JedisUtils.setex(jedis,key,TimeConstant.S_ONE_WEEK,3,result);
                }
            }else {
                result=JSON.parseArray(resultJson,PmsBaseAttrInfo.class);
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

    /**
     * 根据id逻辑删除平台属性
     * @param id
     * @return
     */
    public boolean logicRemoveBaseAttrInfoById(long id){
        boolean result=false;
        Jedis jedis=null;
        try {
            PmsBaseAttrInfo pmsBaseAttrInfoParam = new PmsBaseAttrInfo();
            pmsBaseAttrInfoParam.setId(id);
            pmsBaseAttrInfoParam.setIsEnabled("0");
            int update = pmsBaseAttrInfoMapper.updateByPrimaryKeySelective(pmsBaseAttrInfoParam);
            if (update>0){
                result=true;

                jedis=jedisFactory.tryGetJedis();
                String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_BASE_ATTR_INFO, "*",null);
                if (jedis!=null){
                    JedisUtils.fuzzyDelete(jedis,key);
                }
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

    /**
     * 根据平台属性id查询平台属性值列表
     * @param attrId
     * @return
     */
    public List<PmsBaseAttrValue> listBaseAttrValuesByAttrId(long attrId){
        List<PmsBaseAttrValue> result=null;
        Jedis jedis=null;
        try {
            jedis=jedisFactory.tryGetJedis();
            String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_BASE_ATTR_VALUE, "LIST", "attrId=" + attrId);
            String resultJson = null;
            if (jedis!=null){
                resultJson=jedis.get(key);
            }

            if (resultJson==null){
                PmsBaseAttrValue pmsBaseAttrValueParam = new PmsBaseAttrValue();
                pmsBaseAttrValueParam.setAttrId(attrId);
                pmsBaseAttrValueParam.setIsEnabled("1");
                result = pmsBaseAttrValueMapper.select(pmsBaseAttrValueParam);

                if (jedis!=null){
                    JedisUtils.setex(jedis,key,TimeConstant.S_ONE_WEEK,3,result);
                }
            }else {
                result=JSON.parseArray(resultJson,PmsBaseAttrValue.class);
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

    /**
     * 根据id逻辑删除平台属性值
     * @param id
     * @return
     */
    public boolean logicRemoveBaseAttrValueById(long id){
        boolean result=false;
        Jedis jedis=null;
        try {
            PmsBaseAttrValue pmsBaseAttrValueParam = new PmsBaseAttrValue();
            pmsBaseAttrValueParam.setId(id);
            pmsBaseAttrValueParam.setIsEnabled("0");
            int update = pmsBaseAttrValueMapper.updateByPrimaryKeySelective(pmsBaseAttrValueParam);
            if (update>0){
                result=true;

                jedis=jedisFactory.tryGetJedis();
                String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_BASE_ATTR_VALUE, "*",null);
                if (jedis!=null){
                    JedisUtils.fuzzyDelete(jedis,key);
                }
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

    /**
     * 保存平台属性值
     * @param pmsBaseAttrValue
     * @return
     */
    public boolean saveBaseAttrValue(PmsBaseAttrValue pmsBaseAttrValue){
        boolean result=false;
        Jedis jedis=null;
        try {
            jedis=jedisFactory.tryGetJedis();
            String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_BASE_ATTR_VALUE, "*",null);

            Example example = new Example(PmsBaseAttrValue.class);
            example.createCriteria().andEqualTo(pmsBaseAttrValue);
            PmsBaseAttrValue pmsBaseAttrValueParam = new PmsBaseAttrValue();
            pmsBaseAttrValueParam.setIsEnabled("1");
            int update = pmsBaseAttrValueMapper.updateByExampleSelective(pmsBaseAttrValueParam, example);
            if (update>0) {
                result=true;

                if (jedis!=null){
                    JedisUtils.fuzzyDelete(jedis,key);
                }
            }else {
                int insert = pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);
                if (insert>0){
                    result=true;

                    if (jedis!=null){
                        JedisUtils.fuzzyDelete(jedis,key);
                    }
                }
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

    /**
     * 保存平台属性
     * @param pmsBaseAttrInfo
     * @return
     */
    public boolean saveBaseAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo){
        boolean result=false;
        Jedis jedis=null;
        try {
            jedis=jedisFactory.tryGetJedis();
            String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_BASE_ATTR_INFO, "*", null);

            Example example = new Example(PmsBaseAttrInfo.class);
            example.createCriteria().andEqualTo(pmsBaseAttrInfo);
            PmsBaseAttrInfo pmsBaseAttrInfoParam = new PmsBaseAttrInfo();
            pmsBaseAttrInfoParam.setIsEnabled("1");
            int update = pmsBaseAttrInfoMapper.updateByExampleSelective(pmsBaseAttrInfoParam, example);
            if (update>0){
                result=true;

                if (jedis!=null){
                    JedisUtils.fuzzyDelete(jedis,key);
                }
            }else {
                int insert = pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);
                if (insert>0){
                    result=true;

                    if (jedis!=null){
                        JedisUtils.fuzzyDelete(jedis,key);
                    }
                }
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

    /**
     * 获取基本销售属性列表
     * @return
     */
    public List<PmsBaseSaleAttr> listBaseSaleAttrs(){
        List<PmsBaseSaleAttr> result=null;
        Jedis jedis=null;
        try {
            jedis=jedisFactory.tryGetJedis();
            String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_BASE_SALE_ATTR, "LIST", null);
            String resultJson = null;
            if (jedis!=null){
                resultJson=jedis.get(key);
            }

            if (resultJson==null){
                result = pmsBaseSaleAttrMapper.selectAll();

                if (jedis!=null){
                    JedisUtils.setex(jedis,key,TimeConstant.S_ONE_WEEK,3,result);
                }
            }else {
                result=JSON.parseArray(resultJson,PmsBaseSaleAttr.class);
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

    /**
     * 根据catalog3Id获取PmsBaseAttrInfo和PmsBaseAttrValue
     * @param catalog3Id
     * @return
     */
    public List<PmsBaseAttrInfo> listBaseAttrsByCatalog3Id(long catalog3Id){
        List<PmsBaseAttrInfo> result=null;
        Jedis jedis=null;
        try {
            jedis=jedisFactory.tryGetJedis();
            String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_BASE_ATTR_INFO, "LIST", "catalog3Id=" + catalog3Id);
            String resultJson=null;
            if (jedis!=null){
                resultJson=jedis.get(key);
            }

            if (resultJson==null){
                PmsBaseAttrInfo pmsBaseAttrInfoParam = new PmsBaseAttrInfo();
                pmsBaseAttrInfoParam.setCatalog3Id(catalog3Id);
                result=pmsBaseAttrInfoMapper.select(pmsBaseAttrInfoParam);

                if (jedis!=null){
                    JedisUtils.setex(jedis,key,TimeConstant.S_ONE_WEEK,3,result);
                }
            }else {
                result=JSON.parseArray(resultJson,PmsBaseAttrInfo.class);
            }
            if (!ObjectUtils.isBlank(result)){
                List<Long> attrIds=new ArrayList<>();
                for (PmsBaseAttrInfo pmsBaseAttrInfo : result) {
                    attrIds.add(pmsBaseAttrInfo.getId());
                }
                Example example = new Example(PmsBaseAttrValue.class);
                example.createCriteria().andIn("attrId",attrIds);
                List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.selectByExample(example);
                for (int i = 0; i < result.size(); i++) {
                    PmsBaseAttrInfo pmsBaseAttrInfo = result.get(i);
                    long attrId = pmsBaseAttrInfo.getId();
                    List<PmsBaseAttrValue> pmsBaseAttrValuesParam=new ArrayList<>();
                    for (PmsBaseAttrValue pmsBaseAttrValue : pmsBaseAttrValues) {
                        long attrValueAttrId = pmsBaseAttrValue.getAttrId();
                        if (attrValueAttrId==attrId){
                            pmsBaseAttrValuesParam.add(pmsBaseAttrValue);
                        }
                    }
                    if (!ObjectUtils.isBlank(pmsBaseAttrValuesParam)) {
                        pmsBaseAttrInfo.setBaseAttrValues(pmsBaseAttrValuesParam);
                    }
                }
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }
}
