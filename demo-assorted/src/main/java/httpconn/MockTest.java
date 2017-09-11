package httpconn;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mujiang on 2017/9/7.
 */
public class MockTest {




    public static void main(String[] args){

//        String urlString = "https://msaas.unifiedcloud.lenovo.com/my-service-name/my-api-name/users";
        String urlString = "https://msaas.unifiedcloud.lenovo.com/hs-coupon-service/couponToOrderApi/getMemberCoupons";

        Map<String, String> headerMap = new HashMap<String, String>(); //header参数
//        headerMap.put("Accept-Charset", "utf-8");
        headerMap.put("Content-type", "application/json");// 设置编码
//        headerMap.put("accessToken", "RjQ4M0E5N0FBNzU1NDRDN0FBRjcwMzE3RjE0MTFDN0MxNTAyNzkyMzU0NDkw");// 设置编码


        JSONObject bodyJSON = new JSONObject();
        bodyJSON.put("shopId", "1");
        bodyJSON.put("terminal", "1");
        bodyJSON.put("vid", "123");



        new EasyHttp().sentRequest(urlString, EasyHttp.POST , headerMap, bodyJSON);
//        new EasyHttp().sentRequest(urlString, "POST", headerMap, bodyJSON);

    }


}
