package com.lwt.fastmall.common.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author lwt
 * @date 2019/12/16 22:24
 */
public class ObjectUtils {

    public static boolean isBlank(Object object){
        if (object==null){
            return true;
        }
        if (object instanceof String){
            String string= (String) object;
            if ("".equals(string)){
                return true;
            }
        }else if (object instanceof Collection){
            Collection collection= (Collection) object;
            if (collection.size()<=0){
                return true;
            }
        }else if (object instanceof Map){
            Map map= (Map) object;
            if (map.size()<=0){
                return true;
            }
        }
        return false;
    }
}
