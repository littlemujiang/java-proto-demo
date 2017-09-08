package httpconn;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import thirdpart.HttpResult;
import thirdpart.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by epcm on 2017/8/29.
 */
public class Http4RestTmp {

    @Autowired
    private RestTemplate restTemplate;

    private void callHttp(){
        String uri = "https://wngfp.unifiedcloud.lenovo.com/v1/tenants/lenovo/apps/1B7EC8A40C55448B9097F1F9DFF9C379/service/push/jpush/message";

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Accept-Charset", "utf-8");
        headerMap.put("Content-type", "application/json");// 设置编码
        headerMap.put("accessToken", "RjQ4M0E5N0FBNzU1NDRDN0FBRjcwMzE3RjE0MTFDN0MxNTAyNzkyMzU0NDkw");// 设置编码

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("pushcontent", "内容！！！！！！！");
        paramMap.put("pushmem", "raohl1");
        paramMap.put("title", "标 题");
        paramMap.put("txtras", "{\"times\":\"2\",\"avatar\":\"20170822184359011.jpg\",\"favourite\":\"X1 Carbon,Moto Z2,XT1080\",\"customerName\":\"未知\"}");


        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Charset", "utf-8");
        headers.add("Content-type", "application/json");// 设置编码
        headers.add("accessToken", "RjQ4M0E5N0FBNzU1NDRDN0FBRjcwMzE3RjE0MTFDN0MxNTAyNzkyMzU0NDkw");// 设置编码

//        HttpEntity requestEntity  = new HttpEntity(paramMap, headers);
//        ResponseEntity re = this.restTemplate.postForEntity(uri, requestEntity, Response.class);

        try{
            HttpResult httpResult = HttpUtils.getInstance().send(uri, HttpUtils.POST, headerMap, paramMap);

        }catch (Exception e){
            e.printStackTrace();
        }

//        System.out.println();

        }



    public static void main(String[] args) {
        // TODO �Զ����ɵķ������
        Http4RestTmp h = new Http4RestTmp();

        h.callHttp();

    }


}
