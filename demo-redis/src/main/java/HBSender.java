import redis.clients.jedis.Jedis;
import utils.JedisManager;

/**
 * Created by mujiang on 2017/9/22.
 */


public class HBSender {

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

        jedis.setnx( "deviceid-3" , String.valueOf(System.currentTimeMillis()) );
        jedis.expire("deviceid-3" , hb_interval + 3);

    }

    public static void main(String[] args){
        HBSender sender =  new HBSender();
//        sender.produceEvent();
        sender.produceEvent0();
    }


}

