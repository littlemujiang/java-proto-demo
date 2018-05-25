package com.redis;

/**
 * Created by chenxl7 on 2017/09/22.
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;

/**
 * Jedis连接管理
 *
 * @author pc
 *
 */

@Configuration
public class JedisClientManager {

    @Value("${spring.redis.database:0}")
    private int database;

    @Value("${spring.redis.host:10.76.3.66}")
    private String host;

    @Value("${spring.redis.port:6379}")
    private int port;

    @Value("${spring.redis.password:redis123}")
    private String password;

    private  int redisMaxTotal=5000;
    private  int redisMaxIdle=50;
    private static int timeout = 15000;

    private static JedisPool pool;
    /**
     * 生成连接池
     */
    private void createJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(redisMaxTotal);// 最大连接数,负值表示没限制
        config.setMaxIdle(redisMaxIdle);// 池中空闲的对象的最大数量,负值表示没限制
        config.setBlockWhenExhausted(false);// 超出连接数是否阻塞
        // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, host, port, timeout, password);

    }

    /**
     * 获取Jedis
     */
    public Jedis getJedis() {
        if (pool == null){
            createJedisPool();
        }
        Jedis jedis = pool.getResource();
        return jedis;
    }

//    public  void hmset(String key, Map<String, String> map) {
//        Jedis jedis = null;
//        try {
//            if (pool == null)
//                createJedisPool();
//            jedis = getJedis();
//            jedis.hmset(key, map);
//
//        } finally {
//            release(jedis);
//        }
//    }
//

    /**
     * 释放资源
     */
    public void release(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
