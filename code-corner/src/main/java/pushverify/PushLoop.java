package pushverify;


import com.alibaba.fastjson.JSONObject;
import httpconn.EasyHttp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mujiang on 2017/9/5.
 */
public class PushLoop {

//    String urlString = "https://wngfp.unifiedcloud.lenovo.com/v1/tenants/lenovo/apps/1B7EC8A40C55448B9097F1F9DFF9C379/service/push/jpush/message";
    private String urlString2 = "https://msaas.unifiedcloud.lenovo.com/my-service-name/my-api-name/users";

    private EasyHttp easyHttp = new EasyHttp();
    Map<String, String> headerMap = new HashMap<String, String>();
    JSONObject bodyJSON = null ;


    public void pushMsg(){

        headerMap = headerInit();
//        bodyJSON = bodyInit();

        easyHttp.sentRequest(urlString2, EasyHttp.GET, headerMap, null);

    }

    private Map<String, String> headerInit(){

        Map<String, String> headerMap = new HashMap<String, String>(); //header参数

//        headerMap.put ("Accept-Charset", "utf-8");
//        headerMap.put("Content-type", "application/json");// 设置编码
//        headerMap.put("accessToken", "RjQ4M0E5N0FBNzU1NDRDN0FBRjcwMzE3RjE0MTFDN0MxNTAyNzkyMzU0NDkw");// 设置编码

        headerMap.put("Content-type", "application/json");// 设置编码

        return headerMap;
    }

    private JSONObject bodyInit(){

        JSONObject bodyJSON = new JSONObject();
        bodyJSON.put("pushcontent", "内容！！！！！！！");
        bodyJSON.put("pushmem", "raohl1");
        bodyJSON.put("title", "标 题");
        bodyJSON.put("txtras", "{\"times\":\"2\",\"avatar\":\"20170822184359011.jpg\",\"favourite\":\"X1 Carbon,Moto Z2,XT1080\",\"customerName\":\"未知\"}");

        return bodyJSON;
    }


    public static void main(String[] args){

        new PushLoop().pushMsg();

    }


}
