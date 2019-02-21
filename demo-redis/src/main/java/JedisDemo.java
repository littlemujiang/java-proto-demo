import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import utils.JedisManager;

import java.util.Map;
import java.util.Random;

/**
 * Created by mujiang on 2017/9/22.
 */


public class JedisDemo {

    private JedisManager jedisManager;


    public JedisManager getJedisManager() {

        if(jedisManager == null)
            this.jedisManager = new JedisManager();

        return this.jedisManager;
    }


    private void insertInfo(){

        jedisManager = getJedisManager();

        jedisManager.hset("appkey02", "clientID01", "1");

    }

    private void getInfo(){

        JedisManager jedisManager = new JedisManager();
        Jedis jedis = jedisManager.getJedis();
//        String authInfo = jedisManager.hget("appkey02", "clientID11");
        long time = jedis.ttl( "clientID11" );

        System.out.println(time);

    }

    private void produceEvent() {

        jedisManager = getJedisManager();
        Jedis jedis = jedisManager.getJedis();
        jedis.select(0);

//        String authInfo = jedis.hget("appkey02", "clientID02" );

//        if(authInfo == null)
//            jedis.hset("appkey02", "clientID02", "1");


//        jedis.set("heartbeat-test"+0,  "aaa" );
//        jedis.set("heartbeat-test"+1,  "aaa" );
//        jedis.set("heartbeat-test"+2,  "aaa" );

        for (int i = 0; i < 10; i++) {

            if(i<5){
                jedis.set("heartbeat-test"+i,  "aaa" );
            }else {
                jedis.setex("heartbeat-test"+i,15, "bbb");
            }
//            jedis.set("heartbeat-test"+0,  "aaa" );
        }
        jedis.hset("heartbeat",  "deviceID", "bbbb" );
//        jedis.expire("heartbeat-test", 10 );
        jedisManager.release(jedis);
        System.out.println("---- done ----");

    }


    public void queryBatch(){
        jedisManager = getJedisManager();
        Jedis jedis = jedisManager.getJedis();
        jedis.select(1);

        String[] keyList = {"a_6pZCl/6qH2A/p_6qH7U","a_pipelineTestId/6lajn/p_6lajp"};
//        Map<String,String> content = jedis.hmget("mqttTopicState", keyList);
//        String a = jedis.hget("connDeviceList","1bad87881aaa4d729b544117c73db8b");
        String a = jedis.hget("deviceMsgInterval","1bad87881aaa4d729b54411");

        System.out.println("transfer done:" + a );
    }

    public void hdel(){
        jedisManager = getJedisManager();
        Jedis jedis = jedisManager.getJedis();
        jedis.select(1);

        String key = "flagDeviceList";
        String field = "54f7bbb8023f419c8d8bd3c805f25d19";
        jedis.hdel(key, field);

        System.out.println("hdel done: " + field );
    }


    public void transferContent(){
        jedisManager = getJedisManager();
        Jedis jedis = jedisManager.getJedis();
        jedis.select(0);
        Map<String,String> content = jedis.hgetAll("topicClientIdMqttMapV2");
        for(String key : content.keySet()){
            jedis.hset("mqttTopicServer", key, "172.28.70.4:6016");
        }
        System.out.println("transfer done");
    }




    public static void main(String[] args){

        JedisDemo jedisDemo =  new JedisDemo();

//        jedisDemo.transferContent();
//        jedisDemo.queryBatch();
//        jedisDemo.insertInfo();

        jedisDemo.hdel();


        System.out.println(new Random().nextBoolean());

//        try {
//            Thread.sleep(1000);
//            jedisDemo.produceEvent();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }





//        jedisDemo.getInfo();

    }



}

