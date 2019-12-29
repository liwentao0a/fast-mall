package com.lwt.fastmall.product.service;

import com.alibaba.fastjson.JSON;
import com.lwt.fastmall.api.bean.PmsBaseCatalog1;
import com.lwt.fastmall.api.bean.PmsBaseCatalog2;
import com.lwt.fastmall.api.bean.PmsBaseCatalog3;
import com.lwt.fastmall.api.constant.RedisKeyEnum;
import com.lwt.fastmall.api.constant.TimeConstant;
import com.lwt.fastmall.api.mapper.tk.PmsBaseCatalog1Mapper;
import com.lwt.fastmall.api.mapper.tk.PmsBaseCatalog2Mapper;
import com.lwt.fastmall.api.mapper.tk.PmsBaseCatalog3Mapper;
import com.lwt.fastmall.service.tool.jedis.JedisFactory;
import com.lwt.fastmall.service.util.JedisUtils;
import com.lwt.fastmall.service.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author lwt
 * @date 2019/12/17 15:22
 */
@Service
public class CatalogService {

    @Autowired
    private JedisFactory jedisFactory;

    @Autowired
    private PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;
    @Autowired
    private PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;
    @Autowired
    private PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;

    /**
     * 获取所有一级分类
     * @return
     */
    public List<PmsBaseCatalog1> listBaseCatalog1s(){
        List<PmsBaseCatalog1> result=null;
        Jedis jedis=null;
        try {
            jedis=jedisFactory.tryGetJedis();
            String key= RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_BASE_CATALOG1,"LIST",null);
            String resultJson = null;
            if (jedis!=null){
                resultJson=jedis.get(key);
            }

            if (resultJson==null){
                result = pmsBaseCatalog1Mapper.selectAll();

                if (jedis!=null){
                    JedisUtils.setex(jedis,key, TimeConstant.S_ONE_WEEK,3,result);
                }
            }else {
                result=JSON.parseArray(resultJson,PmsBaseCatalog1.class);
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

    /**
     * 根据一级分类id获取二级分类列表
     * @param catalog1Id
     * @return
     */
    public List<PmsBaseCatalog2> listBaseCatalog2sByCatalog1Id(long catalog1Id){
        List<PmsBaseCatalog2> result=null;
        Jedis jedis=null;
        try {
            jedis=jedisFactory.tryGetJedis();
            String key=RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_BASE_CATALOG2,"LIST","catalog1Id="+catalog1Id);
            String resultJson = null;
            if (jedis!=null){
                resultJson=jedis.get(key);
            }

            if (resultJson==null){
                PmsBaseCatalog2 pmsBaseCatalog2Param = new PmsBaseCatalog2();
                pmsBaseCatalog2Param.setCatalog1Id(catalog1Id);
                result=pmsBaseCatalog2Mapper.select(pmsBaseCatalog2Param);

                if (jedis!=null){
                    JedisUtils.setex(jedis,key,TimeConstant.S_ONE_WEEK,3,result);
                }
            }else {
                result=JSON.parseArray(resultJson,PmsBaseCatalog2.class);
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

    /**
     * 根据二级分类id获取三级分类列表
     * @param catalog2Id
     * @return
     */
    public List<PmsBaseCatalog3> listBaseCatalog3sByCatalog2Id(long catalog2Id){
        List<PmsBaseCatalog3> result=null;
        Jedis jedis=null;
        try {
            jedis=jedisFactory.tryGetJedis();
            String key=RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_PMS_BASE_CATALOG3,"LIST","catalog2Id="+catalog2Id);
            String resultJson = null;
            if (jedis!=null){
                resultJson=jedis.get(key);
            }

            if (resultJson==null){
                PmsBaseCatalog3 pmsBaseCatalog3Param = new PmsBaseCatalog3();
                pmsBaseCatalog3Param.setCatalog2Id(catalog2Id);
                result = pmsBaseCatalog3Mapper.select(pmsBaseCatalog3Param);

                if (jedis!=null){
                    JedisUtils.setex(jedis,key,TimeConstant.S_ONE_WEEK,3,result);
                }
            }else {
                result=JSON.parseArray(resultJson,PmsBaseCatalog3.class);
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }
}
