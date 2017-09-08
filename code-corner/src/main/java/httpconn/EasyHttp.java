package httpconn;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mujiang on 2017/8/31.
 */

public class EasyHttp {

    public final static String GET = "GET";
    public final static String POST = "POST";
    public final static String PUT = "PUT";
    public final static String DELETE = "DELETE";

    public void sentRequest(String url, String method, Map<String,String> headerMap, JSONObject bodyJSON){
        try{
            URL urlClass = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlClass.openConnection();
            connection.setRequestMethod(method);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("charset", "utf-8");
            connection.setConnectTimeout(6000);

            if(headerMap.size() > 0){
                for(Map.Entry<String,String> entry: headerMap.entrySet()){
                    connection.setRequestProperty(entry.getKey(),entry.getValue());
                }
            }

            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream dataOutput = new DataOutputStream(outputStream);

            if((method.equals(EasyHttp.POST) || method.equals(EasyHttp.PUT)) && bodyJSON != null ){
                dataOutput.writeBytes(bodyJSON.toString());
            }

//            if(bodyJSON != null ){
//                dataOutput.writeBytes(bodyJSON.toString());
//            }

            JSONObject result = executeRequest(connection, dataOutput);

            System.out.println(result.toString());

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private JSONObject executeRequest(HttpURLConnection connection, DataOutputStream dataOutput){

        JSONObject resultJSON = new JSONObject();

        try {

            dataOutput.flush();
            dataOutput.close();

            int statusCode = connection.getResponseCode();

            BufferedReader resultBuffer;
            StringBuilder resultBuild = new StringBuilder();
            String resultLine;

            if(statusCode >= 200 & statusCode < 300) {
                resultBuffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((resultLine = resultBuffer.readLine()) != null){
//                    System.out.println("response: "+resultLine);
                    resultBuild.append(resultLine);
                }

                resultJSON.put("body", resultBuild.toString());
            }else {
                resultBuffer = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

                while ((resultLine = resultBuffer.readLine()) != null){
//                    System.out.println("response: "+resultLine);
                    resultBuild.append(resultLine);
                }

                resultJSON.put("body", connection.getResponseMessage());
                resultJSON.put("description", resultBuild.toString());
            }

            resultJSON.put("code", statusCode);

        }catch (Exception e){
            e.printStackTrace();
        }
        return resultJSON;
    }


    public static void main(String[] args){

//        new EasyHttp().sendRequest();

        String urlString = "https://wngfp.unifiedcloud.lenovo.com/v1/tenants/lenovo/apps/1B7EC8A40C55448B9097F1F9DFF9C379/service/push/jpush/message";
        String urlString2 = "https://msaas.unifiedcloud.lenovo.com/my-service-name/my-api-name/users";

        Map<String, String> headerMap = new HashMap<String, String>(); //header参数
        headerMap.put("Accept-Charset", "utf-8");
        headerMap.put("Content-type", "application/json");// 设置编码
        headerMap.put("accessToken", "RjQ4M0E5N0FBNzU1NDRDN0FBRjcwMzE3RjE0MTFDN0MxNTAyNzkyMzU0NDkw");// 设置编码


        Map<String, String> bodyMap = new HashMap<String, String>(); //body参数
        bodyMap.put("pushcontent", "内容！！！！！！！");
        bodyMap.put("pushmem", "raohl1");
        bodyMap.put("title", "标 题");
        bodyMap.put("txtras", "{\"times\":\"2\",\"avatar\":\"20170822184359011.jpg\",\"favourite\":\"X1 Carbon,Moto Z2,XT1080\",\"customerName\":\"未知\"}");


        JSONObject bodyJSON = new JSONObject();
        bodyJSON.put("pushcontent", "内容！！！！！！！");
        bodyJSON.put("pushmem", "raohl1");
        bodyJSON.put("title", "标 题");
        bodyJSON.put("txtras", "{\"times\":\"2\",\"avatar\":\"20170822184359011.jpg\",\"favourite\":\"X1 Carbon,Moto Z2,XT1080\",\"customerName\":\"未知\"}");

        Map<String, String> headerMap2 = new HashMap<String, String>(); //header参数
        headerMap2.put("Content-type", "application/json");// 设置编码

        new EasyHttp().sentRequest(urlString2, "GET", headerMap2, null);
//        new EasyHttp().sentRequest(urlString, "POST", headerMap, bodyJSON);

    }



}


