import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import utils.JedisManager;

import java.util.Date;

/**
 * Created by mujiang on 2017/9/22.
 */


public class HBListener {

    private JedisManager jedisManager;


    public JedisManager getJedisManager() {

        if(jedisManager == null)
            this.jedisManager = new JedisManager();

        return this.jedisManager;
    }

    public void subJedis(){
        jedisManager = getJedisManager();
        Jedis jedis = jedisManager.getJedis();
//        jedis.psubscribe(new UpListener(), "*");
//        jedis.psubscribe(new DownListener(), "*");
        new Thread( new SubJedis()).start();
        new Thread( new SubJedis2()).start();

    }


    class SubJedis implements Runnable{

        @Override
        public void run() {
            Jedis jedis = new JedisManager().getJedis();
            jedis.select(5);
            jedis.psubscribe(new DownListener(), "__keyevent@[0-1]__:expired");
        }
    }

    class SubJedis2 implements Runnable{

        @Override
        public void run() {
            Jedis jedis = new JedisManager().getJedis();
            jedis.select(5);
            jedis.psubscribe(new UpListener(), "__keyevent@[0-1]__:set");
//            jedis.psubscribe(new UpListener(), "*");
        }
    }



    public static void main(String[] args){

        HBListener listener =  new HBListener();
        listener.subJedis();

    }

    class DownListener extends JedisPubSub {

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {
            System.out.println("onPSubscribe "
                    + pattern + " " + subscribedChannels);
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {

            System.out.println("pattern: " + pattern );
            System.out.println("channel: " + channel );
            System.out.println("message: " + message );

            System.out.println("device: " + message + " DOWN! @  "  + String.valueOf(new Date()));
        }

    }

    class UpListener extends JedisPubSub {

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {
            System.out.println("onPSubscribe " + pattern + " " + subscribedChannels);
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {

            System.out.println("pattern: " + pattern );
            System.out.println("channel: " + channel );
            System.out.println("message: " + message );

            System.out.println("device: " + message + " UP!   @ " + String.valueOf(new Date()));

        }

//    @Override
//    public void onMessage(String channel, String message) {
//        System.out.println("onPMessage pattern "
//                + channel + " " + message);
//    }


    }

}





