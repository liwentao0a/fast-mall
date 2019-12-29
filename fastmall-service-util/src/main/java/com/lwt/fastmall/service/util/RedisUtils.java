package com.lwt.fastmall.service.util;

import com.lwt.fastmall.api.constant.RedisKeyEnum;

/**
 * @author lwt
 * @date 2019/12/17 14:20
 */
public class RedisUtils {

    public static final String KEY_SPLIT_CHAR=":";

    public static String buildKey(String prefix,String module,String type,String field,String id){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(prefix)
                .append(KEY_SPLIT_CHAR)
                .append(module)
                .append(KEY_SPLIT_CHAR)
                .append(type);
        if (field!=null){
            stringBuffer.append(KEY_SPLIT_CHAR)
                    .append(field);
        }
        if (id!=null){
            stringBuffer.append(KEY_SPLIT_CHAR)
                    .append(id);
        }
        return stringBuffer.toString();
    }

    public static String buildKey(RedisKeyEnum redisKeyEnum, String field, String id){
        return buildKey(redisKeyEnum.getPrefix(), redisKeyEnum.getModule(), redisKeyEnum.getType(),field,id);
    }
}
