package com.lwt.fastmall.api.mapper.tk;

import com.lwt.fastmall.api.bean.PmsSkuInfo;
import com.lwt.fastmall.api.mapper.TkMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsSkuInfoMapper extends TkMapper<PmsSkuInfo> {

    public List<PmsSkuInfo> selectGroupBy(@Param("fieldName") String fieldName);

}
