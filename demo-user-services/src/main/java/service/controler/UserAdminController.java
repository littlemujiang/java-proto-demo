package service.controler;

/**
 * Created by epcm on 2017/7/12.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import service.dao.UserDao;
import service.entity.ResponseDiscription;
import service.entity.User;
import service.util.TokenUtil;
import service.util.UserUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
public class UserAdminController {


    @Autowired
    UserDao userDao;

    @Autowired
    UserUtil userUtil;


    //管理员获取用户列表
    @GetMapping(value="/v1/apps/{appkey}/service/user-service/users")
    public ResponseEntity getUsers(@PathVariable String appkey, @RequestHeader String accessToken){

        //校验token是否有效
        if(! TokenUtil.checkToken(accessToken))
            return new ResponseEntity( ResponseDiscription.InvalidToken, HttpStatus.UNAUTHORIZED);


        JSONObject responseObj = new JSONObject();
        JSONArray userArrayObj = new JSONArray();
        long cursor = 1111;

        List userList =  new ArrayList();
        userList = userDao.findAll();

        for(Object user : userList){
            userArrayObj.add(JSON.toJSON(user));
        }

        responseObj.put("data",userArrayObj);
        responseObj.put("cursor",cursor);

        System.out.println("~~~~~~ get:   "+appkey);

        return new ResponseEntity(responseObj, HttpStatus.OK);

    }

    //管理员添加用户
    @PostMapping(value="/v1/apps/{appkey}/service/user-service/users")
    public ResponseEntity addUsers(@PathVariable String appkey, @RequestHeader String accessToken, @RequestBody String requestBody){

        //校验token是否有效
        if( ! TokenUtil.checkToken(accessToken) )
            return new ResponseEntity(ResponseDiscription.InvalidToken ,HttpStatus.UNAUTHORIZED);

        User user = JSONObject.parseObject(requestBody,User.class);
        String username = user.getUsername();
        String password = user.getPassword();

        //查询必填字段是否为空
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
            return new ResponseEntity( "Null username or password!" ,HttpStatus.BAD_REQUEST);

        //查询用户是否存在
        if(userUtil.isUserExist(username,appkey))
            return new ResponseEntity("User: "+ username+ " Exists!",HttpStatus.CONFLICT);

        long timestamp = new Date().getTime();
        user.setUser_id(UUID.randomUUID().toString().toUpperCase());
        user.setAppkey(appkey);
        user.setCreated_at(timestamp);
        user.setModified_at(timestamp);

        userDao.insert(user);

        System.out.println("~~~~~~  post:   "+appkey);

        return new ResponseEntity(JSONObject.toJSONString(user), HttpStatus.OK);

    }






}
