package com.lwt.fastmall.common.util;

import com.lwt.fastmall.api.bean.Result;
import com.lwt.fastmall.api.constant.CodeEnum;

/**
 * @author lwt
 * @date 2019/12/16 22:10
 */
public class ResultUtils {

    public static<T> Result<T> result(CodeEnum codeEnum, T data){
        return new Result<>(codeEnum.getCode(),codeEnum.getMessage(),data);
    }

    public static<T> Result<T> result(CodeEnum codeEnum){
        return result(codeEnum,null);
    }

    public static<T> Result<T> success(T data){
        return result(CodeEnum.SUCCESS,data);
    }

    public static<T> Result<T> success(){
        return success(null);
    }

    public static boolean isSuccess(Result result){
        if (result.getCode()==CodeEnum.SUCCESS.getCode()){
            return true;
        }
        return false;
    }

    public static boolean isSuccess(Result... results){
        for (Result result : results) {
            if (!isSuccess(result)){
                return false;
            }
        }
        return true;
    }
}
