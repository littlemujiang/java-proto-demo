package thread;

import java.util.HashMap;

/**
 * Created by mujiang on 2018/1/9.
 */
public class MapHandlerB implements Runnable{

    private HashMap map ;

    private MapHandlerA mhA ;

    public MapHandlerB(HashMap map){
        this.map = map;
    }

    public MapHandlerB(HashMap map , MapHandlerA mhA){
        this.map = map;
        this.mhA = mhA;
    }


    @Override
    public void run() {

        System.out.println("B : started");

        synchronized (mhA.getMap().getClass()){


                map.remove("2");
                System.out.println("B : " + map.toString());
//                Thread.sleep(1000);


        }

        System.out.println("B : done");

    }
}
