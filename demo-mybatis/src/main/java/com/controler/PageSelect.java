package com.controler;

import com.MybatDemo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mapper_classes.AppMapper;
import org.apache.ibatis.session.SqlSession;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

/**
 * Created by epcm on 2017/8/30.
 */
public class PageSelect {


    Timestamp cursor = new Timestamp( System.currentTimeMillis() );

    public void getSelectResult( int pageSize ){

        MybatDemo a = new MybatDemo();
        SqlSession session = a.getSqlSession();
        AppMapper appMapper = session.getMapper(AppMapper.class);


//        String cursor = String.valueOf(System.currentTimeMillis());

        List<HashMap> result = appMapper.getAppsByPage(  cursor , pageSize );
        String resultString = JSON.toJSONString(result);
        JSONArray resultJson = (JSONArray) JSON.parse(resultString);

//        cursor = new Timestamp( Long.getLong( (String) result.get(result.size()-1).get("created_at")) );

        JSONObject resultByPage = new JSONObject();

        if(result.size() == 0){
            resultByPage.put("pageSize", 0);
//            return resultByPage;
        }
        else{
            cursor =  (Timestamp) result.get(result.size()-1).get("created_at")  ;
            resultByPage.put("pageSize", result.size());
            resultByPage.put("cursor",cursor.toString());
            resultByPage.put("resultByPage",resultJson);
        }

        System.out.println(resultByPage.toJSONString());

    }

}
