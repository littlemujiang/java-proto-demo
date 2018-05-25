package com.redis;

import org.springframework.data.redis.connection.MessageListener;
import redis.clients.jedis.JedisPubSub;

public interface CacheServiceByDB {


    void expire(String key, int expire, int db);

    void setnx(String key, String value, int db);

    void pSubscribe(JedisPubSub messageListener, String pattern, int db);
}
