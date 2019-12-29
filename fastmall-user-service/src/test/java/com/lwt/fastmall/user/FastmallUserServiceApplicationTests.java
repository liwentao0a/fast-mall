package com.lwt.fastmall.user;

import com.alibaba.fastjson.JSON;
import com.lwt.fastmall.service.tool.jedis.JedisFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FastmallUserServiceApplicationTests {

    @Autowired
    private JedisFactory jedisFactory;

    @Test
    void contextLoads() {
        String string = JSON.toJSONString(null);
        System.out.println(string);


    }

}
