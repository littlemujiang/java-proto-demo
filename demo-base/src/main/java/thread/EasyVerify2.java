package thread;

import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mujiang on 2017/10/26.
 */


public class EasyVerify2 {


    public Date parseDateFromString(String dateStr, Boolean isReturnZero) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateOrigin = null;
        try {
            dateOrigin = sdf.parse("2016-01-01 08:00:00");
            Date date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            if (isReturnZero) {
                return dateOrigin;
            } else {
                return new Date();
            }
        }
    }

    public void timeline(){

        Date dateOrigin = parseDateFromString("1970-01-01 08:00:00.000", true);

        Date startDate = parseDateFromString("2018-11-22 08:00:00.000", true);
        Date endDate = parseDateFromString("2018-11-23 08:00:00.000", true);

        long startTime = startDate.getTime();
        long endTime = endDate.getTime();

        long msgTime = 1542862810353L;

        List<Long> timeline = new ArrayList<Long>();

        long size = (endTime - startTime)/(60*1000);

        for(int i = 0; i < size; i++){
            timeline.add(startTime - startTime % (60*1000) );
            startTime += 60*1000;
        }

        long msgTime2 = msgTime - msgTime % (60*1000);


        System.out.println(timeline);

    }


    public static void main(String[] args){

//        new EasyVerify2().timeline();
        JSONObject aa = new JSONObject();
        aa.put("sss","ddd");
//        String ll = (String) aa.get("ll");
        String ll = String.valueOf( aa.get("ll"));
        System.out.println(ll);
        if(ll == null){
            System.out.println(ll);
        }



    }

}
