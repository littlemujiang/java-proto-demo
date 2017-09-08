package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.dao.User;
import com.dao.UserDao;
import com.mongodb.WriteResult;
import com.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

/**
 * Created by mujiang on 2017/9/8.
 */

@Controller
public class DataCURDController {

    @Autowired
    UserDao userDao;

    @Autowired
    MongoTemplate mongoTemplate;


    @GetMapping("/")
    private String updateDoc(){

        String username = "aaa112";
        String appkey = "dddd";

        String requestBody = "";



        User user = JSONObject.parseObject(requestBody,User.class);
        //查询用户是否存在
        if(!UserUtil.isUserExist(username,appkey)){
            return "User: "+ username+ " do not Exists!";
        }

        user.setUsername(username);
        user.setAppkey(appkey);
        user.setModified_at(new Date().getTime());

        Update update = new Update().set("password","aaaaaa");
        WriteResult writeResult = mongoTemplate.updateFirst(new Query(Criteria.where("username").is(username)), update, User.class);

        System.out.println("~~~~~~  put:   "+username);

        return JSONObject.toJSONString(user);

    }

}
