package com.lwt.fastmall.service.tool.jedis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lwt
 * @date 2019/12/9 19:58
 */
@Component
@ConfigurationProperties(prefix = "service.jedis")
public class JedisProperties {

    private int maxTotal=200;//最大连接数, 默认8个
    private int maxIdle=20;//最大空闲连接数, 默认8个
    private long maxWaitMillis=2000;//获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
    private boolean isBlockWhenExhausted=true;//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
    private boolean isTestOnBorrow=true;//在获取连接的时候检查有效性, 默认false
    private String host="localhost";
    private int port=6379;
    private int timeout=2000;
    private String password=null;

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public boolean isBlockWhenExhausted() {
        return isBlockWhenExhausted;
    }

    public void setBlockWhenExhausted(boolean blockWhenExhausted) {
        isBlockWhenExhausted = blockWhenExhausted;
    }

    public boolean isTestOnBorrow() {
        return isTestOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        isTestOnBorrow = testOnBorrow;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
