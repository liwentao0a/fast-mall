package com.lwt.fastmall.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FastmallApiApplicationTests {

    @Test
    void contextLoads() throws Exception {
        long s1 = System.currentTimeMillis();
        for (int i=0;i<1000000;i++){
            try {
                int a=10/1;
            }catch (Exception e){
            }
        }
        long e1 = System.currentTimeMillis();
        System.out.println(s1 - e1);
        long s2 = System.currentTimeMillis();
        for (int i=0;i<1000000;i++){
            try {
                int a=10/0;
            }catch (Exception e){
            }
        }
        long e2 = System.currentTimeMillis();
        System.out.println(s2 - e2);
        long s3 = System.currentTimeMillis();
        for (int i=0;i<1000000;i++){
            int a=10/1;
        }
        long e3 = System.currentTimeMillis();
        System.out.println(s3 - e3);
    }

}
