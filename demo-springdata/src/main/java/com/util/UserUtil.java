package com.util;

import com.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by epcm on 2017/7/20.
 */

@Component
public class UserUtil {


    @Autowired
    static UserDao userDao;

    //查询用户是否存在
    public static boolean isUserExist(String username, String appkey){


        if(userDao.findByUsernameAndAppkey(username,appkey).size() > 0)
            return true;

        return false;
    }


}
