package com.lwt.fastmall.service.tool.jedis.config;

import com.lwt.fastmall.service.tool.jedis.JedisFactory;
import com.lwt.fastmall.service.tool.jedis.JedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author lwt
 * @date 2019/12/17 13:56
 */
@Configuration
@EnableConfigurationProperties(JedisProperties.class)
public class JedisConfig {

    private JedisProperties jedisProperties;

    public JedisConfig(JedisProperties jedisProperties){
        this.jedisProperties=jedisProperties;
    }

    @Bean
    public JedisFactory jedisFactory(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(jedisProperties.getMaxTotal());//最大连接数, 默认8个
        jedisPoolConfig.setMaxIdle(jedisProperties.getMaxIdle());//最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxWaitMillis(jedisProperties.getMaxWaitMillis());//获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setBlockWhenExhausted(jedisProperties.isBlockWhenExhausted());//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setTestOnBorrow(jedisProperties.isTestOnBorrow());//在获取连接的时候检查有效性, 默认false
//        return JedisFactory.init(jedisPoolConfig, jedisProperties.getHost(), jedisProperties.getPort(),
//                jedisProperties.getTimeout(),jedisProperties.getPassword());
        return JedisFactory.init(jedisPoolConfig, "172.19.77.242", jedisProperties.getPort(),
                jedisProperties.getTimeout(),jedisProperties.getPassword());
    }

}
