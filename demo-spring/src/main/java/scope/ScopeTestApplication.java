package scope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by mujiang on 2018/1/16.
 */


@SpringBootApplication
@EnableEurekaClient
public class ScopeTestApplication {


    public static void main(String[] args) {
        // TODO �Զ����ɵķ������
        SpringApplication.run(ScopeTestApplication.class, args);

    }



}
