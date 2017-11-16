package utils;

/**
 * Created by chenxl7 on 2017/09/22.
 */

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
//@ConfigurationProperties(prefix = "gateway.redis.jedis")
public class JedisManager {

//    @Value("${gateway.redis.jedis.ip}")
//    private String ip;
//    @Value("${gateway.redis.jedis.port}")
//    private int port;
//    @Value("${gateway.redis.jedis.auth}")
//    private String auth;

    private String ip = "localhost";
    private int port = 6379;
    private String auth;

//    private  String ip;
//    private  int port=6379;
//    private  String auth;
    private  int redisMaxTotal=500;
    private  int redisMaxIdle=50;

    private static JedisPool pool;
    private static int timeout = 15000;

    public JedisManager() {

    }

//    public static JedisManager getJedisManager(){
//
//        return  new JedisManager();
//    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getRedisMaxTotal() {
        return redisMaxTotal;
    }

    public void setRedisMaxTotal(int redisMaxTotal) {
        this.redisMaxTotal = redisMaxTotal;
    }

    public int getRedisMaxIdle() {
        return redisMaxIdle;
    }

    public void setRedisMaxIdle(int redisMaxIdle) {
        this.redisMaxIdle = redisMaxIdle;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    /**
     * 生成连接池
     */
    private void createJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(getRedisMaxTotal());// 最大连接数,负值表示没限制
        config.setMaxIdle( getRedisMaxIdle());// 池中空闲的对象的最大数量,负值表示没限制
        config.setBlockWhenExhausted(false);// 超出连接数是否阻塞
        // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, ip, port , timeout,
                auth);

    }

    /**
     * 获取Jedis
     */
    public Jedis getJedis() {
        if (pool == null)
            createJedisPool();
        Jedis jedis = pool.getResource();
        return jedis;
    }

    /**
     * 设置数据
     */
    public  void set(String key, String value) {
        Jedis jedis = null;
        try {
            if (pool == null)
                createJedisPool();
            jedis = getJedis();
            jedis.set(key, value);
        } finally {
            release(jedis);
        }

    }

    /**
     * 删除
     * @param key
     */
    public Long del(String key){
        Jedis jedis = null;
        try{
            createJedisPool();
            jedis = getJedis();
            return jedis.del(key);
        } finally {
            release(jedis);
        }

    }


    /**
     * 设置过期时间
     * @param key
     * @param seconds
     */
    public  void setExpire(String key, int seconds) {
        Jedis jedis = null;
        try {
            if (pool == null)
                createJedisPool();
            jedis = getJedis();
            jedis.expire(key, seconds);
        } finally {
            release(jedis);
        }

    }

    public  void hmset(String key, Map<String, String> map) {
        Jedis jedis = null;
        try {
            if (pool == null)
                createJedisPool();
            jedis = getJedis();
            jedis.hmset(key, map);

        } finally {
            release(jedis);
        }
    }


    /**
     * 取出数据
     */
    public  List<String> hmget(String key, String... mapKeys) {
        Jedis jedis = null;
        try {
            if (pool == null)
                createJedisPool();
            jedis = getJedis();
            List<String> list = jedis.hmget(key, mapKeys);
            return list;
        } finally {
            release(jedis);
        }
    }

    /**
     *  取出数据
     */
    public  String getUnique(String key) {
        Jedis jedis = null;
        try {
            if (pool == null)
                createJedisPool();
            jedis = getJedis();
            return jedis.get(key);
        } finally {
            release(jedis);
        }
    }

    /**
     * 模糊查询取出数据
     */

    public  String getLike(String token) {
        Jedis jedis = null;
        try {
            if (pool == null)
                createJedisPool();
            jedis = getJedis();
            return jedis.get(token+"_*");
        } finally {
            release(jedis);
        }
    }

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


    public String hget(String key, String value) {
        Jedis jedis = null;
        try {
            if (pool == null)
                createJedisPool();
            jedis = getJedis();
            return jedis.hget(key, value);
        } finally {
            release(jedis);
        }
    }

    public long hset(String var1, String var2, String var3) {
        Jedis jedis = null;
        try {
            if (pool == null)
                createJedisPool();
            jedis = getJedis();
            return jedis.hset(var1, var2, var3);
        } finally {
            release(jedis);
        }
    }

}
