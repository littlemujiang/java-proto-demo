import redis.clients.jedis.Jedis;
import utils.JedisManager;

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




    public static void main(String[] args){

        JedisDemo jedisDemo =  new JedisDemo();
//        jedisDemo.insertInfo();


        try {
            Thread.sleep(1000);
            jedisDemo.produceEvent();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        jedisDemo.getInfo();

    }



}

