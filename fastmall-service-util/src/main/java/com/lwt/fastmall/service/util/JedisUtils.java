package com.lwt.fastmall.service.util;

import com.alibaba.fastjson.JSON;
import com.lwt.fastmall.api.constant.TimeConstant;
import com.lwt.fastmall.common.util.ObjectUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;

/**
 * @author lwt
 * @date 2019/12/18 20:02
 */
public class JedisUtils {

    public static String set(Jedis jedis,String key, Object value){
        String valueJson = JSON.toJSONString(value);
        return jedis.set(key, valueJson);
    }

    public static String setex(Jedis jedis,String key,int seconds, Object value){
        String valueJson = JSON.toJSONString(value);
        return jedis.setex(key,seconds, valueJson);
    }

    public static String setexNull(Jedis jedis,String key,int seconds){
        return setex(jedis,key,seconds,null);
    }

    public static String setex(Jedis jedis,String key,int seconds,int blankSeconds, Object value){
        if (ObjectUtils.isBlank(value)){
            return setexNull(jedis,key,blankSeconds);
        }else {
            return setex(jedis,key, TimeConstant.S_ONE_WEEK,value);
        }
    }

    public static void  fuzzyDelete(Jedis jedis,String pattern){
        ScanParams scanParams = new ScanParams();
        scanParams.count(10);
        scanParams.match(pattern);
        String cursor="0";
        while (true) {
            ScanResult<String> scan = jedis.scan(cursor, scanParams);
            List<String> result = scan.getResult();
            if (result!=null&&result.size()>0){
                jedis.del(result.toArray(new String[]{}));
            }
            if (scan.isCompleteIteration()){
                break;
            }
            cursor=scan.getCursor();
        }
    }

    public static void tryClose(Jedis jedis){
        if (jedis!=null){
            try {
                jedis.close();
            }catch (Exception e){
            }
        }
    }

}
