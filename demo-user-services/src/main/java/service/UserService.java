package service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by epcm on 2017/7/3.
 */


@SpringBootApplication
@EnableEurekaClient
public class UserService {


    public static void main(String[] args) {
        // TODO �Զ����ɵķ������
        SpringApplication.run(UserService.class, args);

    }



}
