package com.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Date;

/**
 * Created by zhaohl on 2017/5/26.
 */
@Service("cacheServiceByDB")
public class RedisServiceImplByDB implements CacheServiceByDB {
    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImplByDB.class);

    @Autowired
    private JedisClientManager jedisConfigManager;

    public void expire(String key, int expire , int db) {
        Jedis jedis = jedisConfigManager.getJedis();
        try {
            jedis.select(db);
            jedis.expire(key, expire);
        }finally {
            jedisConfigManager.release(jedis);
        }
        logger.debug("Set expire key={}, expire={} in db={}", key, expire, db);
    }

    public void setnx(String key, String value , int db){
        Jedis jedis = jedisConfigManager.getJedis();
        try {
            jedis.select(db);
            jedis.setnx(key,value);
        }finally {
            jedisConfigManager.release(jedis);
        }
        logger.debug("Enter setnx() key={}, value={} in db={}", key, value, db);
    }

    public void pSubscribe(JedisPubSub  jedisPubSub ,  String pattern , int db){
        logger.debug("Enter pSubscribe() db={}, parten={} ", db, pattern);
        Jedis jedis4sub = jedisConfigManager.getJedis();
        jedis4sub.select(db);
        jedis4sub.psubscribe(jedisPubSub, pattern);
    }

}

class UpListener extends JedisPubSub {

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe " + pattern + " " + subscribedChannels);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {

//        System.out.println("onPMessage pattern "
//                + pattern + " " + channel + " " + message);

        System.out.println("device: " + message + " UP!   @ " + String.valueOf(new Date()));

    }


}


