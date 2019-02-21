package com;

import com.alibaba.fastjson.JSONObject;
import com.config.MongodbConfig;
import com.mongodb.BasicDBObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created by mujiang on 2017/9/8.
 */


//@SpringBootApplication

public class EasyMongo {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoTemplate.class);

    MongoTemplate mongoTemplate = null;

    private MongoTemplate mongoInit(){
        try {
            if(mongoTemplate == null ){
                MongodbConfig mongodbConfig = new MongodbConfig();
                mongoTemplate = mongodbConfig.mongoTemplate();
                System.out.println("init mongo template done");
                MappingMongoConverter mongoConverter = (MappingMongoConverter) mongoTemplate.getConverter();
//                mongoTemplate.setWriteConcern(new WriteConcern("."));
//                mongoTemplate.setWriteConcern(WriteConcern.JOURNALED);
//                mongoConverter.setMapKeyDotReplacement("!");
//                mongoConverter.afterPropertiesSet();
                MappingMongoConverter mongoConverter2 = (MappingMongoConverter) mongoTemplate.getConverter();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mongoTemplate;
    }
/*
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
        query.addCriteria(where("appkey").is(appkey));
        query.addCriteria(where("username").is(username));

        if(! mongoTemplate.exists(query,User.class)){
            System.out.println("User: "+ username+ " do not Exists!");
            return "User: "+ username+ " do not Exists!";
        }

        user.setUsername(username);
        user.setAppkey(appkey);
        user.setModified_at(System.currentTimeMillis());

        Update update = new Update().set("password","bbbbb");
        WriteResult writeResult = mongoTemplate.updateFirst(new Query(where("username").is(username)), update, User.class);

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

        DBObject dbObject = new BasicDBObject();
        BasicDBObject fieldsObject=new BasicDBObject();
        fieldsObject.put("ts",true);
        BasicQuery query = new BasicQuery(dbObject,fieldsObject);
        query.addCriteria(where("ts").lte(new Date()));

        List<Document> appList =  mongoTemplate.find(query, Document.class,"SDF");
        System.out.println("=========== \r\n"+appList);
    }
*/

    private void aggregateData() throws ParseException {
        mongoInit();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        //开始构建管道
        // 匹配查询
        Criteria criteria = where("eid").is("c2aaa628918d46838bb40ac5741361d5");
        Date startDate = sdf.parse("2019-01-18 08:40:00.000");
        Date endDate = sdf.parse("2019-01-18 08:50:00.000");

        criteria.andOperator(where("ts").gte(startDate).lte(endDate));
        MatchOperation matchOperation = match(criteria);

        // 数据加工
        Date dateOrigin = sdf.parse("1970-01-01 08:00:00.000");

//            ArithmeticOperators.Add tsLong = ArithmeticOperators.Add.valueOf(aggregationOperationContext -> Document.parse(new BasicDBObject("$subtract", Arrays.asList("$ts", dateOrigin)).toJson())).add(0);
        ArithmeticOperators.Add tsLong = ArithmeticOperators.Add.valueOf(aggregationOperationContext -> new Document("$subtract", Arrays.asList("$ts", dateOrigin))).add(0);
//            ArithmeticOperators.Add tsLong = ArithmeticOperators.Add.valueOf(aggregationOperationContext -> new BasicDBObject("$subtract", Arrays.asList("$ts", dateOrigin))).add(0);
        ArithmeticOperators.Mod tsOdd = ArithmeticOperators.Mod.valueOf(tsLong).mod(60 * 1000);
        ArithmeticOperators.Subtract tsCut = ArithmeticOperators.Subtract.valueOf(tsLong).subtract(tsOdd);

        AggregationOperation projectionOperation = project("ts", "Car_light")
                .and(tsCut)
                .as("tsCut");


        // 按条件分组
//        GroupOperation groupOperation = group("$dateCuted").count().as("msgcount").push("ts").as("tsList");
        GroupOperation groupOperation = group("$tsCut").avg("Car_light").as("Car_lightAvg");
//        GroupOperation groupOperation = group("$dateCut").sum("byteLength").as("msgByteSum");
        // 设置排序
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.ASC, "_id");
        //汇总管道
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, projectionOperation, groupOperation, sortOperation);
        //分组聚合查询
        long start = System.currentTimeMillis();
//        AggregationResults<Document> aggregate = mongoTemplate.aggregate(aggregation, "citicV2_f1fb457016a44fceb9a99298adaaf4ae", Document.class);
        AggregationResults<Document> aggregate = mongoTemplate.aggregate(aggregation, "c5040e6ebab4a45e69d43be094f99cf38", Document.class);
//        AggregationResults<Document> aggregate = mongoTemplate.aggregate(aggregation, "raw_6labg", Document.class);
        long end = System.currentTimeMillis();
        //获取结果
        List<Document> resultVos = aggregate.getMappedResults();
        for(Document resultVo : resultVos){
            resultVo.put("time", sdf.format(new Date(Long.valueOf(resultVo.get("_id").toString()))));
        }
        System.out.println("=========== \r\n" + resultVos.toString());
        System.out.println("=========== \r\n" + (end - start));

    }



    private void insertDoc() {
        mongoInit();
        Document msgBson = new Document();
        msgBson.put("father.son","value");

        JSONObject msgJson = new JSONObject();
        msgJson.put("father.son","value");
//        JSONObject msgJson = new JSONObject();
//        msgJson.put("father!son","value");

        String collectionName = "demo_temp";

//        mongoTemplate.insert(msgJson, collectionName);
//        mongoTemplate.save(msgBson, collectionName);
        mongoTemplate.save(msgJson, collectionName);
    }


    private void queryCompare() throws ParseException {
        mongoInit();

        new BasicDBObject("$subtract", Arrays.asList("$ts", "" ));

        Document dbObject = new Document();
        Document fieldsObject = new Document();
        fieldsObject.put("_id", false);
        fieldsObject.put("_class", false);
        fieldsObject.put("topic", false);
        fieldsObject.put("fresh", false);


//        Query query = new BasicQuery(dbObject, fieldsObject);
        Query query = new BasicQuery(dbObject);
//        Date startDate =  getStartDate("2018-11-30 00:59:45");
        Date startDate =  getStartDate("2018-11-30 08:59:45");
        Date endDate =  getStartDate("2019-01-30 09:00:45");
        query.addCriteria(Criteria.where("eid").is("45cacfbe126742ddbd5074c8b5f923d1"));
        query.addCriteria(Criteria.where("ts").gte(startDate).lte(endDate));

        long start = System.currentTimeMillis();
//        List<Document> documents = mongoTemplate.find(query, Document.class, "c41d5f5e97dab42ebbfb9dad49b1bb9b7");
        long documentsSize = mongoTemplate.count(query, Document.class, "c41d5f5e97dab42ebbfb9dad49b1bb9b7");
        long end = System.currentTimeMillis();
        //打印时间
        System.out.println("条数=========== \r\n" + documentsSize );
//        System.out.println("条数=========== \r\n" + documents.size() );
        System.out.println("用时=========== \r\n" + (end-start) );

    }


    private void aggregationCompare() throws ParseException {
        mongoInit();

        long start = System.currentTimeMillis();

        Date startDate =  getStartDate("2018-11-30 08:59:45");
        Date endDate =  getStartDate("2019-01-30 09:00:45");
        //条件匹配
        Criteria criteria = where("eid").is("45cacfbe126742ddbd5074c8b5f923d1");
        criteria.andOperator(where("ts").gte(startDate).lte(endDate));
        MatchOperation matchOperation = match(criteria);



        AggregationOperation projectionOperation = project("_id", "ts");
        // 按条件分组
        GroupOperation groupOperationCount = group("$_id").count().as("dataCount");


        //汇总管道
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, null, groupOperationCount, null);
        //分组聚合查询
        AggregationResults<Document> aggregate = mongoTemplate.aggregate(aggregation, "c41d5f5e97dab42ebbfb9dad49b1bb9b7", org.bson.Document.class);

        List<Document> result = aggregate.getMappedResults();

        long end = System.currentTimeMillis();
        //打印时间
        System.out.println("条数=========== \r\n" + result.size()  );
        System.out.println("用时=========== \r\n" + (end-start) );

    }



    private void queryCompare2() throws ParseException {
        mongoInit();

        new BasicDBObject("$subtract", Arrays.asList("$ts", "" ));

        Document dbObject = new Document();
        Document fieldsObject = new Document();
        fieldsObject.put("_id", false);
        fieldsObject.put("_class", false);
        fieldsObject.put("topic", false);
        fieldsObject.put("fresh", false);


        Query query = new BasicQuery(dbObject, fieldsObject);
//        Date startDate =  getStartDate("2018-11-30 00:59:45");
        Date startDate =  getStartDate("2018-11-30 08:59:45");
        Date endDate =  getStartDate("2019-01-30 09:00:45");
        query.addCriteria(Criteria.where("eid").is("45cacfbe126742ddbd5074c8b5f923d1"));
        query.addCriteria(Criteria.where("ts").gte(startDate).lte(endDate));

//        List<Document> documents = mongoTemplate.find(query, Document.class, "ccea57a425f9446c39a894332c775cd3c");

        Bson eidCriteria = Filters.eq("eid", "45cacfbe126742ddbd5074c8b5f923d1");
        Bson tsStartCriteria = Filters.gte("ts", startDate);
        Bson tsEndCriteria = Filters.lte("ts", endDate);

        Bson criteria = Filters.and(eidCriteria, tsStartCriteria, tsEndCriteria);
        long start = System.currentTimeMillis();
        FindIterable documents = mongoTemplate.getCollection("c41d5f5e97dab42ebbfb9dad49b1bb9b7").find(criteria).limit(50000);
        long end0 = System.currentTimeMillis();

        System.out.println("用时----------- \r\n" + (end0-start) );

        List<Document> result = new ArrayList<>();
        documents.batchSize(50000);
        MongoCursor<Document> cursor = documents.iterator();
        documents.into(result);

//        List<Document> docs = IterableUtils.toList(cursor

        long end2 = System.currentTimeMillis();
//        ((QueryBatchCursor) ((com.mongodb.MongoBatchCursorAdapter) cursor).batchCursor).nextBatch

//        ((QueryBatchCursor) ((MongoBatchCursorAdapter) cursor).batchCursor).nextBatch;

        System.out.println("用时~~~~~~~~~~~~ \r\n" + (end2-start) );

        while (cursor.hasNext()){
            Document doc = cursor.next();
            result.add(doc);
        }

        long end = System.currentTimeMillis();
        //打印时间
        System.out.println("条数=========== \r\n" + result.size() );
        System.out.println("用时=========== \r\n" + (end-start) );

    }

    private Date getStartDate(String startTime) {
        //时间校验
        Date startDate = new Date(0L);
        startTime = startTime + ".000";
        startDate = strToDate(startTime, "yyyy-MM-dd HH:mm:ss.SSS");
        return startDate;
    }

    public static Date strToDate(String strDate, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }




    public static void main(String[] args) {
        // TODO �Զ����ɵķ������

//        new EasyMongo().insertDoc();

//        SpringApplication.run(EasyMongo.class, args);
//        new EasyMongo().queryInfo();
        try {
//            new EasyMongo().queryCompare();
            new EasyMongo().aggregateData();

//            new EasyMongo().queryCompare2();
//            new EasyMongo().aggregationCompare();

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}
