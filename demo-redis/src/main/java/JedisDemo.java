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

    private void resetInfo(){

        jedisManager = getJedisManager();
        Jedis jedis = jedisManager.getJedis();

//        String authInfo = jedis.hget("appkey02", "clientID02" );

//        if(authInfo == null)
//            jedis.hset("appkey02", "clientID02", "1");


        for(int i=0; i < 10 ; i++ ){

            for(int j=0; j < 10 ; j++ )

            jedis.hset("AUTH:appkey"+i, "clientID" + j, "1");
//            jedis.expire("appkey02", 20 );

        }

        jedisManager.release(jedis);

        System.out.println("---- done ----");

    }




    public static void main(String[] args){

        JedisDemo jedisDemo =  new JedisDemo();
//        jedisDemo.insertInfo();

        jedisDemo.resetInfo();

//        jedisDemo.getInfo();

    }


}
