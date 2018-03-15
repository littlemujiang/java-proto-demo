package thread;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mujiang on 2018/1/9.
 */
public class MapHandlerA implements Runnable{

    private HashMap map ;

    public MapHandlerA(HashMap map){
        this.map = map;
    }

    public HashMap getMap(){
        return map;
    }


    @Override
    public void run() {

        System.out.println("A : started");

        synchronized (map.getClass()){

            try {

                System.out.println("A : waiting 1");
                Thread.sleep(3000);
                map.remove("1");
                System.out.println("A : " + map.toString());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

        System.out.println("A : release resource");

        try {

            Thread.sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("A : done");

    }
}
