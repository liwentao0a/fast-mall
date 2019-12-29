package com.lwt.fastmall.service.tool.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author lwt
 * @date 2019/12/17 13:57
 */
public class JedisFactory {

    private long getResourceErrTime=0;
    private int getResourceErrCount=0;
    private long getResourceErrIntervals=60000;

    private Thread thread;

    private JedisPool jedisPool;

    private JedisFactory(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public static JedisFactory init(JedisPool jedisPool){
        return new JedisFactory(jedisPool);
    }

    public static JedisFactory init(JedisPoolConfig jedisPoolConfig,String host,int port,int timeout,String password){
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout,password);
        return init(jedisPool);
    }

    public static JedisFactory init(JedisPoolConfig jedisPoolConfig, String host, int port, int timeout){
        return init(jedisPoolConfig, host, port, timeout,null);
    }

    public Jedis tryGetJedis(){
        if (getResourceErrCount>=3){
            return null;
        }
        try {
            Jedis jedis = jedisPool.getResource();
            getResourceErrCount=0;
            return jedis;
        }catch (Exception e){
            getResourceErr();
            return null;
        }
    }

    private void getResourceErr() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis-getResourceErrTime>getResourceErrIntervals){
            getResourceErrCount=0;
        }else {
            getResourceErrCount++;
            if (getResourceErrCount>=3&&thread==null){
                synchronized (this){
                    if (getResourceErrCount>=3&&thread==null){
                        thread=new Thread(){
                            @Override
                            public void run() {
                                try {
                                    sleep(300000);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                                getResourceErrCount=0;
                                thread=null;
                            }
                        };
                        thread.start();
                    }
                }
            }
        }
        getResourceErrTime=currentTimeMillis;
    }

}
