package com.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by mujiang on 2018/3/27.
 */

@RestController
public class RedisHandler {


    @Autowired
    CacheServiceByDB cacheServiceByDB;

    public void saveInDB(){
        cacheServiceByDB.setnx("db1111","11111111",1);
        cacheServiceByDB.pSubscribe(null, "*" ,1 );
//        cacheService2.pSubscribe(new KeySetRedisListenser(),"*",1);
    }


    @GetMapping(value = "/test")
    public String test( ) {
        saveInDB();
        return "ddddd";
    }


    public static void main(String[] args) {

        new RedisHandler().saveInDB();

//        ApplicationContext ac = new AnnotationConfigApplicationContext(RedisHandler.class);
//
//        CacheService cacheService = (CacheService) ac.getBean("cacheService");
//        cacheService.set("db000","db111");
//        cacheService.set("db000","db111");
    }


}
