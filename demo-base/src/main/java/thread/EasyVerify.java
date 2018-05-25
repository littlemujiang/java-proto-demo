package thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.json.JSONException;
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

        JSONObject sensorData = new JSONObject();
        JSONObject sensor = new JSONObject();


        try {
            sensor.put("sensorCode",50);
            sensor.put("sensorName", 50.5);


            String aa = JSON.toJSONString(sensor);

            Map<String,String> shadowMap = new HashMap<String, String>();
            shadowMap.put("aa",aa);


            JSONObject shadow = JSON.parseObject(shadowMap.get("aa").toString());
            JSONObject shadow2 = (JSONObject) JSONObject.parse(shadowMap.get("aa"));


            Object a = shadow.get("sensorName");
            Object a2 = shadow2.get("sensorName");

            System.out.println(shadow);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
