package task;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by mujiang on 2017/10/26.
 */


public class EasySchedule {

    public static void main(String[] args){


        AnnotationConfigApplicationContext context =  new AnnotationConfigApplicationContext(ScheduleConfig.class);
    }

}
