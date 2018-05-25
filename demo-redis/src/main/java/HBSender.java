import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import utils.JedisManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by mujiang on 2017/9/22.
 */

@Component
public class HBSender {


    private StringRedisTemplate template = new StringRedisTemplate();


    private JedisManager jedisManager;


    public JedisManager getJedisManager() {

        if(jedisManager == null)
            this.jedisManager = new JedisManager();

        return this.jedisManager;
    }


    private void produceEvent2Channel() {

        jedisManager = getJedisManager();
        Jedis jedis = jedisManager.getJedis();
        jedis.select(1);

        int hb_interval = 10;

        try {
            jedis.setex( "deviceid-1", hb_interval ,"11111" );
            jedis.setnx( "ON_deviceid-1" ,"1" );
            int flag = 0;
            while (true){
                jedis.setex( "deviceid-2", hb_interval+3 ,"22222" );
                jedis.setnx( "ON_deviceid-2","1" );
                flag++;
                System.out.println("send hb from deviceid-2");
//                if(flag > 1){
//                    Thread.sleep(hb_interval*2000);
//                }else {
//                    Thread.sleep(hb_interval*1000);
//                }

                Thread.sleep(hb_interval*1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void produceEvent1Channel() {

        jedisManager = getJedisManager();
        Jedis jedis = jedisManager.getJedis();
        jedis.select(1);

        int hb_interval = 10;

        try {
            jedis.setnx( "deviceid-1" ,"1" );
            jedis.expire("deviceid-1" , hb_interval);

            int flag = 0;
            while (true){
                jedis.setnx( "deviceid-2" , String.valueOf(System.currentTimeMillis()) );
                jedis.expire("deviceid-2" , hb_interval + 3);
                flag++;

                if(flag%3 == 1){
                    System.out.println("send broken hb from deviceid-2 : sleep 15s" );
                    Thread.sleep(hb_interval*1500);
                }else {
                    System.out.println("send normal hb from deviceid-2 : sleep 10s" );
                    Thread.sleep(hb_interval*1000);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void produceEvent0() {

        jedisManager = getJedisManager();
        Jedis jedis = jedisManager.getJedis();
        jedis.select(1);

        int hb_interval = 10;

//        jedis.setnx( "deviceid-3" , String.valueOf(System.currentTimeMillis()) );
//        jedis.expire("deviceid-3" , hb_interval + 3);

//        jedis.hsetnx("list", "aaa", "1");
//        jedis.hsetnx("list", "aaa", "0");
//        jedis.hsetnx("list", "bbb", "1");

        jedis.hdel("list", "aaa");

        System.out.println("send hb @" + String.valueOf(new Date()));

    }


    public void produceEvent2() {

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setUsePool(true);
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.afterPropertiesSet();

        template.setConnectionFactory(jedisConnectionFactory);
        template.afterPropertiesSet();


        JedisConnectionFactory redisConnectionFactory = (JedisConnectionFactory) template.getConnectionFactory();
        redisConnectionFactory.setDatabase(1);

        template.opsForValue().set("aaaa","111111");
//        template.expire("db1-qqq", 20 , TimeUnit.SECONDS);
        System.out.println("send hb: 1111 @" + String.valueOf(new Date()));
        template.opsForValue().set("bbbb","000000");
        template.expire("db0-www", 30 , TimeUnit.SECONDS);
        System.out.println("send hb: 0000 @" + String.valueOf(new Date()));

//        jedisConnectionFactory = (JedisConnectionFactory) template.getConnectionFactory();
//        RedisConnection redisConnection = jedisConnectionFactory.getConnection();
//        RedisConnection redisConnection2 = jedisConnectionFactory.getConnection();
//        redisConnection.pSubscribe(new RedisListener(), pattern.getBytes());
//        redisConnection2.pSubscribe(new RedisListener(), pattern.getBytes());

        System.out.println("~~~~~~~~~~~");

    }


    private int getDbFromChannel(String channel){
        int db = 0;
        if(!channel.contains("@")){
            return db;
        }else {
            String[] tmp = channel.split("@");
            db = Integer.valueOf( tmp[1].substring(0,1) );
        }

        return db;
    }

    public static void main(String[] args){

        HBSender sender =  new HBSender();
        sender.produceEvent0();
//        sender.produceEvent2();

//        System.out.println(sender.getDbFromChannel("__keyevent@0__:set"));

    }


}

