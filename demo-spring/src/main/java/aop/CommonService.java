package aop;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by mujiang on 2017/11/2.
 */
@Component
public class CommonService {


    public void point(){
        System.out.println("enter point");
    }


    public static void main(String[] args){

        ApplicationContext ac = new AnnotationConfigApplicationContext(AopConfig.class);

        CommonService cs = (CommonService) ac.getBean("commonService");

        cs.point();

    }


}
