package service.controler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.dao.UserDao;
import service.entity.User;
import service.util.UserUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by epcm on 2017/7/13.
 */


@RestController
public class UserMgmtController {

    @Autowired
    UserDao userDao;

    @Autowired
    UserUtil userUtil;

    @Autowired
    MongoTemplate mongoTemplate;


    private final static String URI = "/v1/apps/{appkey}/service/user-service/users/{username}";


    //获取用户详细信息
    @GetMapping(value= UserMgmtController.URI )
    public ResponseEntity getUser(@PathVariable String appkey,@PathVariable String username){

        //校验token是否有效
//        if(! TokenUtil.checkToken(token))
//            return new ResponseEntity( ResponseDiscription.InvalidToken, HttpStatus.UNAUTHORIZED);


        JSONObject responseObj = new JSONObject();
        JSONArray userArrayObj = new JSONArray();

        //去mongo查询
        List userList = userDao.findByUsernameAndAppkey(username,appkey);

        if(userList.size() == 0 )
            return new ResponseEntity(" Can not find user: "+ username, HttpStatus.NOT_FOUND);

        for(Object user : userList){
            userArrayObj.add(JSON.toJSON(user));
        }

        responseObj.put("data",userArrayObj);
        responseObj.put("size",userArrayObj.size());


        System.out.println("~~~~~~ get:   " + username);

        return new ResponseEntity(responseObj, HttpStatus.OK);

    }

    //修改用户信息
    @PutMapping(value= UserMgmtController.URI )
    public ResponseEntity modifyUser(@PathVariable String appkey,@PathVariable String username, @RequestBody String requestBody){

        //查询用户是否存在
        if(!userUtil.isUserExist(username,appkey)){
            return new ResponseEntity("User: "+ username+ " do not Exists!",HttpStatus.NOT_FOUND);
        }

//        Update update = new Update().set("password","aaaaaa").set("modified_at",new Date().getTime());

        Criteria criteria = Criteria.where("username").is(username);


        BasicDBObject basicDBObject=new BasicDBObject();
        basicDBObject.put("$set", com.mongodb.util.JSON.parse(requestBody));

        WriteResult writeResult = mongoTemplate.updateFirst(new Query(criteria), new BasicUpdate(basicDBObject), User.class );

        System.out.println("~~~~~~  put:   "+username);

        return new ResponseEntity(writeResult.toString(), HttpStatus.OK);

    }



    private ResponseEntity backup(@PathVariable String appkey,@PathVariable String username, @RequestBody String requestBody){

        User user = userDao.findOneByUsernameAndAppkey(username,appkey);

        if(user == null){
            return new ResponseEntity("User: "+ username+ " do not Exists!",HttpStatus.NOT_FOUND);
        }

        JSONObject body = JSONObject.parseObject(requestBody);

        if(body == null){
            return new ResponseEntity("Body is null",HttpStatus.BAD_REQUEST);
        }

        for(Object key : body.keySet()){

            Class<? extends User> userClass =  user.getClass();
            List fieldsList = Arrays.asList(userClass.getDeclaredFields());

            try {
                if( fieldsList.contains(key) ){
                    Field theField = userClass.getDeclaredField(key.toString());
                    theField.setAccessible(true);// 设置操作权限为true
                    theField.set(user, body.get(key));

//                    user.setModified_at(new Date().getTime());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }




        Update update = new Update().set("password",user.getPassword());
        WriteResult writeResult = mongoTemplate.updateFirst(query(where("username").is(username)),update,User.class);

        System.out.println("~~~~~~  put:   "+username);

        return new ResponseEntity(JSONObject.toJSONString(user), HttpStatus.OK);

    }








}
