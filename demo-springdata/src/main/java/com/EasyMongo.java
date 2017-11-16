package com;

import com.alibaba.fastjson.JSONObject;
import com.config.MongodbConfig;
import com.entity.App;
import com.entity.User;
import com.mongodb.WriteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;
import java.util.List;

/**
 * Created by mujiang on 2017/9/8.
 */


//@SpringBootApplication
public class EasyMongo {

    MongoTemplate mongoTemplate = null;

    private MongoTemplate mongoInit(){

        try {
            if(mongoTemplate == null ){
                MongodbConfig mongodbConfig = new MongodbConfig();
                this.mongoTemplate = mongodbConfig.mongoTemplate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mongoTemplate;
    }

    private String updateDoc() {


        mongoInit();

        String username = "aaa112";
        String appkey = "demo";

        String requestBody = "";

//        User user = JSONObject.parseObject(requestBody,User.class);
        User user = new User();
//        //查询用户是否存在
//        if(!UserUtil.isUserExist(username,appkey)){
//            return "User: "+ username+ " do not Exists!";
//        }

        Query query = new Query();
        query.addCriteria(Criteria.where("appkey").is(appkey));
        query.addCriteria(Criteria.where("username").is(username));

        if(! mongoTemplate.exists(query,User.class)){
            System.out.println("User: "+ username+ " do not Exists!");
            return "User: "+ username+ " do not Exists!";
        }

        user.setUsername(username);
        user.setAppkey(appkey);
        user.setModified_at(new Date().getTime());

        Update update = new Update().set("password","bbbbb");
        WriteResult writeResult = mongoTemplate.updateFirst(new Query(Criteria.where("username").is(username)), update, User.class);

        System.out.println("~~~~~~  put:   "+username);

        return JSONObject.toJSONString(user);

    }


    private void queryAuthInfo(){

        mongoInit();
        String queryString = "{\n" +
                "    \"app_key_info.app_key\":\"DD30C2ED868F470DB521B9B6014A7DC6\",\n" +
                "    \"appServices\":{\"$elemMatch\":\n" +
                "        {\n" +
                "            \"service_id\" : \"593cf9b680cfe22ed91a25ff\",\n" +
                "            \"service_statu\" : \"empowered\"\n" +
                "        }\n" +
                "        }  \n" +
                "    }";
        BasicQuery query = new BasicQuery(queryString);

        List<App> appList =  mongoTemplate.find(query, App.class);

        System.out.println("==========="+appList.size());
        System.out.println("==========="+appList.get(0).getApp_key_info().app_key);

    }

    public static void main(String[] args) {
        // TODO �Զ����ɵķ������

//        SpringApplication.run(EasyMongo.class, args);

//        new EasyMongo().updateDoc();
        new EasyMongo().queryAuthInfo();
//          new MSPServiceApplyStat().getAppliedService();
    }


}
