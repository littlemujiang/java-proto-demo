import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import utils.JedisManager;

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

    private void subJedis(){
//        jedisManager = getJedisManager();
//        Jedis jedis = jedisManager.getJedis();
//        jedis.psubscribe(new UpListener(), "*");
        new Thread( new SubJedis()).start();
        new Thread( new SubJedis2()).start();

    }


    class SubJedis implements Runnable{

        @Override
        public void run() {
            Jedis jedis = new JedisManager().getJedis();
            jedis.psubscribe(new DownListener(), "__keyevent@1__:expired");
        }
    }

    class SubJedis2 implements Runnable{

        @Override
        public void run() {
            Jedis jedis = new JedisManager().getJedis();
            jedis.psubscribe(new UpListener(), "__keyevent@1__:set");
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
            Jedis jedis = new JedisManager().getJedis();
            jedis.select(1);
            jedis.del("ON_"+message);
            System.out.println("device: " + message + " DOWN!");
        }

    }

    class UpListener extends JedisPubSub {

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {
            System.out.println("onPSubscribe "
                    + pattern + " " + subscribedChannels);
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {

//        System.out.println("onPMessage pattern "
//                + pattern + " " + channel + " " + message);

            System.out.println("device: " + message + " UP!");

        }

//    @Override
//    public void onMessage(String channel, String message) {
//        System.out.println("onPMessage pattern "
//                + channel + " " + message);
//    }


    }

}





