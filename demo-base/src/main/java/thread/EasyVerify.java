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

        Boolean bl = true;
        if(bl){
            System.out.println("AAA");
        }

        List<JSONObject> commandList = new ArrayList<JSONObject>();

        JSONObject a = commandList.get(0);

        JSONObject jo = new JSONObject();
        JSONObject jo2 = new JSONObject();


        try {

            jo.put("aa","aa");
            jo2.put("bb","bb");
            commandList.add(jo);
            commandList.add(jo2);

            System.out.println(commandList.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
