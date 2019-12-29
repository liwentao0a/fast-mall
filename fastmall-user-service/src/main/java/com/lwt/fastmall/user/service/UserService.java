package com.lwt.fastmall.user.service;

import com.alibaba.fastjson.JSON;
import com.lwt.fastmall.api.bean.UmsUser;
import com.lwt.fastmall.api.constant.RedisKeyEnum;
import com.lwt.fastmall.api.constant.TimeConstant;
import com.lwt.fastmall.api.mapper.tk.UmsUserMapper;
import com.lwt.fastmall.common.util.ObjectUtils;
import com.lwt.fastmall.service.tool.jedis.JedisFactory;
import com.lwt.fastmall.service.util.JedisUtils;
import com.lwt.fastmall.service.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * @author lwt
 * @date 2019/12/16 21:48
 */
@Service
public class UserService {

    @Autowired
    private JedisFactory jedisFactory;

    @Autowired
    private UmsUserMapper umsUserMapper;

    /**
     * 根据用户名和密码获取用户
     * @param username
     * @param password
     * @return
     */
    public UmsUser getUserByUsernameAndPassword(String username,String password){
        UmsUser result=null;
        Jedis jedis=null;
        try {
            jedis=jedisFactory.tryGetJedis();
            String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_UMS_USER, "INFO", "USERNAME="+username+"&PASSWORD="+password);
            String resultJson= null;
            if (jedis!=null){
                resultJson=jedis.get(key);
            }

            if (resultJson==null) {
                UmsUser umsUserParam = new UmsUser();
                umsUserParam.setUsername(username);
                umsUserParam.setPassword(password);
                result = umsUserMapper.selectOne(umsUserParam);

                if (jedis!=null) {
                    if (ObjectUtils.isBlank(result)) {
                        JedisUtils.setexNull(jedis,key, 3);
                    } else {
                        JedisUtils.setex(jedis,key, 604800, result);
                    }
                }
            }else {
                result=JSON.parseObject(resultJson,UmsUser.class);
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    public UmsUser getUserById(long userId){
        UmsUser result=null;
        Jedis jedis=null;
        try {
            jedis=jedisFactory.tryGetJedis();
            String key = RedisUtils.buildKey(RedisKeyEnum.FASTMALL_DB_UMS_USER, "INFO", String.valueOf(userId));
            String resultJson=null;
            if (jedis!=null){
                resultJson=jedis.get(key);
            }

            if (resultJson==null){
                UmsUser umsUserParam = new UmsUser();
                umsUserParam.setId(userId);
                result = umsUserMapper.selectOne(umsUserParam);

                if (jedis!=null){
                    JedisUtils.setex(jedis,key, TimeConstant.S_ONE_WEEK,3,result);
                }
            }else {
                result=JSON.parseObject(resultJson,UmsUser.class);
            }
        }finally {
            JedisUtils.tryClose(jedis);
        }
        return result;
    }

}
