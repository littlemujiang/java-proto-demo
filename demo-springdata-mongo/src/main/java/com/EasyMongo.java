package com;

import com.alibaba.fastjson.JSONObject;
import com.config.MongodbConfig;
import com.entity.App;
import com.entity.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import netscape.javascript.JSObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import org.bson.Document;
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

                mongoTemplate = mongodbConfig.mongoTemplate();
                MappingMongoConverter mongoConverter = (MappingMongoConverter) mongoTemplate.getConverter();
                mongoConverter.setMapKeyDotReplacement("!");
//                MappingMongoConverter mongoConverter2 = (MappingMongoConverter) mongoTemplate.getConverter();
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


    private void insertDoc() {
        mongoInit();

        Document msgBson = new Document();
        msgBson.put("father.son","value");

//        JSONObject msgJson = new JSONObject();
//        msgJson.put("father!son","value");


        String collectionName = "demo_temp";

//        mongoTemplate.insert(msgJson, collectionName);
        mongoTemplate.save(msgBson, collectionName);

    }

    private void queryInfo(){

        mongoInit();
//        String queryString = "{\n" +
//                "    \"app_key_info.app_key\":\"DD30C2ED868F470DB521B9B6014A7DC6\",\n" +
//                "    \"appServices\":{\"$elemMatch\":\n" +
//                "        {\n" +
//                "            \"service_id\" : \"593cf9b680cfe22ed91a25ff\",\n" +
//                "            \"service_statu\" : \"empowered\"\n" +
//                "        }\n" +
//                "        }  \n" +
//                "    }";
//        String queryString = "{\"father!son\":\"value\"}";

        DBObject dbObject = new BasicDBObject();
        BasicDBObject fieldsObject=new BasicDBObject();
        fieldsObject.put("ts",true);
        BasicQuery query = new BasicQuery(dbObject,fieldsObject);
        query.addCriteria(Criteria.where("ts").lte(new Date()));

        List<Document> appList =  mongoTemplate.find(query, Document.class,"SDF");

        System.out.println("=========== \r\n"+appList);

    }

    public static void main(String[] args) {
        // TODO �Զ����ɵķ������

//        SpringApplication.run(EasyMongo.class, args);

//        new EasyMongo().updateDoc();
//        new EasyMongo().insertDoc();
        new EasyMongo().queryInfo();
//          new MSPServiceApplyStat().getAppliedService();
    }


}
