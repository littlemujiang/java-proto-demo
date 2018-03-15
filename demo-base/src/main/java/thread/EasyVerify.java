package thread;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by mujiang on 2017/10/26.
 */


public class EasyVerify {


    private void runThread(){

        HashMap<String, String> map = new HashMap<String, String>();

        map.put("1","1");
        map.put("2","2");
        map.put("3","3");

        MapHandlerA mhA = new MapHandlerA(map);
//        MapHandlerB mhB = new MapHandlerB(map);
        MapHandlerB mhB = new MapHandlerB(map , mhA);

        Thread mhTA = new Thread(mhA);
        Thread mhTB = new Thread(mhB);

        System.out.println("----- : " + map.toString());

        mhTA.start();
        mhTB.start();


    }




    public static void main(String[] args){


//        new EasyVerify().runThread();

        Map<String,Object> m = new HashMap();
        m.put("111","aaa");
        m.put("222","aaa");
        m.put("333","aaa");

        System.out.println(m);

        ArrayList a = new ArrayList();
//        Set<String> s = m.keySet();
        for(String key : m.keySet()){
            a.add(key);
        }

        System.out.println("a: "+a.toString());

        Map<String, String> n = new HashMap();
        n.put("fresh", a.toString());
        System.out.println(n.get("fresh"));


        String fresh = n.get("fresh");
        fresh = fresh.substring(1,fresh.length()-1);

        String[] freshArray = {};
        if(fresh.length()>0){
            freshArray = fresh.split(",");
        }
        List freshList = new ArrayList();
        for(String s : freshArray){
            freshList.add(s.trim());
        }

        System.out.println(freshList.contains("222"));



    }

}
