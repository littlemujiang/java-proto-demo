package com.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaohl on 2017/5/26.
 */
@Service("cacheService")
public class RedisServiceImpl implements CacheService {
    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    @Qualifier("iotRedisTemplate")
    private StringRedisTemplate template;

//    @Autowired
//    @Qualifier("iotRedisTemplateByDB")
//    private StringRedisTemplate templateByDB;


    @Autowired
    private EnableRedisConfig redisConfig;


//    private StringRedisTemplate templateByDB;

//    private void initTemplate(){
//        if(templateByDB == null){
//            templateByDB = new StringRedisTemplate();
//            templateByDB.setConnectionFactory(redisConfig.jedisConnectionFactoryByDB());
//            templateByDB.afterPropertiesSet();
//        }
//    }

    public void expire(String key, long expire) {
        this.template.expire(key, expire, TimeUnit.SECONDS);
        logger.debug("Set expire key={}, expire={}", key, expire);
    }

//    public void expire(String key, long expire , int db) {
////        initTemplate();
//        JedisConnectionFactory redisConnectionFactory = (JedisConnectionFactory) this.templateByDB.getConnectionFactory();
//        redisConnectionFactory.setDatabase(db);
//        this.templateByDB.expire(key, expire, TimeUnit.SECONDS);
//        logger.debug("Set expire key={}, expire={} in db={}", key, expire, db);
//    }

    public String get(String key) {
        logger.debug("Enter get() key={}", key);
        ValueOperations<String, String> ops = this.template.opsForValue();
        return ops.get(key);
    }

    public void set(String key, String value) {
        logger.debug("Enter set() key={}, value={}", key, value);
        ValueOperations<String, String> ops = this.template.opsForValue();
        ops.set(key, value);
    }

    public void set(String key, String value, long expire) {
        logger.debug("Enter set() key={}, value={}", key, value);
        ValueOperations<String, String> ops = this.template.opsForValue();
        ops.set(key, value, expire, TimeUnit.SECONDS);
    }

    public List<String> mget(Set<String> keys) {
        logger.debug("Enter mget() keys={}", keys);
        return template.opsForValue().multiGet(keys);
    }

    public void mset(Map<String, String> map) {
        logger.debug("Enter mset() keys={}, value={}", map.keySet(), map.values());
        template.opsForValue().multiSet(map);
    }

    public void remove(String key) {
        logger.debug("Enter remove() key={}", key);
        this.template.delete(key);
    }

    public String getHash(String key, String hashKey) {
        logger.debug("Enter getHash() key={},hashKey={}", key, hashKey);
        HashOperations<String, String, String> hashOps = this.template.opsForHash();
        return hashOps.get(key, hashKey);
    }

    public void setHash(String key, String hashKey, String value) {
        logger.debug("Enter setHash() key={},hashKey={}, value={}", key, hashKey, value);
        HashOperations<String, String, String> hashOps = this.template.opsForHash();
        hashOps.put(key, hashKey, value);
    }

    public void removeHash(String key, String hashKey) {
        logger.debug("Enter removeHash() key={} hashKey={}", key, hashKey);
        HashOperations<String, String, String> hashOps = this.template.opsForHash();
        hashOps.delete(key, hashKey);
    }

    public List<String> getHashValues(String key) {
        logger.debug("Enter getHashValues() key={}", key);
        HashOperations<String, String, String> hashOps = this.template.opsForHash();
        return hashOps.values(key);
    }

    public Map getHashEntries(String key) {
        logger.debug("Enter getHashEntries() key={}", key);
        HashOperations<String, String, String> hashOps = this.template.opsForHash();
        return hashOps.entries(key);
    }

    public Boolean incrementHashLong(String hashname, String itemkey, Long delta) {
        logger.debug("Enter hashIncrementLong() hashname={},itemkey={},delta={}", hashname, itemkey, delta);
        HashOperations<String, String, String> hashOps = this.template.opsForHash();

        String countStr = hashOps.get(hashname, itemkey);
        if (countStr == null) {
            hashOps.put(hashname, itemkey, delta + "");
        } else {
            hashOps.increment(hashname, itemkey, delta);//增加统计量
        }
        return true;
    }

    public List<String> mgetHash(String key, Set<String> keys) {
        logger.debug("Enter getHash() key={},hashKey.size={}", key, keys.size());
        HashOperations<String, String, String> hashOps = this.template.opsForHash();
        return hashOps.multiGet(key, keys);
    }

    public void msetHash(String key, Map<String, String> map) {
        logger.debug("Enter getHash() key={},hash.size={}", key, map.size());
        HashOperations<String, String, String> hashOps = this.template.opsForHash();
        hashOps.putAll(key, map);
    }

    public void mremoveHash(String key, String[] hashKeys) {
        logger.debug("Enter mremoveHash() key={} hashKeys={}", key, hashKeys);
        HashOperations<String, String, String> hashOps = this.template.opsForHash();
        hashOps.delete(key, hashKeys);
    }

//    public void setnx(String key, String value , int db){
//        logger.debug("Enter setnx() key={}, value={} in db={}", key, value, db);
////        initTemplate();
//        JedisConnectionFactory redisConnectionFactory = (JedisConnectionFactory) this.templateByDB.getConnectionFactory();
//        redisConnectionFactory.setDatabase(db);
//        this.templateByDB.opsForValue().setIfAbsent(key,value);
//    }
//
//    public void pSubscribe(MessageListener  messageListener ,  String pattern , int db){
//        logger.debug("Enter pSubscribe() parten={} ", pattern);
////        initTemplate();
//        JedisConnectionFactory redisConnectionFactory = (JedisConnectionFactory) this.templateByDB.getConnectionFactory();
//        redisConnectionFactory.setDatabase(db);
//        RedisConnection redisConnection = redisConnectionFactory.getConnection();
//        redisConnection.pSubscribe( messageListener, pattern.getBytes());
//    }

}






